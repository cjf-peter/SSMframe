package com.privates.kevin.test;

import com.privates.kevin.entity.Person;
import com.privates.kevin.service.PersonService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-context.xml"})
public class personTest {
    private static Logger logger=Logger.getLogger(personTest.class);
    @Autowired
    private PersonService personService=null;
    @Test
    public void test1(){
        Person person=personService.selectData();
        logger.info("当前对象中的某个内容为："+person.getName());
    }


}
