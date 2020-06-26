package portal.education.Monolit.utils;

import java.io.Serializable;


public class ExcMsg {

    public static String AccessTokenNotValid(String obj) {
        return obj;
    }

    public static String ArticleAlreadyExist(String obj) {
        return "Article already exist ".concat(obj);
    }

    public static <T extends Serializable> String ArticleNotFound(T obj) {
        return "Could not find article ".concat(obj.toString());
    }

    public static String ArticleNotFound() {
        return "Could not find article!";
    }

    public static <T extends Serializable> String ArticleStatusNotFound(T obj) {
        return "Could not find article status:".concat(obj.toString());
    }

    public static <T extends Serializable> String TypeNotificationNotFound(T obj) {
        return "Could not find TypeNotification:".concat(obj.toString());
    }

    public static  <T extends Serializable> String FileNotFound(T obj) {
        return "File not find article by id:".concat(obj.toString());
    }

    public static  <T extends Serializable> String TagNotFound(T obj) {
        return "Tag not find article by id:".concat(obj.toString());
    }

    public static <T extends Serializable> String AuthorNotFound(T obj) {
        return "Could not find author ".concat(obj.toString());
    }

    public static String AuthorNotFound(Long obj) {
        return "Could not find author by id: ".concat(obj.toString());
    }

    public static String CategoryAlreadyExist(String obj) {
        return "Category already exist ".concat(obj);
    }

    public static String CategoryAlreadyExist(Object obj) {
        return obj.toString();
    }

    public static String CategoryNotFound(String obj) {
        return "Could not find category".concat(obj);
    }

    public static String CategoryNotFound(Long obj) {
        return "Could not find category".concat(obj.toString());
    }

    public static String CommentNotFound(String obj) {
        return "Could not find comment ".concat(obj);
    }

    public static String CommentNotFound(Long obj) {
        return "Could not find comment by id:".concat(obj.toString());
    }

    public static String UserAlreadyExist(String obj) {
        return "Usertest already exist ".concat(obj);
    }

    public static String UserNotFound(String obj) {
        return "Could not find user ".concat(obj);
    }

    public static String UserNotFound(Long obj) {
        return "Could not find user by id:".concat(obj.toString());
    }

    public static String ViewAlreadyExist(String obj) {
        return "AbstractView already exist ".concat(obj);
    }

}