package com.bwlx.jspring;

import java.util.List;

/**
 * desc.
 *
 * @author johan
 * @date 2020/10/7 17:31.
 */
public class Aspect {
  private String ref;
  private List<Advice> advices;

  public Aspect(String ref, List<Advice> advices) {
    this.ref = ref;
    this.advices = advices;
  }

  public String getRef() {
    return ref;
  }

  public void setRef(String ref) {
    this.ref = ref;
  }

  public List<Advice> getAdvices() {
    return advices;
  }

  public void setAdvices(List<Advice> advices) {
    this.advices = advices;
  }
}
