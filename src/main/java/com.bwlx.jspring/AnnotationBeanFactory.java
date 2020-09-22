package com.bwlx.jspring;

import com.bwlx.jspring.annotation.Component;
import com.bwlx.jspring.annotation.Value;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;

/**
 * desc.
 *
 * @author johan
 * @date 2020/9/22 22:09.
 */
public class AnnotationBeanFactory extends AbstractBeanFactory {

  public AnnotationBeanFactory(String packageName) {
    this.load(packageName);
  }

  private void load(String packageName) {
    try {
      String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      File dir = new File(path + File.pathSeparator + packageName.replace(".", File.pathSeparator));
      for (File file : dir.listFiles()) {
        if (file.isFile()) {
          String fileName = file.getName();
          String className = file.getName().substring(0, fileName.lastIndexOf(".class"));
          String FQDNClassName = packageName + "." + className;
          Class<?> klass = classLoader.loadClass(FQDNClassName);
          Component component = klass.getAnnotation(Component.class);
          if (component != null) {
            BeanDefinition bd = new BeanDefinition(Util.uncapitalize(className), FQDNClassName);
            Field[] fields = klass.getDeclaredFields();
            for (Field f : fields) {
              Value value = f.getAnnotation(Value.class);
              if (value != null) {
                bd.addProperty(new Property(f.getName(), value.value(), null));
              }
              Resource r = f.getAnnotation(Resource.class);
              if (r != null) {
                bd.addProperty(new Property(f.getName(), null, Util.uncapitalize(f.getType().getSimpleName())));
              }
            }
            beanDefinitionMap.put(Util.uncapitalize(className), bd);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
