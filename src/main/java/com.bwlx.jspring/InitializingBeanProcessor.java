package com.bwlx.jspring;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/6 21:29.
 */
public class InitializingBeanProcessor extends AbstractBeanProcessor {

  public InitializingBeanProcessor(XmlBeanFactory xmlBeanFactory) {
    super(xmlBeanFactory);
  }

  @Override
  public void process(String beanId) throws Exception {
    Map<String, Object> bean2ObjectMap = this.getXmlBeanFactory().getBean2ObjectMap();
    Object o = bean2ObjectMap.get(beanId);
    for (Class<?> c : o.getClass().getInterfaces()) {
      if (InitializingBean.class.getSimpleName().equals(c.getSimpleName())) {
        Method m = o.getClass().getDeclaredMethod("afterPropertiesSet", null);
        m.invoke(o, null);
        break;
      }
    }
  }

  @Override
  public void process() throws Exception {
  }
}
