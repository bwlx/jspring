package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/6 15:45.
 */
public interface BeanProcessor {
  void process(String beanId);

  void process();
}
