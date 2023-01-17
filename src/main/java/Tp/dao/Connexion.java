/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tp.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author karen
 */
public class Connexion {
    public static Connection getConnection() throws Exception {
        Connection con = null;
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://postgresql-mamisoa.alwaysdata.net:5432/mamisoa_testbase",
                "mamisoa", "root");
        return con;
    }

    public static MongoDatabase getMongoConnection() throws Exception {
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://karen:karen@clusterka.agwfuv7.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("EnchereTest");
        return database;
    }
}
