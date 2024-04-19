package com.elice.team4.singleShop.global.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("Cannot find the category.");
    }
}
