package com.bwlx.jspring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * desc.
 *
 * @author johan
 * @date 2020/9/22 22:12.
 */
abstract class AbstractBeanFactory implements BeanFactory {
  final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
  final Map<String, Class> bean2ClassMap = new ConcurrentHashMap<>();
  final Map<String, Object> bean2ObjectMap = new ConcurrentHashMap<>();

  public BeanDefinition getBeanDefinition(String beanId) {
    return beanDefinitionMap.get(beanId);
  }

  @Override
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
