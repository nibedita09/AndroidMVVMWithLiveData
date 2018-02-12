package com.android.nytimesmvvm.common.error;

/**
 * Created by Satish Mulay on 3/24/2017.
 */

public class BaseURLEmptyException extends Exception {

    public BaseURLEmptyException() {
        super("Base Url can not be null or empty");
    }
}
