package com.bwlx.jspring.v1;


import com.bwlx.jspring.BeanDefinition;
import com.bwlx.jspring.IocContainer;
import com.bwlx.jspring.v1.dao.AccountDao;
import org.junit.Assert;
import org.junit.Test;
import com.bwlx.jspring.v1.service.PetStoreService;

public class BeanInstantiateTest {

    @Test
    public void test() throws Exception {
        IocContainer iocContainer = new IocContainer("petstore-v1.xml");

        BeanDefinition bd = iocContainer.getBeanDefinition("petStore");

        Assert.assertEquals("com.bwlx.jspring.v1.service.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) iocContainer.getBean("petStore");

        Assert.assertNotNull(petStore);

        AccountDao accountDao = (AccountDao) iocContainer.getBean("accountDao");
        Assert.assertNotNull(accountDao);
    }
}
