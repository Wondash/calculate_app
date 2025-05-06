package com.Calculate.calculate_app.tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    public static String encryptPassword(String password) {
        try {
            // 获取 SHA - 256 算法的 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将密码转换为字节数组
            byte[] encodedHash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyPassword(String rawPassword, String encryptedPassword) {
        String hashedRawPassword = encryptPassword(rawPassword);
        return hashedRawPassword != null && hashedRawPassword.equals(encryptedPassword);
    }
}
