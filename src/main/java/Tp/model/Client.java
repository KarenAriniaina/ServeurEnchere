package Tp.model;

import Tp.dao.ObjetBDD;
import java.util.Base64;
import java.util.Date;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Client extends ObjetBDD {
    private String idClient;
    private String Nom;
    private String Email;
    private String Mdp;
    private double Solde;

    public Client() {
        this.setNomTable("Client");
        this.setPrimaryKey("idClient");
    }

    private String Token;

    public String getToken() {
        if (Token == null)
            this.setToken(this.getJWTToken());
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String mdp) {
        Mdp = mdp;
    }

    public double getSolde() {
        return Solde;
    }

    public void setSolde(double solde) {
        Solde = solde;
    }

    public String getJWTToken() {
        Instant now = Instant.now();
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
                String jwtToken = Jwts.builder()
                .claim("idAdmin", this.getIdClient())
                .setSubject(this.getIdClient())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(2, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS256,hmacKey)
                .compact();
        return "Bearer " + jwtToken;
    }

    public boolean VerifToken() {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> jwt =null;
        try{
            jwt= Jwts.parser()
                .setSigningKey(hmacKey)
                .parseClaimsJws(this.getToken().replaceFirst("Bearer ", ""));
        }catch(io.jsonwebtoken.ExpiredJwtException ex){
            jwt=null;
        }
        if(jwt!=null) return true;
        return false;
    }

    public void EncrypterMdp() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(this.getMdp().getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        this.setMdp(sb.toString());
    }

}
