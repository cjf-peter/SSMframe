package com.privates.kevin.web;

import com.privates.kevin.entity.Person;
import com.privates.kevin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("message")
public class PersonController {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "show",method = RequestMethod.GET)
    @ResponseBody
    public Person GetMessage(@RequestParam("id") String id){
        System.out.println("当前id"+id);
        Person p=personService.selectData();
        return p;
    }
}
