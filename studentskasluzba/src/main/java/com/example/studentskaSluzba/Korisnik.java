package com.example.studentskaSluzba;

import java.util.ArrayList;

import static com.example.studentskaSluzba.DB.listaKorisnika;

public class Korisnik {
    int ID;
    String ime;
    String prezime;
    String uloga;


    public Korisnik(int ID, String ime, String prezime,String uloga)
    {
        this.ID=ID;
        this.ime=ime;
        this.prezime=prezime;
        this.uloga = uloga;

    }

    public int getID() {
        return ID;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getUloga() {
        return uloga;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "ID=" + ID +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", uloga='" + uloga + '\'' +
                '}';
    }



}
