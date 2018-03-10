package com.rxmuhammadyoussef.core.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Pattern;

public class TextUtil {

    public static final int KEY_EMAIL = 0;
    public static final int KEY_PHONE = 1;
    public static final int KEY_FIRST_NAME = 2;
    public static final int KEY_LAST_NAME = 3;
    public static final int KEY_PASSWORD = 4;
    public static final int KEY_PASSWORD_MATCH = 5;
    public static final String EMAIL_AR = "البريد الالكتروني";
    public static final String PHONE_AR = "رقم الهاتف";
    public static final String FIRST_NAME_AR = "الاسم الاول";
    public static final String LAST_NAME_AR = "الاسم الاخير";
    public static final String PASSWORD_AR = "كلمة المرور";

    public TextUtil(Context context) {
        Preconditions.checkNonNull(context, "should not pass null context reference");
    }

    /**
     Returns true if the string is null or 0-length.

     @param text the string to be examined

     @return true if str is null or zero length
     */
    public boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    /**
     Returns true if the string matches a human name format

     @param name the string to be examined

     @return true if name consists of at least three alphabetic characters with no white space
     see https://stackoverflow.com/a/3802238/7330512
     */
    public boolean isValidName(String name) {
        Preconditions.checkNonNull(name);
        String acceptableChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ةؤلاىيوهـشسقفغعضصنملكظطزرذدخحجثتبا1234567890";
        if (name.length() >= 3) {
            for (int i = 0; i < name.length(); i++) {
                if (!acceptableChars.contains(String.valueOf(name.charAt(i)))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     @param email the string to be examined

     @return true if email is valid
     see https://stackoverflow.com/a/3802238/7330512
     */
    public boolean isValidEmail(String email) {
        Preconditions.checkNonNull(email);
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    /**
     @param password the string to be examined

     @return true if password consists of at at least 6 chars the contains at least 1 number, 1 small letter and 1 capital letter
     see https://stackoverflow.com/a/3802238/7330512
     */
    public boolean isValidPassword(String password) {
        Preconditions.checkNonNull(password);
        String expression = "^(?=.*[0-9])(?=.*[A-Z]).{6,}$";
        Pattern pattern = Pattern.compile(expression);
        return pattern.matcher(password).matches();
    }

    /**
     @param number the string to be examined

     @return true if valid phone number
     see https://howtodoinjava.com/regex/java-regex-validate-international-phone-numbers/
     */
    public boolean isValidPhoneNumber(String number) {
        Preconditions.checkNonNull(number);
        String expression = "^[+]?[0-9]{9,14}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(number).matches();
    }

    /**
     @param text the string to be examined

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfNotEmptyStringResult(String text) {
        Result result = new Result();
        if (TextUtils.isEmpty(text)) {
            result.valid = false;
            result.messageEn = "Please fill data";
            result.messageAr = "من فضلك املأ جميع البيانات";
        } else {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        }
        return result;
    }

    /**
     @param name the string to be examined

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfValidNameResult(String name) {
        Preconditions.checkNonNull(name);
        Result result = new Result();

        if (isEmpty(name)) {
            return getIfNotEmptyStringResult(name);
        } else if (isValidName(name)) {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        } else {
            result.valid = false;
            result.messageEn = "Invalid name";
            result.messageAr = "الاسم غير صحيح";
        }
        return result;
    }

    /**
     @param email the string to be examined

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfValidEmailResult(String email) {
        Preconditions.checkNonNull(email);
        Result result = new Result();

        if (isEmpty(email)) {
            return getIfNotEmptyStringResult(email);
        } else if (isValidEmail(email)) {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        } else {
            result.valid = false;
            result.messageEn = "Invalid email";
            result.messageAr = "البريد الإلكتروني غير صحيح";
        }
        return result;
    }

    /**
     @param password the string to be examined

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfValidPasswordResult(String password) {
        Preconditions.checkNonNull(password);
        Result result = new Result();
        if (isEmpty(password)) {
            return getIfNotEmptyStringResult(password);
        } else if (isValidPassword(password)) {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        } else {
            result.valid = false;
            result.messageEn = "Your password must be at least 6 characters and contain 1 uppercase & 1 lowercase";
            result.messageAr = "يحب ان تتكون كلمه المرور من 6 حروف و تحتوى على حرف صغير و كبير";
        }
        return result;
    }

    /**
     @param password  the string to be examined
     @param password2 the string to check against

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfPasswordsMatchResult(String password, String password2) {
        Preconditions.checkNonNull(password);
        Preconditions.checkNonNull(password2);
        Result result = new Result();
        if (password.contentEquals(password2)) {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        } else {
            result.valid = false;
            result.messageEn = "Passwords don't match";
            result.messageAr = "كلمات المرور غير متطابقة";
        }
        return result;
    }

    /**
     @param number the string to be examined

     Returns Result object containing boolean variable isValid, messageEn and localized messageEn
     */
    public Result getIfValidPhoneNumberResult(String number) {
        Preconditions.checkNonNull(number);
        Result result = new Result();
        if (isEmpty(number)) {
            return getIfNotEmptyStringResult(number);
        } else if (isValidPhoneNumber(number)) {
            result.valid = true;
            result.messageEn = "";
            result.messageAr = "";
        } else {
            result.valid = false;
            result.messageEn = "Invalid phone number";
            result.messageAr = "رقم الهاتف غير صحيح";
        }
        return result;
    }

    public final class Result {
        boolean valid;
        String messageEn;
        String messageAr;

        Result() {
            // This constructor is intentionally empty. Nothing special is needed here.
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessageEn() {
            return messageEn;
        }

        public String getMessageAr() {
            return messageAr;
        }
    }
}
