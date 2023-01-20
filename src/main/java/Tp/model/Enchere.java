package Tp.model;

import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;

import java.sql.Timestamp;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Tp.dao.Connexion;
import Tp.dao.ObjetBDD;

public class Enchere extends ObjetBDD {
    private String idEnchere;
    private String Nom;
    private String idCategorie;
    private double Duree;
    private Double PrixDepart;
    private String Description;
    private String idClient;
    private Timestamp Date;
    private Double Commission;

    private List<Encherir> encherir;

    public List<Encherir> getEncherir() {
        return encherir;
    }

    public void setEncherir(List<Encherir> encherir) {
        this.encherir = encherir;
    }

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

    public double getDuree() {
        return Duree;
    }

    public Enchere() {
        this.setNomTable("Enchere");
        this.setPrimaryKey("idEnchere");
    }

    public void setDuree(double duree) {
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

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp date) {
        Date = date;
    }

    public Double getCommission() {
        return Commission;
    }

    public void setCommission(Double commission) {
        Commission = commission;
    }

    @Override
    public void Create(Connection c) throws Exception {
        try{
            super.Create(c);
            String currentId = "Enchere_" + Integer.toString(this.currentSequence(c));
            System.out.println(currentId);
            this.setIdEnchere(currentId);
            Enchere en=(Enchere) this.Find(c)[0];
            MongoDatabase database = Connexion.getMongoConnection();
            MongoCollection<Document> collection = database.getCollection("Enchere");
            Document filtre = new Document("idEnchere", this.getIdEnchere());
            System.out.println(filtre.toJson());
            Instant instant = Instant.ofEpochMilli(en.getDate().getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            System.out.println(localDateTime);
            int DureeEnSeconde = (int) Duree * 3600*24;
            System.out.println(localDateTime);
            localDateTime = localDateTime.plusSeconds(DureeEnSeconde);
            System.out.println(localDateTime);
            filtre.append("datefin", Timestamp.valueOf(localDateTime).toString());
            System.out.println(filtre.toJson());
            filtre.append("prixdepart", this.getPrixDepart()).append("description", this.getDescription()).append("Nom",
                    this.getNom());
            System.out.println(filtre.toJson());
            Categorie ca = new Categorie();
            ca.setIdCategorie(this.getIdCategorie());
            ca = (Categorie) ca.Find(c)[0];
            filtre.append("Categorie", ca.getDesignation());
            filtre.append("idCategorie", this.getIdCategorie());
            filtre.append("idClient", this.getIdClient());
            System.out.println(filtre.toJson());
            this.setEncherir(new ArrayList<>());
            filtre.append("encherir", this.getEncherir());
            collection.insertOne(filtre);
        }catch(Exception ex){
            if(c!=null) c.rollback();
            throw ex;
        }
    }

    /*public Enchere[] getListeEnchere() throws Exception {
        MongoDatabase database = Connexion.getMongoConnection();
        MongoCollection<Document> collection = database.getCollection("Enchere");
        ArrayList<Enchere> le=new ArrayList<Enchere>();
        for(Document c:collection.find()){
            Enchere e=new Enchere();
            e.setDate();
        }
    }*/
}
