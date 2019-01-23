package com.privates.kevin.web;

import com.privates.kevin.entity.Person;
import com.privates.kevin.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("message")
public class PersonController {

//    public static final Logger logger= LogManager.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;
    @ModelAttribute
    public Person get(@RequestParam(required = false) String id){

        if(id==null){
            return new Person();
        }else{
            Person person=personService.get(id);
            return person;
        }
    }


    @RequestMapping(value = "show",method = RequestMethod.GET)
    @ResponseBody
    public Person GetMessage(@RequestParam("id") String id){
        System.out.println("当前id"+id);
        Person p=personService.selectData();
        return p;
    }
    @RequestMapping("Info")
    public String Info(){
        return "Person";
    }

}
