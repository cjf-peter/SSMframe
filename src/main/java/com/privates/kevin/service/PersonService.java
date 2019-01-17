package com.privates.kevin.service;

import com.privates.kevin.entity.Person;
import com.privates.kevin.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonMapper personMapper;
    public Person selectData(){
        return personMapper.selectByPrimaryKey(1);
    }

    public Person get(String id) {
        return personMapper.selectByPrimaryKey(Integer.valueOf(id));
    }
}
