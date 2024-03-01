package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.dto.UsuarioDto;
import com.javatechie.crud.example.entity.Usuario;
import com.javatechie.crud.example.entity.UsuarioEntity;
import com.javatechie.crud.example.service.UsuarioService;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SwaggerDefinition(tags = {
	    @Tag(name = "user", description = "user Controller")})
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/addUsuario")
    public UsuarioDto addUsuario(@RequestBody UsuarioEntity entity) {
        return service.saveUsuario(entity);
    }
}
