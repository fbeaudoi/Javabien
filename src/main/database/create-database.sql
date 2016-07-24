DROP DATABASE IF EXISTS projetinf4375;

CREATE DATABASE projetinf4375 ENCODING='UTF8';
\c projetinf4375

CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;

CREATE TABLE foodtruck 
(
	id text NOT NULL PRIMARY KEY,
	name text,
	description text,
	camion text
);

CREATE TABLE schedule
(
	id text NOT NULL REFERENCES foodtruck,
	date DATE NOT NULL,
	heure_debut text NOT NULL,
	heure_fin text NOT NULL,
	coordinates geometry(POINT,4326) NOT NULL,
	lieu text
);

CREATE TABLE bixi
(
	id serial PRIMARY KEY,
	coordinates geometry(POINT,4326) NOT NULL,
	name text,
	nb_bikes INTEGER,
	nb_empty_docks INTEGER
);

CREATE TABLE arceaux
(
	id serial PRIMARY KEY,
	coordinates geometry(POINT,4326) NOT NULL,
);
