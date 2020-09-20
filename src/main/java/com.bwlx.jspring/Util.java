package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/9/20 18:25.
 */
public class Util {
  public static String getSetMethodName(String name) {
    return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
  }
}
