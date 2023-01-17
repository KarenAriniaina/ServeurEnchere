package Tp.model;

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

}
