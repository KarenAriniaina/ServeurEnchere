package Tp.model;

import java.sql.Connection;

import Tp.dao.ObjetBDD;

public class Categorie extends ObjetBDD {
    private String idCategorie;
    private String Designation;

    public Categorie() {
        this.setNomTable("Categorie");
        this.setPrimaryKey("idCategorie");
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    @Override
    public ObjetBDD[] Find(Connection c) throws Exception {
        this.setNomTable("v_Categorie");
        ObjetBDD[] liste= super.Find(c);
        this.setNomTable("Categorie");
        return liste;
    }

}
