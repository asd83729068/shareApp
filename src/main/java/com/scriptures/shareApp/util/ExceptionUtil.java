package com.scriptures.shareApp.util;


import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.exception.BusinessException;

/**
 * 异常工具类
 *
 * @author jzsong@uworks.cc
 */
public class ExceptionUtil {

  public static void throwException(int code, String codeLabel) {
    BusinessException e = new BusinessException(code, codeLabel, codeLabel);
    throw e;
  }

  public static void throwException(Errors error) {
    BusinessException e = new BusinessException(error, error.label);
    throw e;
  }

}
