package com.bwlx.jspring.v1;

import com.bwlx.jspring.BeanDefinition;
import com.bwlx.jspring.XmlBeanFactory;
import org.junit.Assert;
import org.junit.Test;

public class BeanInstantiateTest {

    @Test
    public void test() throws Exception {
        XmlBeanFactory iocContainer = new XmlBeanFactory("petstore-v1.xml");

        BeanDefinition bd = iocContainer.getBeanDefinition("petStore");

        Assert.assertEquals("com.bwlx.jspring.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) iocContainer.getBean("petStore");

        Assert.assertNotNull(petStore);

        AbstractDao accountDao = (AbstractDao) iocContainer.getBean("accountDao");
        Assert.assertNotNull(accountDao);
        accountDao.printName();
    }
}
