package com.android.nytimesmvvm.common.error;


public class BaseURLEmptyException extends Exception {

    public BaseURLEmptyException() {
        super("Base Url can not be null or empty");
    }
}
