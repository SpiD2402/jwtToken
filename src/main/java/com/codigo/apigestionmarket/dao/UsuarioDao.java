package com.codigo.apigestionmarket.dao;

import com.codigo.apigestionmarket.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao  extends JpaRepository<Usuarios,Integer> {

    Usuarios findByEmail(@Param("email")String email);

}
