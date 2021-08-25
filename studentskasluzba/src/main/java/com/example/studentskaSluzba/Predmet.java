package com.example.studentskaSluzba;

import java.util.ArrayList;

public class Predmet {
    int ID;
    String imePredmeta;
    String imeProfesora;
    // ArrayList<Predmet> listaPredmeta = new ArrayList<Predmet>(); // dodat staticki niz predmeta

    public Predmet(int ID, String imePredmeta, String imeProfesora)
    {
        this.ID=ID;
        this.imePredmeta=imePredmeta;
        this.imeProfesora=imeProfesora;
    }

    public int getID() {
        return ID;
    }

    public String getImePredmeta() {
        return imePredmeta;
    }

    public String getImeProfesora() {
        return imeProfesora;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setImePredmeta(String imePredmeta) {
        this.imePredmeta = imePredmeta;
    }

    public void setImeProfesora(String imeProfesora) {
        this.imeProfesora = imeProfesora;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "ID=" + ID +
                ", imePredmeta='" + imePredmeta + '\'' +
                ", imeProfesora='" + imeProfesora + '\'' +
                '}';
    }
}
