CREATE SEQUENCE s_Admin INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_Categorie INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_CategorieDelete INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_Client INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_configuration INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_DemandeRechargement INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_Enchere INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE SEQUENCE s_Encherir INCREMENT BY 1 MINVALUE 1 NO MAXVALUE START WITH 1;

CREATE TABLE Admin (
  idAdmin varchar(10) NOT NULL,
  Login varchar(50) NOT NULL UNIQUE,
  Mdp varchar(50) NOT NULL,
  PRIMARY KEY (idAdmin)
);

CREATE TABLE Categorie (
  idCategorie varchar(20) NOT NULL,
  Designation varchar(100) NOT NULL,
  PRIMARY KEY (idCategorie)
);

CREATE TABLE CategorieDelete (
  idCategorieDelete varchar(30) NOT NULL,
  idCategorie varchar(20) NOT NULL,
  PRIMARY KEY (idCategorieDelete)
);

CREATE TABLE Client (
  idClient varchar(50) NOT NULL,
  Nom varchar(50) NOT NULL,
  Email varchar(50) NOT NULL UNIQUE,
  Mdp varchar(50) NOT NULL,
  Solde DOUBLE PRECISION NOT NULL,
  PRIMARY KEY (idClient)
);

CREATE TABLE Configuration (
  DureMin DOUBLE PRECISION NOT NULL,
  DureeMax DOUBLE PRECISION NOT NULL,
  idConfiguration varchar(30) NOT NULL,
  Commission DOUBLE PRECISION NOT NULL,
  PRIMARY KEY (idConfiguration)
);

CREATE TABLE DemandeRechargement (
  idDemandeRechargement varchar(30) NOT NULL,
  idClient varchar(50) NOT NULL,
  Montant DOUBLE PRECISION NOT NULL,
  Statut int4 NOT NULL,
  PRIMARY KEY (idDemandeRechargement)
);

CREATE TABLE Enchere (
  idEnchere varchar(10) NOT NULL,
  Nom varchar(50) NOT NULL,
  idCategorie varchar(20) NOT NULL,
  Duree int4 NOT NULL,
  PrixDepart DOUBLE PRECISION NOT NULL,
  Description text NOT NULL,
  idClient varchar(50) NOT NULL,
  Date timestamp DEFAULT current_timestamp NOT NULL,
  Commission DOUBLE PRECISION NOT NULL,
  PRIMARY KEY (idEnchere)
);

CREATE TABLE Encherir (
  idEncherir varchar(10) NOT NULL,
  idEnchere varchar(10) NOT NULL,
  idClient varchar(50) NOT NULL,
  Montant int4 NOT NULL,
  Date timestamp NOT NULL,
  PRIMARY KEY (idEncherir)
);

ALTER TABLE
  Enchere
ADD
  CONSTRAINT FKEnchere922758 FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);

ALTER TABLE
  Enchere
ADD
  CONSTRAINT FKEnchere462703 FOREIGN KEY (idClient) REFERENCES Client (idClient);

ALTER TABLE
  DemandeRechargement
ADD
  CONSTRAINT FKDemandeRec561574 FOREIGN KEY (idClient) REFERENCES Client (idClient);

ALTER TABLE
  Encherir
ADD
  CONSTRAINT FKEncherir77814 FOREIGN KEY (idEnchere) REFERENCES Enchere (idEnchere);

ALTER TABLE
  Encherir
ADD
  CONSTRAINT FKEncherir900186 FOREIGN KEY (idClient) REFERENCES Client (idClient);

ALTER TABLE
  CategorieDelete
ADD
  CONSTRAINT FKCategorieD952076 FOREIGN KEY (idCategorie) REFERENCES Categorie (idCategorie);