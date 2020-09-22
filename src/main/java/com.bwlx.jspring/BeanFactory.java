package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/9/22 22:10.
 */
public interface BeanFactory {
  Object getBean(String beanId) throws Exception;
}
