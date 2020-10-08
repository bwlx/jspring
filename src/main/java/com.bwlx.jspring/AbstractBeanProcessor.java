package com.bwlx.jspring;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/6 15:46.
 */
public abstract class AbstractBeanProcessor implements BeanProcessor {
  private XmlBeanFactory xmlBeanFactory;

  public XmlBeanFactory getXmlBeanFactory() {
    return xmlBeanFactory;
  }

  public void setXmlBeanFactory(XmlBeanFactory xmlBeanFactory) {
    this.xmlBeanFactory = xmlBeanFactory;
  }

  public AbstractBeanProcessor(XmlBeanFactory xmlBeanFactory) {
    this.xmlBeanFactory = xmlBeanFactory;
  }
}
