package com.javatechie.crud.example.service;

import com.javatechie.crud.example.dto.UsuarioDto;
import com.javatechie.crud.example.entity.Phone;
import com.javatechie.crud.example.entity.Usuario;
import com.javatechie.crud.example.repository.PhoneRepository;
import com.javatechie.crud.example.repository.UsuarioRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository repository;
    
    public Phone savePhone(Phone phone) {
    	return repository.save(phone);
    }

    public List<Phone> savePhones(List<Phone> phones) {
        return repository.saveAll(phones);
    }

    public Phone getPhoneById(int id) {
        return repository.findById(id).orElse(null);
    }

 

    public String deletePhone(int id) {
        repository.deleteById(id);
        return "phone removed !! " + id;
    }

    public Phone updatePhone(Phone phone) {
    	Phone existingProduct = repository.findById(phone.getId()).orElse(null);
        existingProduct.setNumber(phone.getNumber());
        existingProduct.setCitycode(phone.getCitycode());
        existingProduct.setCitycode(phone.getCitycode());
        existingProduct.setUser_id(phone.getUser_id());
        existingProduct.setModified(phone.getModified());
        return repository.save(existingProduct);
    }


}
