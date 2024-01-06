package com.codigo.apigestionmarket.service;

import com.codigo.apigestionmarket.entity.Usuarios;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UsuarioService {

    ResponseEntity<String> singUp(Map<String,String> requestMap);
    ResponseEntity<String> login(Map<String,String> requestMap);

    List<Usuarios> obtenerTodos();

}
