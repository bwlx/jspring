package com.bwlx.jspring;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {
  private String id;
  private String beanClassName;
  private List<Property> props = new ArrayList<>();

  public BeanDefinition(String id, String beanClassName) {
    this.id = id;
    this.beanClassName = beanClassName;
  }

  public String getBeanClassName() {
    return beanClassName;
  }

  public void addProperty(Property property) {
    this.props.add(property);
  }

  public List<Property> getProps() {
    return props;
  }
}
