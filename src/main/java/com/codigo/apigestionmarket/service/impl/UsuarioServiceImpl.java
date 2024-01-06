package com.codigo.apigestionmarket.service.impl;

import com.codigo.apigestionmarket.constantes.Constants;
import com.codigo.apigestionmarket.dao.UsuarioDao;
import com.codigo.apigestionmarket.entity.Usuarios;
import com.codigo.apigestionmarket.security.CustomerDetailService;
import com.codigo.apigestionmarket.security.jwt.JwtUtil;
import com.codigo.apigestionmarket.service.UsuarioService;
import com.codigo.apigestionmarket.util.MarketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UsuarioServiceImpl  implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailService customerDetailService;


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {

        log.info("Ingreso a Registrar Usuario");


        usuarioDao.save(getUsuariosMap(requestMap));

        log.info("Termino de registrar Usuario");
        return MarketUtils.getReponseEntity(Constants.MSG_USUARIO_CREADO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Ingrso Login");
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password")));
                    if (authentication.isAuthenticated())
                    {
                        if(customerDetailService.getUsuarios().getStatus() ==Constants.ACTIVO)
                        {
                            return new ResponseEntity<String>(jwtUtil.generateToken(customerDetailService.getUsuarios().getEmail(),customerDetailService.getUsuarios().getRole()),HttpStatus.OK);
                        }
                    }
                    else {
                        return new ResponseEntity<String>("Error en el login",HttpStatus.BAD_REQUEST);
                    }

        }
        catch (Exception  e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Tu clave esta mal",HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Usuarios> obtenerTodos() {
        return usuarioDao.findAll();
    }

    private Usuarios getUsuariosMap(Map<String,String>requestMap)

    {
            Usuarios usuarios = new Usuarios();
            usuarios.setNombre(requestMap.get("nombre"));
            usuarios.setNumeroContacto(requestMap.get("numeroContacto"));
            usuarios.setEmail(requestMap.get("email"));
            usuarios.setPassword(requestMap.get("password"));
            usuarios.setStatus(Constants.ACTIVO);
            usuarios.setRole(Constants.ROLE_USUARIO);

            return usuarios;
    }



}
