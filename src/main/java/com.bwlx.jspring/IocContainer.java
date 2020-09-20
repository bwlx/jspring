package com.bwlx.jspring;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IocContainer {

  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
  private final Map<String, Class> bean2ClassMap = new ConcurrentHashMap<>();
  private final Map<String, Object> bean2ObjectMap = new ConcurrentHashMap<>();

  public IocContainer(String configFile) {
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

  public BeanDefinition getBeanDefinition(String beanId) {
    return beanDefinitionMap.get(beanId);
  }

  public Object getBean(String beanId) throws Exception {
    Class<?> klass = bean2ClassMap.get(beanId);
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanId);
    Object obj = null;
    if (klass == null) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      klass = cl.loadClass(beanDefinition.getBeanClassName());
    }
    if (klass != null) {
      bean2ClassMap.put(beanId, klass);
      obj = bean2ObjectMap.get(beanId);
      if (obj == null) {
        obj = klass.newInstance();
      } else {
        return obj;
      }
    }
    List<Property> props = beanDefinition.getProps();
    for (Property p : props) {
      String propertyName = p.getName();
      Field field = klass.getDeclaredField(propertyName);
      String methodName = Util.getSetMethodName(propertyName);
      Method method = klass.getDeclaredMethod(methodName, field.getType());
      if (p.getValue() != null) {
        method.invoke(obj, p.getValue());
      }
      // ref 覆盖 value
      if (p.getRef() != null) {
        Object refObj = this.getBean(p.getRef());
        method.invoke(obj, refObj);
      }
    }
    bean2ObjectMap.put(beanId, obj);
    return obj;
  }
}
