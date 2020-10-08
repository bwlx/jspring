package com.bwlx.jspring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AspectBeanProcessor extends AbstractBeanProcessor {
  private Map<String, Map<String, List<Advice>>> pointcut2Aspect = new HashMap<String, Map<String, List<Advice>>>();

  public AspectBeanProcessor(XmlBeanFactory xmlBeanFactory) {
    super(xmlBeanFactory);
    buildPointcut2Aspect();
  }

  private void buildPointcut2Aspect() {
    List<Aspect> aspects = this.getXmlBeanFactory().getAspects();
    for (Aspect aspect : aspects) {
      String ref = aspect.getRef();
      for (Advice advice : aspect.getAdvices()) {
        String pointcut = advice.getPointcut();
        Map<String, List<Advice>> aspect2Advice = pointcut2Aspect.get(pointcut);
        if (aspect2Advice == null) {
          aspect2Advice = new HashMap<String, List<Advice>>();
          pointcut2Aspect.put(pointcut, aspect2Advice);
        }
        List<Advice> advices = aspect2Advice.get(ref);
        if (advices == null) {
          advices = new ArrayList<Advice>();
          aspect2Advice.put(ref, advices);
        }
        advices.add(advice);
      }
    }
  }

  @Override
  public void process(String beanId) throws Exception {
    Class klass = this.getXmlBeanFactory().getBean2ClassMap().get(beanId);
    for (String pointcut : pointcut2Aspect.keySet()) {
      if (pointcut.equals(klass.getName())) {
        Map<String, List<Advice>> aspect2Advice = pointcut2Aspect.get(pointcut);
        Object object = this.getXmlBeanFactory().getBean2ObjectMap().get(beanId);
        ProxyHandler proxyHandler = new ProxyHandler(object, aspect2Advice);
        Object proxy = Proxy.newProxyInstance(klass.getClassLoader(), klass.getInterfaces(), proxyHandler);
        this.getXmlBeanFactory().getBean2ObjectMap().put(beanId, proxy);
      }
    }
  }

  @Override
  public void process() throws Exception {
  }

  public class ProxyHandler implements InvocationHandler {
    private Object object;
    private Map<String, List<Advice>> aspect2Advice;

    public ProxyHandler(Object object, Map<String, List<Advice>> aspect2Advice) {
      this.object = object;
      this.aspect2Advice = aspect2Advice;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      for (String ref : aspect2Advice.keySet()) {
        Object aspectObject = AspectBeanProcessor.this.getXmlBeanFactory().getBean(ref);
        for (Advice advice : aspect2Advice.get(ref)) {
          if (advice instanceof BeforeAdvice) {
            Method m = aspectObject.getClass().getDeclaredMethod(advice.getMethod(), null);
            m.invoke(aspectObject, null);
          }
        }
      }
      return method.invoke(object, args);
    }
  }
}
