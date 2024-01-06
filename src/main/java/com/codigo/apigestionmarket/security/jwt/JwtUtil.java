package com.codigo.apigestionmarket.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String token_secret = "BootCampGrupo2";


    public <T> T extractClaims(String token , Function<Claims,T> claimsResolver)
    {
        final Claims claims = extraAllClaims(token);
        return  claimsResolver.apply(claims);
    }

    public Claims extraAllClaims(String token) {

        return Jwts.parser().setSigningKey(token_secret).parseClaimsJws(token).getBody();

    }

    public Date extractExpiration(String token)
    {
        return extractClaims(token, Claims::getExpiration);
    }
    public String extractUserName(String token)
    {
        return extractClaims(token,Claims::getSubject);
    }

    public Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String,Object>claims,String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+100*60*60*10))
                .signWith(SignatureAlgorithm.HS256,token_secret).compact();
    }

    public String generateToken(String userName,String role)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        return  createToken(claims,userName);
    }


    /*public String generateTokenV2(String userName,String role)
    {
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("scope","Token public");
        return  createToken(claims,userName);
    }*/


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName= extractUserName(token);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

   /* public Boolean validateTokenV2(String token, UserDetails userDetails) throws Exception {
        final String userName= extractUserName(token);
        Claims claims= extraAllClaims(token);
        if(claims.containsKey("scope"))
        {
            //No iNGRESAS CON TOKEN V1
            return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }

        return  null;
    }*/

}
