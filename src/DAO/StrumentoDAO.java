package DAO;

import DTO.Postazione;
import DTO.Sede;
import DTO.Strumento;
import UTILITIES.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StrumentoDAO {

    private DB_Connection DBConnection;
    Controller currController;


    public StrumentoDAO(Controller controller) {

        currController = controller;

        DBConnection = DB_Connection.getConnessione();

    }

    //Metodo per recuperare gli strumenti in base al nome della sede

    public List<Strumento> toolsRecovery (String nomeSede, String descrizioneStrumento) {

        List<Strumento> strumenti = new ArrayList<>();

        //Caso in cui sia sede che descrizione non sono vuoti

        if(!nomeSede.isEmpty() && !descrizioneStrumento.isEmpty()) {

            SedeDAO sedeDAO = new SedeDAO(currController);
            PostazioneDAO postazioneDAO = new PostazioneDAO(currController);

            try {

                String query = "SELECT * FROM strumento JOIN sede ON cods = codsede_fk WHERE descrizione ILIKE ? AND codsede_fk = ?";
                PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
                preparedStatement.setString(1, '%' + descrizioneStrumento + '%');
                preparedStatement.setInt(2, sedeDAO.codificaSedeDAO(nomeSede));
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    int codStrumento = resultSet.getInt("codstrumento");
                    String caratteristiche_tecniche = resultSet.getString("caratteristiche_tecniche");
                    String descrizione = resultSet.getString("descrizione");
                    Time tempoMaxUso = resultSet.getTime("tempomaxuso");
                    Integer codSede_fk = resultSet.getInt("codsede_fk");
                    String codPostazione_fk = resultSet.getString("codpostazione_fk");

                    //Recupero della Sede tramite l'attributo codsede_fk dello strumento
                    //Recupero allo stesso modo anche la postazione tramite metodi delle proprie classi DAO
                    Sede sede = sedeDAO.recuperoSede(codSede_fk);
                    Postazione postazione = postazioneDAO.workstationRecovery(codPostazione_fk);

                    // Creazione di un oggetto Strumento e aggiunta alla lista
                    Strumento strumento = new Strumento(codStrumento, caratteristiche_tecniche, descrizione, tempoMaxUso, postazione, sede);
                    strumenti.add(strumento);

                }

            } catch (SQLException e) {

                System.out.println("Errore nella ricerca degli strumenti tramite sede e descrizione");
                e.printStackTrace();

            }

        }

        else if(!nomeSede.isEmpty()) {

            SedeDAO sedeDAO = new SedeDAO(currController);
            PostazioneDAO postazioneDAO = new PostazioneDAO(currController);

            try {

                String query = "SELECT * FROM strumento WHERE codsede_fk = ?";
                PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
                preparedStatement.setInt(1, sedeDAO.codificaSedeDAO(nomeSede));
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    int codStrumento = resultSet.getInt("codstrumento");
                    String caratteristiche_tecniche = resultSet.getString("caratteristiche_tecniche");
                    String descrizione = resultSet.getString("descrizione");
                    Time tempoMaxUso = resultSet.getTime("tempomaxuso");
                    Integer codSede_fk = resultSet.getInt("codsede_fk");
                    String codPostazione_fk = resultSet.getString("codpostazione_fk");

                    //Recupero della Sede tramite l'attributo codsede_fk dello strumento
                    //Recupero allo stesso modo anche la postazione tramite metodi delle proprie classi DAO
                    Sede sede = sedeDAO.recuperoSede(codSede_fk);
                    Postazione postazione = postazioneDAO.workstationRecovery(codPostazione_fk);

                    // Creazione di un oggetto Strumento e aggiunta alla lista
                    Strumento strumento = new Strumento(codStrumento, caratteristiche_tecniche, descrizione, tempoMaxUso, postazione, sede);
                    strumenti.add(strumento);

                }

            } catch (SQLException e) {

                System.out.println("Errore nella ricerca degli strumenti tramite sede");
                e.printStackTrace();

            }

            //Caso in cui descrizione è diverso da null
        } else if(!descrizioneStrumento.isEmpty()) {

            SedeDAO sedeDAO = new SedeDAO(currController);
            PostazioneDAO postazioneDAO = new PostazioneDAO(currController);

            try {

                String query = "SELECT * FROM strumento WHERE descrizione ILIKE ?";
                PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
                preparedStatement.setString(1, '%' + descrizioneStrumento + '%');
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    int codStrumento = resultSet.getInt("codstrumento");
                    String caratteristiche_tecniche = resultSet.getString("caratteristiche_tecniche");
                    String descrizione = resultSet.getString("descrizione");
                    Time tempoMaxUso = resultSet.getTime("tempomaxuso");
                    Integer codSede_fk = resultSet.getInt("codsede_fk");
                    String codPostazione_fk = resultSet.getString("codpostazione_fk");

                    //Recupero della Sede tramite l'attributo codsede_fk dello strumento
                    //Recupero allo stesso modo anche la postazione tramite metodi delle proprie classi DAO
                    Sede sede = sedeDAO.recuperoSede(codSede_fk);
                    Postazione postazione = postazioneDAO.workstationRecovery(codPostazione_fk);

                    // Creazione di un oggetto Strumento e aggiunta alla lista
                    Strumento strumento = new Strumento(codStrumento, caratteristiche_tecniche, descrizione, tempoMaxUso, postazione, sede);
                    strumenti.add(strumento);

                }

            } catch (SQLException e) {

                System.out.println("Errore nella ricerca degli strumenti tramite descrizione");
                e.printStackTrace();

            }

        }

        return strumenti;

    }

    //Metodo per recuperare tutti gli strumenti

    public List<Strumento> allToolsRecoveryDAO () {

        List<Strumento> allStruments = new ArrayList<>();
        SedeDAO sedeDAO = new SedeDAO(currController);
        PostazioneDAO postazioneDAO = new PostazioneDAO(currController);

        try {

            String query = "SELECT * FROM strumento";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int codStrumento = resultSet.getInt("codstrumento");
                String caratteristiche_tecniche = resultSet.getString("caratteristiche_tecniche");
                String descrizione = resultSet.getString("descrizione");
                Time tempoMaxUso = resultSet.getTime("tempomaxuso");
                Integer codSede_fk = resultSet.getInt("codsede_fk");
                String codPostazione_fk = resultSet.getString("codpostazione_fk");

                //Recupero della Sede tramite l'attributo codsede_fk dello strumento
                //Recupero allo stesso modo anche la postazione tramite metodi delle proprie classi DAO
                Sede sede = sedeDAO.recuperoSede(codSede_fk);
                Postazione postazione = postazioneDAO.workstationRecovery(codPostazione_fk);

                // Creazione di un oggetto Strumento e aggiunta alla lista
                Strumento strumento = new Strumento(codStrumento, caratteristiche_tecniche, descrizione, tempoMaxUso, postazione, sede);
                allStruments.add(strumento);

            }

        } catch (SQLException e) {

            System.out.println("Errore nel recupero di tutti gli strumenti");
            e.printStackTrace();

        }

        return allStruments;

    }

    public Strumento toolRecovery(Integer codStrumento) {

        Strumento strumentoTrovato = null;
        SedeDAO sedeDAO = new SedeDAO(currController);
        PostazioneDAO postazioneDAO = new PostazioneDAO(currController);

        try {

            String query = "SELECT * FROM strumento WHERE codstrumento = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setInt(1, codStrumento);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Integer codStrumentoRec = resultSet.getInt("codstrumento");
                String caratteristicheTecnicheRec = resultSet.getString("caratteristiche_tecniche");
                String descrizioneRec = resultSet.getString("descrizione");
                Time tempoMaxUsoRec = resultSet.getTime("tempomaxuso");
                Integer codSede_fk = resultSet.getInt("codsede_fk");
                String codPostazione_fk = resultSet.getString("codpostazione_fk");

                //Recupero della Sede tramite l'attributo codsede_fk dello strumento
                //Recupero allo stesso modo anche la postazione tramite metodi delle proprie classi DAO
                Sede sede = sedeDAO.recuperoSede(codSede_fk);
                Postazione postazione = postazioneDAO.workstationRecovery(codPostazione_fk);

                strumentoTrovato = new Strumento(codStrumentoRec, caratteristicheTecnicheRec, descrizioneRec,
                        tempoMaxUsoRec, postazione, sede);

            }


        } catch (SQLException e) {

            System.out.println("Errore nel recupero dello strumento mediante il suo codice");
            e.printStackTrace();

        }

        return  strumentoTrovato;

    }

    public String toolSummaryDAO(int codStrumento, int mese, int anno ) {

        String risultato = null;
        int codStrumentoRec;
        int annoScelto = anno;
        int meseScelto = mese;

        try {

            String query = "SELECT * FROM riepilogo_strumenti(?, ?, ?)";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setInt(1, codStrumento);
            preparedStatement.setInt(2, mese);
            preparedStatement.setInt(3, anno);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                codStrumentoRec = resultSet.getInt("codstrumento");
                annoScelto = resultSet.getInt("anno");
                meseScelto = resultSet.getInt("mese");
                Time durataTotale = resultSet.getTime("durata_totale");
                String utenteMax = resultSet.getString("utentemax");

                risultato = "Strumento con codice: " + codStrumentoRec + "\n" +
                        " Periodo : " + meseScelto + "\\" + annoScelto + "\n" +
                        " Tempo totale d'uso: " + durataTotale + "\n" +
                        " Utilizzato maggiormente da: " + utenteMax;

            } else {

                System.out.println("Non trovato");

            }



        } catch (SQLException e) {

            System.out.println("Errore nel recupero del riepilogo dello strumento");
            e.printStackTrace();

            risultato =  "Lo strumento nel periodo: " + meseScelto + "\\" + annoScelto + "\n" +
                    "non è stato prenotato.";

        }

        return  risultato;

    }

    public String noMonthToolSummaryDAO(int codStrumento, int anno ) {

        String risultato = null;
        int codStrumentoRec;
        int annoScelto = anno;

        try {

            String query = "SELECT * FROM riepilogo_strumenti_noMese(?, ?)";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setInt(1, codStrumento);
            preparedStatement.setInt(2, anno);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                codStrumentoRec = resultSet.getInt("codstrumento");
                annoScelto = resultSet.getInt("anno");
                Time durataTotale = resultSet.getTime("durata_totale");
                String utenteMax = resultSet.getString("utentemax");

                risultato = "Strumento con codice: " + codStrumentoRec + "\n" +
                        " Anno : " + annoScelto + "\n" +
                        " Tempo totale d'uso: " + durataTotale + "\n" +
                        " Utilizzato maggiormente da: " + utenteMax;

            }

        } catch (SQLException e) {

            System.out.println("Errore nel recupero del riepilogo dello strumento senza mese");
            e.printStackTrace();

            risultato =  "Lo strumento nell'anno: " + annoScelto + "\n" +
                    "non è stato prenotato.";

        }

        return  risultato;

    }

}