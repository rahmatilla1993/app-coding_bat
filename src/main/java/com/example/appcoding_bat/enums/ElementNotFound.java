package com.example.appcoding_bat.enums;

public enum ElementNotFound {

    LANGUAGE("Dasturlash tili topilmadi"),
    USER("Foydalanuvchi topilmadi"),
    THEME("Mavzu topilmadi"),
    ANSWER("Javob topilmadi"),
    TASK("Masala topilmadi");

    private final String message;

    ElementNotFound(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
