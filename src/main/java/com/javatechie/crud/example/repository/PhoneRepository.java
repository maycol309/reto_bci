package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Phone;
import com.javatechie.crud.example.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhoneRepository extends JpaRepository<Phone,Integer> {
}

