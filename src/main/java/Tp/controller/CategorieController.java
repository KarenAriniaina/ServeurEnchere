package Tp.controller;

import java.sql.Connection;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Tp.JSonData.JsonData;
import Tp.dao.Connexion;
import Tp.dao.ObjetBDD;
import Tp.model.Categorie;

@RestController
public class CategorieController {

    @CrossOrigin
    @PostMapping("Categorie/")
    public JsonData AjoutCategorie(@RequestParam String designation) throws Exception {
        JsonData json = new JsonData();
        Connection con=null;
        try {
            con=Connexion.getConnection();
            con.setAutoCommit(false);
            Categorie c=new Categorie();
            c.setDesignation(designation);
            c.Create(con);
            c.setIdCategorie("Categorie_"+Integer.toString(c.currentSequence(con)));
            Object[] lc=new Object[1];
            lc[0]=c;
            json.setData(lc);
            json.setMessage("Operation reussi");
            con.commit();
        } catch (Exception e) {
            if(con!=null) con.rollback();
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        finally{
            if(con!=null) con.close();
        }
        return json;

    }

    @CrossOrigin
    @PostMapping("Categorie/{id}")
    public JsonData UpdateCategorie(@PathParam("id") String id,@RequestParam("description") String designation) throws Exception {
        JsonData json = new JsonData();
        try {
            Categorie c=new Categorie();
            c.setIdCategorie(id);
            c.setDesignation(designation);
            c.Update(null);
            Object[] lc=new Object[1];
            lc[0]=c;
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
    @PostMapping("/DeleteCategorie/{idC}")
    public JsonData DeleteCategorie(@PathParam("idC") String idCategorie) throws Exception {
        JsonData json = new JsonData();
        Connection con=null;
        try {
            con=Connexion.getConnection();
            con.setAutoCommit(false);
            Categorie c=new Categorie();
            c.setIdCategorie(idCategorie);
            c.setNomTable("CategorieDelete");
            c.setPrimaryKey("idCategorieDelete");
            c.Create(con);
            Object[] lc=new Object[1];
            lc[0]=c;
            json.setData(lc);
            json.setMessage("Operation delete reussi");
            con.commit();
        } catch (Exception e) {
            if(con!=null) con.rollback();
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        finally{
            if(con!=null) con.close();
        }
        return json;

    }

    @CrossOrigin
    @RequestMapping("/Categories")
    public JsonData ListeCategorie(@PathParam("idC") String idCategorie) throws Exception {
        JsonData json = new JsonData();
        try {
            ObjetBDD[] lc=new Categorie().Find(null);
            json.setData(lc);
            json.setMessage("Operation delete reussi");
        } catch (Exception e) {
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        return json;

    }
}
