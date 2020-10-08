package com.bwlx.jspring.v1;

import java.lang.reflect.Method;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/7 17:19.
 */
public class LoggingAspect {
  public void logBefore(Method method, Object object, Object[] args) {
    System.out.println("---logBefore---");
    System.out.println(method.getName());
    System.out.println(object);
    System.out.println(args);
  }
}
