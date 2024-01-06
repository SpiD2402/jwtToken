package com.codigo.apigestionmarket.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MarketUtils {

    private MarketUtils()
    {

    }

    public static ResponseEntity<String>getReponseEntity(String message, HttpStatus httpStatus)
    {
        return new ResponseEntity<String>("Mensaje: "+message, httpStatus);
    }

}
