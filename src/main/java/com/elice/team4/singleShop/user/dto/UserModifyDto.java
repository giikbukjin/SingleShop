package com.elice.team4.singleShop.user.dto;

import com.elice.team4.singleShop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModifyDto {
    private Long id;
    private String name;
    private String currentPassword;
    private String password;
    private HashMap<String, String> address;
    private String phoneNumber;
    private String email;

    public UserModifyDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }

    public String mapToString(HashMap<String, String> map) {

        // StringBuilder를 사용하여 값들을 문자열로 결합
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : map.values()) {
            stringBuilder.append(value);
        }
        // 결과 출력
        String result = stringBuilder.toString();

        return result;
    }

}
