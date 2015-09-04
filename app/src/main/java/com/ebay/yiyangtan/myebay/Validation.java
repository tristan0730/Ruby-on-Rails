package com.ebay.yiyangtan.myebay;

import android.widget.EditText;
import java.util.regex.Pattern;

/**
 * Created by yiyangtan on 4/20/15.
 */
public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String PRICE_REGEX = "^[1-9]\\d*(\\.\\d+)?$";
    // Error Messages
    private static final String REQUIRED_MSG = "Please enter a keyword";
    private static final String PRICE_MSG = "Please input a invalid number for price";

    // call this method when you need to check priceTo/ priceFrom validation
    public static boolean isValidPrice(EditText editText, boolean required) {
        return isValid(editText, PRICE_REGEX, PRICE_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    public static boolean isValidPriceRange(EditText priceFrom, EditText priceTo, String errMsg){
        priceTo.setError(null);
        if (priceTo.getText().toString().trim().equals("")||priceFrom.getText().toString().trim().equals("")) return true;
        else
            if(Float.parseFloat(priceFrom.getText().toString())>Float.parseFloat(priceTo.getText().toString())) {
                priceTo.setError(errMsg);
                return false;
            }
            else return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

}
