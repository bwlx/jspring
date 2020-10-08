package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/7 17:31.
 */
public class BeforeAdvice extends Advice {
  public BeforeAdvice(String pointcut, String method) {
    super(pointcut, method);
  }
}
