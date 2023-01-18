package Tp.model;

import Tp.dao.ObjetBDD;

public class Configuration extends ObjetBDD {

    private String idConfiguration;
    private double DureeMin;
    private double DureeMax;
    private double Commission;

    public Configuration() {
        this.setNomTable("Configuration");
        this.setPrimaryKey("idConfiguration");
    }

    public String getIdConfiguration() {
        return idConfiguration;
    }

    public void setIdConfiguration(String idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    public double getDureeMin() {
        return DureeMin;
    }

    public void setDureeMin(double dureeMin) {
        DureeMin = dureeMin;
    }

    public double getDureeMax() {
        return DureeMax;
    }

    public void setDureeMax(double dureeMax) {
        DureeMax = dureeMax;
    }

    public double getCommission() {
        return Commission;
    }

    public void setCommission(double commission) {
        Commission = commission;
    }

    /* 
    public void UpdateConfig() throws Exception{
        MongoDatabase m=Connexion.getMongoConnection();
        Document doc=new Document();
        if(this.getCommission()==0){
            doc.append("DureeMin",this.getDureeMin()).append("DureeMax", this.getDureeMax());
        }
    }

    public void getConfig() throws Exception{
        MongoDatabase database=Connexion.getMongoConnection();
        MongoCollection<Document> collection = database.getCollection("Configuration");

        // Retrieve all documents sorted by _id
        List<Document> documents = collection.find().sort(new Document("_id", 1)).into(new ArrayList<>());
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
    }
    */

}
