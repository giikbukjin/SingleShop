package com.elice.team4.singleShop.global.response;

import lombok.Getter;

@Getter
public enum Response {

    SUCCESS(0, "Success"),
    FAIL(-1, "Fail");

    final int code;
    final String message;

    Response(int code, String msg){
        this.code = code;
        this.message = msg;
    }
}
