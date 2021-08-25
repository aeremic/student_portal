package com.example.studentskaSluzba;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.sql.Types.NULL;

public class DB {
    public int identifikatorK = 0;
    public int identifikatorP = 0;
    public int identifikatorPrijave=0;
    Connection  conn = null;
    Statement stmt = null;
    String sql = null;
    static ArrayList<Korisnik> listaKorisnika = new ArrayList<Korisnik>(); // dodat staticki niz korisnika
    static ArrayList<Predmet> listaPredmeta = new ArrayList<Predmet>(); // dodat staticki niz predmeta
    static ArrayList<Prijava> listaPrijava = new ArrayList<Prijava>(); //dodat staticki niz za prijave ispita
    static ArrayList<Predmet> listaPredmetaSlusanje = new ArrayList<Predmet>(); //dodat staticki niz predmeta koje slusa student
    static ArrayList<Predmet> listaProfesorPredmet = new ArrayList<Predmet>(); //dodat staticki niz predmeta koje predaje profesor
    static ArrayList<Prijava> listaProfesorOcenjivanje = new ArrayList<Prijava>(); //dodat staticki niz predmeta koje profesor moze da oceni

    public DB()
    {


        try
        {
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/studije",
                    "root",
                    ""
            );
            stmt = conn.createStatement();
        }
        catch (SQLException throwables)
        {
            throwables.getMessage();
            System.out.println("Nece konekcija");
        }
    }

    //Ucitavanje korisnika
    public Korisnik korisnik()
    {
        sql="SELECT * from korisnici" ;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {

                int id = rs.getInt("ID");
                String ime = rs.getString("Ime");
                String prezime = rs.getString("Prezime");
                String uloga = rs.getString("Uloga");
                if(id > identifikatorK)
                    identifikatorK=id;
                Korisnik pomocni = new Korisnik(id,ime,prezime,uloga);
                listaKorisnika.add(pomocni);



            }


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }
    //Kraj ucitavanja korisnika

    //Ucitavanje predmeta
    public Predmet predmet()
    {

        sql="" +
                "SELECT p.idPredmeta,p.nazivPredmeta,CONCAT(k.Ime,' ', k.Prezime) as ImeProfe from predmeti p, korisnici k WHERE k.ID = p.nastavnik" ;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {

                int id = rs.getInt("idPredmeta");
                String ime = rs.getString("nazivPredmeta");
                String imeProfesora = rs.getString("ImeProfe" );
                if(id > identifikatorP)
                    identifikatorP=id;
                Predmet pomocni = new Predmet(id,ime,imeProfesora);
                listaPredmeta.add(pomocni);

            }


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return null;
    }
    //Kraj ucitavanja predmeta



    //Dodavanje korisnika u bazu
    public void dodajKorisnika(String imeKorisnika, String prezime, String uloga) throws SQLException
    {


        sql= "INSERT INTO korisnici "
                + "(Ime,Prezime,Uloga) "
                + "VALUES('"+imeKorisnika+"','"+prezime+"','"+uloga+"')";


        stmt.executeUpdate(sql);




    }
    //Kraj dodavanja korisnika u bazu

    //Dodavanje predmeta u bazu
    public void dodavanjePredmeta(String nazivPredmeta,int nastavnik) throws SQLException
    {

        String sql;

        System.out.println("Azuriram predmeti...");
        sql= "INSERT INTO predmeti "
                + "(nazivPredmeta,nastavnik) "
                + "VALUES('"+nazivPredmeta+"','"+nastavnik+"') ";

        stmt.executeUpdate(sql);

        System.out.println("Tabela predmeti azurirana");

    }
    //Kraj dodavanja predmeta u bazu

    //Brisanje predmeta iz baze
    public void brisanjePredmeta(int id) throws SQLException {

        String sql;

        sql="DELETE FROM predmeti "
                +"WHERE idPredmeta='"+id+"'";

        stmt.executeUpdate(sql);
    }
    //Kraj brisanja predmeta iz baze


    //Brisanje korisnika iz baze
    public void brisanjeKorisnici(int id) throws SQLException {



        sql="DELETE FROM korisnici "
                +"WHERE ID='"+id+"'";

        stmt.executeUpdate(sql);

        sql="DELETE FROM predmeti "
                +"WHERE nastavnik='"+id+"'";
        stmt.executeUpdate(sql);
    }
    //Kraj brisanja korisnika iz baze


    //Izmena korisnika u bazi preko id
    public void izmenaKorisnika(int id, String Ime, String Prezime, String Uloga) throws SQLException {


        if(Ime !=""){
            sql ="UPDATE korisnici " +
                    "SET Ime =' "+Ime
                    +" ' WHERE ID = '"+id+"'";


            stmt.execute(sql);
        }

        if(Prezime !=""){
            sql ="UPDATE korisnici " +
                    "SET Prezime =' "+Prezime
                    +" ' WHERE ID = '"+id+"'";


            stmt.execute(sql);
        }

        if(Uloga !=""){
            sql ="UPDATE korisnici " +
                    "SET Uloga =' "+Uloga
                    +" ' WHERE ID = '"+id+"'";


            stmt.execute(sql);
        }
    }
    //kraj izmene korisnika


    //Izmena predmeta
    public void izmenaPredmeta(int id,String nazivPredmeta,Integer profesor) throws SQLException {


        if(nazivPredmeta !=""){
            sql ="UPDATE predmeti " +
                    "SET nazivPredmeta =' "+nazivPredmeta
                    +" ' WHERE idPredmeta = '"+id+"'";


            stmt.execute(sql);
        }
        if(!(profesor.equals(null))){
            sql ="UPDATE predmeti " +
                    "SET nastavnik =' "+profesor
                    +" ' WHERE idPredmeta = '"+id+"'";


            stmt.execute(sql);
        }
    }
    //kraj izmene predmeta

    /*************************/
    //DEO ZA STUDENTA

    public void prikaziPrijave(int id) throws SQLException
    {
        sql="" +
                "SELECT p.ID,p.idPredmeta,p.idStudenta,p.nazivPredmeta,p.ocena,CONCAT(k.Ime,' ', k.Prezime) as ImeProfe from prijave p, korisnici k WHERE k.ID = p.nastavnik && p.idStudenta = '"+id+"'" ;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {

                int ID = rs.getInt("ID");
                int idStudenta = rs.getInt("idStudenta");
                int idPredmeta = rs.getInt("idPredmeta");
                String imePredmeta = rs.getString("nazivPredmeta");
                String imeProfesora = rs.getString("ImeProfe" );
                int ocena = rs.getInt("ocena");
                String ocena2="MBR";
                if(ocena == 0)
                {
                    ocena2="Neocenjen";
                }
                else
                {
                    ocena2 = ocena+"";
                }
                if(id > identifikatorPrijave)
                    identifikatorPrijave=id;
                Prijava pomocni = new Prijava(ID,idStudenta,imeProfesora,ocena2,imePredmeta,idPredmeta);
                listaPrijava.add(pomocni);


            }


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void predmetiSlusanje(int id)
    {
        sql="" +
                "SELECT p.idPredmeta,p.nazivPredmeta,CONCAT(k.Ime,' ', k.Prezime) as ImeProfe from slusanje p, korisnici k WHERE k.ID = p.nastavnik && p.idStudenta = '"+id+"'" ;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {

                int ID = rs.getInt("idPredmeta");
                String ime = rs.getString("nazivPredmeta");
                String imeProfesora = rs.getString("ImeProfe" );
                Predmet pomocni = new Predmet(ID,ime,imeProfesora);
                listaPredmetaSlusanje.add(pomocni);
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public boolean prijaviIspit(int idStudenta, int idPredmeta,String nastavnik, String naziv) throws SQLException {
        sql="SELECT nazivPredmeta from prijave WHERE idStudenta= '"+idStudenta+"'"+"AND idPredmeta = '"+idPredmeta+"'";
        String imeIspita=null;
        int indikator=0;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String ime = rs.getString("nazivPredmeta");
                imeIspita=ime;
                if(imeIspita != null )
                {
                    indikator=1;
                    break;
                }
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }


        int idNastavnika=0;
        for (Korisnik k: listaKorisnika)
        {
            if(k != null)
            {
                String nazivProfesora = k.getIme() +" "+ k.getPrezime();
                if(nazivProfesora.equals(nastavnik) )
                {
                    idNastavnika=k.getID();
                }
            }
        }

       if(indikator == 0 )
        {

            String dodaj = "INSERT INTO prijave"
                    + "(idStudenta,idPredmeta,nazivPredmeta,nastavnik,ocena) "
                    + "VALUES('"+idStudenta+"','"+idPredmeta+"','"+naziv+"','"+idNastavnika+"','"+NULL+"') ";

           stmt.executeUpdate(dodaj);


        }
        else
        {
            System.out.println("Prijava vec postoji");
            return false;
        }

        return true;
    }
    //Deo za slusanje predmeta

    public boolean dodajPredmetSlusanje(int idStudenta,int idPredmeta,String nazivPredmeta,int nastavnik) throws SQLException {
        sql="SELECT nazivPredmeta from slusanje WHERE idStudenta= '"+idStudenta+"'"+"AND idPredmeta = '"+idPredmeta+"'";
        String imeIspita=null;
        int indikator=0;
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String ime = rs.getString("nazivPredmeta");
                imeIspita=ime;
                if(imeIspita != null )
                {
                    indikator=1;
                    break;
                }
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        if(indikator == 0)
        {

            sql="INSERT INTO slusanje "
                    + "(idStudenta,idPredmeta,nazivPredmeta,nastavnik) "
                    + "VALUES('"+idStudenta+"','"+idPredmeta+"','"+nazivPredmeta+"','"+nastavnik+"') ";

            stmt.executeUpdate(sql);
        }
        else
        {
            System.out.println("Student vec slusa predmet");
            return false;
        }

        return true;
    }


    public void obrisiPredmetSlusanje(int idStudenta,int idPredmeta) throws SQLException {
        sql="DELETE FROM slusanje "
                +"WHERE idStudenta='"+idStudenta+"' AND idPredmeta='"+idPredmeta+"'";

        stmt.executeUpdate(sql);
    }
    /*******************/
    //DEO ZA PROFESORA

    public void prikaziPrijaveProfesor(int idProfesora) throws SQLException
    {

        sql="SELECT * FROM prijave "
                +"WHERE nastavnik='"+idProfesora+"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {

                int ID = rs.getInt("ID");
                int idStudenta = rs.getInt("idStudenta");
                int ocena1 = rs.getInt("ocena");
                int idPredmeta = rs.getInt("idPredmeta");
                String nazivPredmeta = rs.getString("nazivPredmeta");
                String ocena2= ocena1+ "";
                String imeProfesora=null;
                for(Korisnik k: listaKorisnika) {
                    if (k != null && k.getID() == idProfesora)
                    {
                        imeProfesora=k.getIme()+" "+k.getPrezime();
                        break;
                    }
                }
                if(ocena1 == 0)
                {
                    ocena2 = "Neocenjen";
                }
                Prijava pomocna = new Prijava(ID,idStudenta,imeProfesora,ocena2,nazivPredmeta,idPredmeta);
                listaPrijava.add(pomocna);
            }
            for (Prijava pr: listaPrijava) {
                if(pr != null)
                {
                    System.out.println(pr.nazivPredmeta+" "+pr.ocena + " " + pr.profesor);
                }
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }

    public void upisiOcenu(int idPredmeta,int idStudenta, int ocena)
    {
        sql="SELECT ocena FROM prijave "
                +"WHERE idPredmeta='"+idPredmeta+"' AND idStudenta ='" +idStudenta +"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next() ) {
                int ocenaBaza = rs.getInt("ocena");
                if(ocenaBaza == 0){

                    sql ="UPDATE prijave " +
                            "SET ocena =' "+ocena
                            +" ' WHERE idPredmeta = '"+idPredmeta+"' AND idStudenta = '" + idStudenta+"'";

                    stmt.execute(sql);
                    break;
                }
            }

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }


}
