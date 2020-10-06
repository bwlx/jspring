package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/6 15:46.
 */
public abstract class AbstractBeanProcessor implements BeanProcessor {
  private XmlBeanFactory xmlBeanFactory;

  public AbstractBeanProcessor(XmlBeanFactory xmlBeanFactory) {
    this.xmlBeanFactory = xmlBeanFactory;
  }

  @Override
  public void process(String beanId) {

  }

  @Override
  public void process() {

  }
}
