package Tp.controller;
import java.sql.Connection;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Tp.JSonData.JsonData;
import Tp.dao.Connexion;
import Tp.dao.ObjetBDD;
import Tp.model.Client;
import Tp.model.DemandeRechargement;

@RestController
public class DemandeRechargementController{
    @CrossOrigin
    @RequestMapping("/DemandeRechargements")
    public JsonData ListeDemandeRechargementNonValide() throws Exception {
        JsonData json = new JsonData();
        try {
            DemandeRechargement d=new DemandeRechargement();
            d.setStatut(1);
            ObjetBDD[] lc=d.Find(null);
            json.setData(lc);
            json.setMessage("Operation reussi");
        } catch (Exception e) {
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        return json;

    }

    @CrossOrigin
    @RequestMapping("/DemandeRechargements/{id}")
    public JsonData ValideRechargement(@PathParam("id") String idDemande) throws Exception {
        JsonData json = new JsonData();
        try {
            DemandeRechargement d=new DemandeRechargement();
            d.setIdDemandeRechargement(idDemande);
            d.setStatut(0);
            d.Update(null);
            Object[] ld=new Object[1];
            ld[0]=d;
            json.setData(ld);
            json.setMessage("Operation reussi");
        } catch (Exception e) {
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        return json;

    }
    
    @CrossOrigin
    @PostMapping("/DemandeRechargement")
    public JsonData InsererRechargement(@RequestHeader("token") String token,@RequestHeader("idClient") String idClient,@RequestParam("montant") double montant) throws Exception {
        JsonData json = new JsonData();
        Connection con=null;
        Client c=new Client();
        c.setToken(token);
        c.setIdClient(idClient);
        if(! c.VerifToken()){
            try {
                con=Connexion.getConnection();
                con.setAutoCommit(false);
                DemandeRechargement d=new DemandeRechargement();
                d.setMontant(montant);
                d.setIdClient(idClient);
                d.Create(con);
                Object[] ld=new Object[1];
                ld[0]=d;
                json.setData(ld);
                json.setMessage("Operation reussi");
                con.commit();
            } catch (Exception e) {
                if(con!=null) con.rollback();
                json.setData(null);
                json.setMessage("Operation echoue");
                json.setStatus(false);
                json.setErreur(e.getMessage());
            }
        } else {
            json.setData(null);
            json.setMessage("Client non connecté");
        }
        return json;

    }
}