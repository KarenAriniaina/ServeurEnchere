package Tp.controller;

import java.sql.Connection;
import java.util.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Tp.JSonData.JsonData;
import Tp.dao.Connexion;
import Tp.dao.ObjetBDD;
import Tp.model.Admin;
import Tp.model.Categorie;
import Tp.model.Enchere;

@RestController
public class CategorieController {

    @CrossOrigin
    @PostMapping("/Categorie")
    public JsonData AjoutCategorie(@RequestHeader("token") String token, @RequestHeader("idAdmin") String idAdmin,
            @RequestParam String designation) throws Exception {
        JsonData json = new JsonData();
        Connection con = null;
        Admin a = new Admin();
        a.setIdAdmin(idAdmin);
        a.setToken(token);
        if (a.VerifToken()) {
            try {
                con = Connexion.getConnection();
                con.setAutoCommit(false);
                Categorie c = new Categorie();
                c.setDesignation(designation);
                c.Create(con);
                c.setIdCategorie("Categorie_" + Integer.toString(c.currentSequence(con)));
                Object[] lc = new Object[1];
                lc[0] = c;
                json.setData(lc);
                json.setMessage("Operation reussi");
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

    @CrossOrigin
    @PostMapping("Categorie/{id}")
    public JsonData UpdateCategorie(@RequestHeader("token") String token, @RequestHeader("idAdmin") String idAdmin,
            @PathVariable("id") String id, @RequestParam("description") String designation) throws Exception {
        JsonData json = new JsonData();
        Admin a = new Admin();
        a.setIdAdmin(idAdmin);
        a.setToken(token);
        if (a.VerifToken()) {
            try {
                Categorie c = new Categorie();
                c.setIdCategorie(id);
                c.setDesignation(designation);
                c.Update(null);
                Object[] lc = new Object[1];
                lc[0] = c;
                json.setData(lc);
                json.setMessage("Operation reussi");
            } catch (Exception e) {
                json.setData(null);
                json.setMessage("Operation echoue");
                json.setStatus(false);
                json.setErreur(e.getMessage());
            }
        } else {
            json.setData(null);
            json.setMessage("Vous n'etes pas connecté");
        }
        return json;

    }

    @CrossOrigin
    @PostMapping("/DeleteCategorie/{idC}")
    public JsonData DeleteCategorie(@RequestHeader("token") String token, @RequestHeader("idAdmin") String idAdmin,
            @PathVariable("idC") String idCategorie) throws Exception {
        JsonData json = new JsonData();
        Connection con = null;
        Admin a = new Admin();
        a.setIdAdmin(idAdmin);
        a.setToken(token);
        if (a.VerifToken()) {
            try {

                con = Connexion.getConnection();
                con.setAutoCommit(false);
                Categorie c = new Categorie();
                c.setIdCategorie(idCategorie);
                c.setNomTable("CategorieDelete");
                c.setPrimaryKey("idCategorieDelete");
                c.Create(con);
                Object[] lc = new Object[1];
                lc[0] = c;
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

    @CrossOrigin
    @RequestMapping("/Categories")
    public JsonData ListeCategorie() throws Exception {
        JsonData json = new JsonData();
        try {
            ObjetBDD[] lc = new Categorie().Find(null);
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
    @RequestMapping("/RechercheEncheres")
    public JsonData RechereAvance(@RequestParam String motsCle, @RequestParam String idCategorie,
            @RequestParam Double prixmin, @RequestParam Double prixmax,
            @RequestParam Date Datedebut, @RequestParam Date DateFin) throws Exception {
        JsonData json = new JsonData();
        try {
            String requtes = "SELECT  * FROM enchere where 1=1";
            if (motsCle != null) {
                requtes += " AND description like '%" + motsCle + "'%";
            }
            if (idCategorie != null) {
                requtes += " AND idCategorie='" + idCategorie + "'";
            }
            if (DateFin != null || Datedebut != null) {
                if (DateFin != null && Datedebut != null) {
                    requtes += " AND (Date BETWEEN" + Datedebut + " and " + DateFin + ")";
                }
                if (DateFin != null && Datedebut == null) {
                    requtes += " AND (Date <=" + DateFin + ")";
                }
                if (DateFin == null && Datedebut != null) {
                    requtes += " AND (Date >=" + Datedebut + ")";
                }
            }
            if (prixmax != null || prixmin != null) {
                if (prixmax != null && prixmin != null) {
                    requtes += " AND (prix BETWEEN" + prixmin + " and " + prixmax + ")";
                }
                if (prixmax != null && prixmin == null) {
                    requtes += " AND (prix <=" + prixmax + ")";
                }
                if (prixmax == null && prixmin != null) {
                    requtes += " AND (prix >=" + prixmin + ")";
                }
            }

            ObjetBDD[] lc = new Enchere().Find(null, requtes);
            json.setData(lc);
            json.setMessage("Operation select reussi");
        } catch (Exception e) {
            json.setData(null);
            json.setMessage("Operation echoue");
            json.setStatus(false);
            json.setErreur(e.getMessage());
        }
        return json;

    }
}
