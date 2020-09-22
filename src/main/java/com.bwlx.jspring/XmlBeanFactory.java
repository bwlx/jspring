package com.bwlx.jspring;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author johan
 */
public class XmlBeanFactory extends AbstractBeanFactory {

  public XmlBeanFactory(String configFile) {
    loadBeanDefinitions(configFile);
  }

  private void loadBeanDefinitions(String configFile) {
    InputStream is = null;
    try {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      is = cl.getResourceAsStream(configFile); // 根据 configFile 获取 petstore-v1.xml 文件的字节流
      SAXReader reader = new SAXReader();
      Document doc = reader.read(is); // 将字节流转成文档格式
      Element root = doc.getRootElement(); // <beans>
      Iterator iter = root.elementIterator();
      // 遍历所有 bean
      while (iter.hasNext()) {
        Element ele = (Element) iter.next();
        String id = ele.attributeValue("id");
        String beanClassName = ele.attributeValue("class");
        Iterator propertyIter = ele.elementIterator("property");
        BeanDefinition bd = new BeanDefinition(id, beanClassName);
        while (propertyIter.hasNext()) {
          Element propertyEle = (Element) propertyIter.next();
          String name = propertyEle.attributeValue("name");
          String value = propertyEle.attributeValue("value");
          String ref = propertyEle.attributeValue("ref");
          // value/ref only one is not null
          Property property = new Property(name, value, ref);
          bd.addProperty(property);
        }
        this.beanDefinitionMap.put(id, bd);
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
