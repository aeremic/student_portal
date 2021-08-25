package com.example.studentskaSluzba;

public class Prijava {
    int ID;
    int idStudenta;
    int idPredmeta;
    String profesor;
    String ocena;
    String nazivPredmeta;


    public Prijava(int ID,int idStudenta,String profesor,String ocena, String nazivPredmeta,int idPredmeta)
    {
        this.ID=ID;
        this.idStudenta=idStudenta;
        this.profesor=profesor;
        this.ocena=ocena;
        this.nazivPredmeta=nazivPredmeta;
        this.idPredmeta=idPredmeta;
    }

    public int getID() {
        return ID;
    }

    public int getIdStudenta() {
        return idStudenta;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getOcena() {
        return ocena;
    }

    public int getIdPredmeta() { return idPredmeta; }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }


}
