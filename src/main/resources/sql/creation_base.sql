-- =====================================================
-- Script de création : utilisateur, base et tables
-- SGBD : PostgreSQL
-- Exécution : psql -U postgres -f creation_base.sql
-- =====================================================

-- 1. Création de l'utilisateur
CREATE USER ourboutique WITH PASSWORD 'ourboutique123!';

-- 2. Création de la base (propriétaire : ourboutique)
CREATE DATABASE ourboutique OWNER ourboutique ENCODING 'UTF8';

-- 3. Privilèges
GRANT ALL PRIVILEGES ON DATABASE ourboutique TO ourboutique;

-- 4. Connexion à la base
\c ourboutique

GRANT ALL ON SCHEMA public TO ourboutique;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO ourboutique;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO ourboutique;

-- =====================================================
-- 5. Création des tables (tous les id en BIGINT / long)
-- =====================================================

-- Héritage : Personne <- Employe, Client
CREATE TABLE personne (
    id      BIGSERIAL PRIMARY KEY,
    nom     VARCHAR(100) NOT NULL,
    prenom  VARCHAR(100) NOT NULL
);

CREATE TABLE employe (
    id              BIGINT PRIMARY KEY,
    date_naissance  DATE NOT NULL,
    CONSTRAINT fk_employe_personne
        FOREIGN KEY (id) REFERENCES personne(id) ON DELETE CASCADE
);

CREATE TABLE client (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_client_personne
        FOREIGN KEY (id) REFERENCES personne(id) ON DELETE CASCADE
);

CREATE TABLE categorie (
    id           BIGSERIAL PRIMARY KEY,
    libelle      VARCHAR(100) NOT NULL,
    description  VARCHAR(255)
);

CREATE TABLE produit (
    id               BIGSERIAL PRIMARY KEY,
    libelle          VARCHAR(150) NOT NULL,
    prix_unitaire    DOUBLE PRECISION NOT NULL,
    date_peremption  DATE,
    categorie_id     BIGINT NOT NULL,
    CONSTRAINT fk_produit_categorie
        FOREIGN KEY (categorie_id) REFERENCES categorie(id)
);

CREATE TABLE achat (
    id          BIGSERIAL PRIMARY KEY,
    date_achat  DATE NOT NULL,
    remise      DOUBLE PRECISION DEFAULT 0,
    employe_id  BIGINT NOT NULL,
    client_id   BIGINT NOT NULL,
    CONSTRAINT fk_achat_employe
        FOREIGN KEY (employe_id) REFERENCES employe(id),
    CONSTRAINT fk_achat_client
        FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE produit_achete (
    achat_id    BIGINT NOT NULL,
    produit_id  BIGINT NOT NULL,
    remise      DOUBLE PRECISION DEFAULT 0,
    PRIMARY KEY (achat_id, produit_id),
    CONSTRAINT fk_produit_achete_achat
        FOREIGN KEY (achat_id) REFERENCES achat(id) ON DELETE CASCADE,
    CONSTRAINT fk_produit_achete_produit
        FOREIGN KEY (produit_id) REFERENCES produit(id)
);
