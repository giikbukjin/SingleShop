package com.elice.team4.singleShop.order.dto;

import com.elice.team4.singleShop.order.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "주문 요약 제목은 필수입니다.")
    private String summaryTitle;

    @NotNull(message = "총 주문 금액은 필수입니다.")
    private Integer totalPrice;

   /* @NotBlank(message = "수취인 이름은 필수입니다.")
    private String receiverName;

    @NotBlank(message = "수취인 전화번호는 필수입니다.")
    private String receiverPhoneNumber;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String postalCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address1;

    private String address2;*/

    @NotBlank(message = "요청사항은 필수입니다.")
    private String request;

    private HashMap<String, String> address;

    public OrderRequestDto(Order order) {
        this.summaryTitle = order.getSummaryTitle();
        this.totalPrice = order.getTotalPrice();
        /*this.receiverName = order.getReceiverName();
        this.receiverPhoneNumber = order.getReceiverPhoneNumber();
        this.postalCode = order.getPostalCode();
        this.address1 = order.getAddress1();
        this.address2 = order.getAddress2();*/
        this.request = order.getRequest();
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