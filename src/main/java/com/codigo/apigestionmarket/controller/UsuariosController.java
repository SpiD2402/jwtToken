package com.codigo.apigestionmarket.controller;

import com.codigo.apigestionmarket.constantes.Constants;
import com.codigo.apigestionmarket.entity.Usuarios;
import com.codigo.apigestionmarket.service.UsuarioService;
import com.codigo.apigestionmarket.util.MarketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuariosController {


    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/singup")
    public ResponseEntity<String> registrarUsuario(@RequestBody(required = true)Map<String,String>requestMap)
    {
        try{
            return  usuarioService.singUp(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return MarketUtils.getReponseEntity(Constants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String>requestMap)
    {
        try {
            return  usuarioService.login(requestMap);
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
        return MarketUtils.getReponseEntity(Constants.ALGO_SALIO_MAL, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/todos")
    public List<Usuarios> todosUsuarios()
    {
        try {
            return usuarioService.obtenerTodos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    return null;

    }




}
