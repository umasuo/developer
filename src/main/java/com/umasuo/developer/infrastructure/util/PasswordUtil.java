package com.umasuo.developer.infrastructure.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password util.
 */
public final class PasswordUtil {

  /**
   * encrypt tool.
   */
  private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  /**
   * Private default constructor.
   */
  private PasswordUtil() {
  }

  /**
   * hash raw password.
   *
   * @param password raw password.
   * @return hashed password
   */
  public static String hashPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  /**
   * check if the password if correct.
   *
   * @param originalPassword raw password
   * @param hashedPassword   hashed password
   * @return result.
   */
  public static boolean checkPassword(String originalPassword, String hashedPassword) {
    return bCryptPasswordEncoder.matches(originalPassword, hashedPassword);
  }

}
