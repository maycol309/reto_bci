package com.javatechie.crud.example.service;

import com.javatechie.crud.example.dto.UsuarioDto;
import com.javatechie.crud.example.entity.Phone;
import com.javatechie.crud.example.entity.Usuario;
import com.javatechie.crud.example.entity.UsuarioEntity;
import com.javatechie.crud.example.jwt.JwtProvider;
import com.javatechie.crud.example.repository.PhoneRepository;
import com.javatechie.crud.example.repository.UsuarioRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class UsuarioService {
	
	@Value("${application.clave.pattern}")
	private String clave_pattern;
	
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PhoneRepository phoneRepository;
    
    @Autowired
    private JwtProvider jwtProvider;
    
    public UsuarioDto saveUsuario(UsuarioEntity entity) {
    	String regx = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    	Pattern pattern = Pattern.compile(regx);
    	Matcher matcher = pattern.matcher(entity.getEmail());
    	boolean checkEmail = matcher.matches();
    	if (!checkEmail) {
    		return UsuarioDto.builder().mensaje("Correo No Valido").build();
		}
    	Date date = new Date();
    	Usuario usuario = new Usuario();
    	usuario.setName(entity.getName());
    	usuario.setEmail(entity.getEmail());
    	usuario.setPassword(entity.getPassword());
    	usuario.setIsactive(entity.getIsactive());
    	usuario.setCreated(date);
    	usuario.setLast_login(date);
    	
    	Usuario existingUsuario = repository.getByEmail(usuario.getEmail());
    	
    	if (existingUsuario != null) {
    		return UsuarioDto.builder().mensaje("El correo ya registrado").build();
		}
    	
    	if (!isValidPassword(entity.getPassword())) {
    		return UsuarioDto.builder().mensaje("El password no cumple con los requisitos").build();
		}
    	
    	Usuario user = repository.save(usuario);
    	List<Phone> phones = entity.getPhones();
    	for (Phone phone : phones) {
			phone.setUser_id(""+user.getId());
			phone.setCreated(date);
		}
    	phoneRepository.saveAll(phones);
    	return UsuarioDto.builder()
    		.id(user.getId())
    		.created(user.getCreated())
    		.modified(user.getModified())
    		.last_login(user.getLast_login())
    		.token(jwtProvider.generateToken(user.getEmail()))
    		.isactive(user.getIsactive()).build();
    }

    public List<Usuario> saveUsuarios(List<Usuario> products) {
        return repository.saveAll(products);
    }

    public boolean isValidPassword(String password) {
  
        // Regex to check valid password.
        String regex = clave_pattern.replace("_", "$");
  
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }
  
        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);
  
        // Return if the password
        // matched the ReGex
        return m.matches();
    }

}
