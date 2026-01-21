package com.vitube.online_learning.utils;

public class ValidateUtil {
    
    /**
     * Kiểm tra xem email có đúng định dạng hay không.
     * @param email Email cần kiểm tra.
     * @return true nếu email hợp lệ, false nếu không hợp lệ.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    /**
     * Kiểm tra xem tên người dùng có đáp ứng định dạng yêu cầu hay không.
     * Tên người dùng phải dài ít nhất 3 ký tự và có thể chứa chữ cái, số, dấu chấm, gạch dưới và gạch ngang.
     * @param username Tên người dùng cần kiểm tra.
     * @return true nếu tên người dùng hợp lệ, false nếu không hợp lệ.
     */
    public static boolean isValidUsername(String username) {
        String usernameRegex = "^[a-zA-Z0-9._-]{3,}$";
        return username != null && username.matches(usernameRegex);
    }

    /**
     * Kiểm tra xem mật khẩu có đáp ứng yêu cầu về độ dài tối thiểu hay không.
     * Mật khẩu phải dài ít nhất 6 ký tự.
     * @param password Mật khẩu cần kiểm tra.
     * @return true nếu mật khẩu hợp lệ, false nếu không hợp lệ.
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean customValidateString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
}
