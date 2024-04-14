package com.elice.team4.singleShop.user.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Slf4j
@Component
@Getter
public class KakaoApi {
    @Value("${kakao.api_key}")
    private String kakaoApiKey;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.code_uri}")
    private String reqUrl;

    @Value("${kakao.userInfo_uri}")
    private String reqUrl1;

    @Value("${kakao.logout_uri}")
    private String reqUrl2;

    public String getAccessToken(String code, HttpServletResponse response) {
        String accessToken = "";

        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(kakaoApiKey);
            sb.append("&redirect_uri=").append(kakaoRedirectUri);
            sb.append("&code=").append(code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info("[KakaoApi.getAccessToken] responseCode = {}", responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while((line = br.readLine()) != null){
                responseSb.append(line);
            }
            String result = responseSb.toString();
            log.info("responseBody = {}", result);

            JsonElement element = JsonParser.parseString(result);
            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();

            var cookie1 = new Cookie("Authorization", URLEncoder.encode("Bearer " + accessToken, StandardCharsets.UTF_8));
            cookie1.setPath("/");
            cookie1.setMaxAge(60 * 60);
            cookie1.setHttpOnly(true);
            response.addCookie(cookie1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        try{
            URL url = new URL(reqUrl1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            int responseCode = conn.getResponseCode();
            log.info("[KakaoApi.getUserInfo] responseCode : {}",  responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while((line = br.readLine()) != null){
                responseSb.append(line);
            }
            String result = responseSb.toString();
            log.info("responseBody = {}", result);

            JsonElement element = JsonParser.parseString(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();

            userInfo.put("nickname", nickname);

            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }

    public void kakaoLogout(String accessToken) {

        try{
            URL url = new URL(reqUrl2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            log.info("[KakaoApi.kakaoLogout] responseCode : {}",  responseCode);

            BufferedReader br;
            if (responseCode >= 200 && responseCode <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            StringBuilder responseSb = new StringBuilder();
            while((line = br.readLine()) != null){
                responseSb.append(line);
            }
            String result = responseSb.toString();
            log.info("kakao logout - responseBody = {}", result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
