package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/7 17:30.
 */
public class Advice {
  protected String pointcut;
  protected String method;

  public Advice(String pointcut, String method) {
    this.pointcut = pointcut;
    this.method = method;
  }

  public String getPointcut() {
    return pointcut;
  }

  public void setPointcut(String pointcut) {
    this.pointcut = pointcut;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
