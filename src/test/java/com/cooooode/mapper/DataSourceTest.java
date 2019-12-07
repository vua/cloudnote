package com.cooooode.mapper;

import com.cooooode.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataSourceTest {
    static ApplicationContext applicationContext;
    static {
        applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    @Test
    public void getDataSource(){

        Object dataSource=applicationContext.getBean("dataSource");
        Assert.assertNotNull(dataSource);
    }

}
