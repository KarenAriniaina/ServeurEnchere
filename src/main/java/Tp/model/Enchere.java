package Tp.model;

import java.util.Date;

public class Enchere {
    String idEnchere;
    String Nom;
    String idCategorie;
    int Duree;
    Double PrixDepart;
    String Description;
    String idClient;
    Date Date;
    Double Commission;

    public String getIdEnchere() {
        return idEnchere;
    }

    public void setIdEnchere(String idEnchere) {
        this.idEnchere = idEnchere;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getDuree() {
        return Duree;
    }

    public Enchere() {
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public Double getPrixDepart() {
        return PrixDepart;
    }

    public void setPrixDepart(Double prixDepart) {
        PrixDepart = prixDepart;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public Double getCommission() {
        return Commission;
    }

    public void setCommission(Double commission) {
        Commission = commission;
    }

}