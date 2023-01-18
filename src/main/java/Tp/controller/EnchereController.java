package Tp.controller;

import java.sql.Connection;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Tp.JSonData.JsonData;
import Tp.dao.Connexion;
import Tp.dao.ObjetBDD;
import Tp.model.Admin;
import Tp.model.Enchere;
import Tp.model.Configuration;

@RestController
public class EnchereController {

    @CrossOrigin
    @PostMapping("/Enchere")
    public JsonData AjoutEnchere(@RequestHeader("token") String token, @RequestHeader("idAdmin") String idAdmin,
            @RequestParam String Nom, @RequestParam String idCategorie, @RequestParam int Duree,
            @RequestParam Double PrixDepart, @RequestParam String Description, @RequestParam String idClient,
            @RequestParam Date Date) throws Exception {
        JsonData json = new JsonData();
        Connection con = null;

        Admin a = new Admin();
        a.setIdAdmin(idAdmin);
        a.setToken(token);
        if (!a.VerifToken()) {

            try {
                con = Connexion.getConnection();
                con.setAutoCommit(false);

                Configuration conf = (Configuration) new Configuration().Find(con)[0];
                Enchere e = new Enchere();

                e.setDate(Date);
                e.setDescription(Description);
                e.setDuree(Duree);
                e.setIdCategorie(idCategorie);
                e.setIdClient(idClient);
                e.setNom(Nom);
                e.setPrixDepart(PrixDepart);
                e.setCommission(conf.getCommission());
                if (conf.getDureeMin() > e.getDuree() || conf.getDureeMax() < e.getDuree()) {
                    json.setData(null);
                    json.setMessage("Duree invalide");
                } else {
                    e.Create(con);
                    e.setIdEnchere("Enchere_" + Integer.toString(e.currentSequence(con)));

                    Object[] lc = new Object[1];
                    lc[0] = e;
                    json.setData(lc);
                    json.setMessage("Operation reussi");
                    con.commit();
                }

            } catch (Exception e) {
                if (con != null)
                    con.rollback();
                json.setData(null);
                json.setMessage("Operation echoue");
                json.setStatus(false);
                json.setErreur(e.getMessage());
            } finally {
                if (con != null)
                    con.close();

            }
        } else {
            json.setData(null);
            json.setMessage("Vous n'etes pas connecté");
        }
        return json;

    }

    @CrossOrigin
    @PostMapping("/DeleteCategorie/{idE}")
    public JsonData DeleteCategorie(@RequestHeader("token") String token, @RequestHeader("idAdmin") String idAdmin,
            @PathParam("idE") String idEnchere) throws Exception {
        JsonData json = new JsonData();
        Connection con = null;
        Admin a = new Admin();
        a.setIdAdmin(idAdmin);
        a.setToken(token);
        if (!a.VerifToken()) {
            try {
                con = Connexion.getConnection();
                con.setAutoCommit(false);
                Enchere e = new Enchere();
                e.setIdEnchere(idEnchere);
                e.setNomTable("EnchereDelete");
                e.setPrimaryKey("idEnchereDelete");
                e.Create(con);

                Object[] lc = new Object[1];
                lc[0] = e;
                json.setData(lc);
                json.setMessage("Operation delete reussi");
                con.commit();
            } catch (Exception e) {
                if (con != null)
                    con.rollback();
                json.setData(null);
                json.setMessage("Operation echoue");
                json.setStatus(false);
                json.setErreur(e.getMessage());
            } finally {
                if (con != null)
                    con.close();
            }
        } else {
            json.setData(null);
            json.setMessage("Vous n'etes pas connecté");
        }
        return json;

    }
    

}
