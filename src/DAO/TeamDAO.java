package DAO;

import DTO.Responsabile;
import DTO.Team;
import DTO.Tecnico;
import UTILITIES.Controller;
import UTILITIES.DB_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    private DB_Connection DBConnection;
    Controller currController;


    public TeamDAO(Controller controller) {

        currController = controller;

        DBConnection = DB_Connection.getConnessione();

    }

    public boolean teamRecoveryWithCode(String codTeam) {

        boolean teamTrovato = false;

        try {

            String query = "SELECT * FROM Team WHERE codTeam = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setString(1, codTeam);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                teamTrovato = true;

            }

        } catch (SQLException e) {

            System.out.println("Errore nella ricerca del Team");
            e.printStackTrace();

        }

        return teamTrovato;

    }

    public Team teamRecoveryDAO(String codT) {

        Team team = null;
        String codTeam;
        String nome;
        String descrizione;
        String matricolaL;
        int n_tecnici;
        String codR_fk;

        try {

            String query = "SELECT * FROM Team WHERE codTeam = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setString(1, codT);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                codTeam = resultSet.getString("codTeam");
                nome = resultSet.getString("nome");
                descrizione = resultSet.getString("descrizione");
                matricolaL = resultSet.getString("matricolaL");
                n_tecnici = resultSet.getInt("n_tecnici");
                codR_fk = resultSet.getString("codR_fk");

                ResponsabileDAO responsabileDAO = new ResponsabileDAO(currController);
                Responsabile responsabile = responsabileDAO.managerRecoveryDAO(codR_fk);

                team = new Team(codTeam, nome, descrizione,matricolaL,n_tecnici,responsabile);

            }

        } catch (SQLException e) {

            System.out.println("Errore nella ricerca del Team");
            e.printStackTrace();

        }

        return  team;

    }

    public List<Team> teamRecoverysDalDB(Responsabile responsabile) {

        List<Team> teams = new ArrayList<>();

        try {

            String query = "SELECT * FROM team where codR_fk = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setString(1,responsabile.getMatricola());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String codTeam = resultSet.getString("codTeam");
                String nomeTeam = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");
                String matricolaL = resultSet.getString("matricolaL");
                Integer n_tecnici = resultSet.getInt("n_tecnici");
                String codR_fk = resultSet.getString("codR_fk");

                ResponsabileDAO responsabileDAO = new ResponsabileDAO(currController);
                Responsabile responsabile1 = responsabileDAO.managerRecoveryDAO(codR_fk);

                Team team = new Team(codTeam, nomeTeam, descrizione,matricolaL,n_tecnici,responsabile1);
                teams.add(team);

            }

        } catch (SQLException e) {

            System.out.println("Errore durante il recupero dei team dal DB");
            e.printStackTrace();

        }

        return teams;

    }

    //////////////////////////////////////INSERT TEAM////////////////////////////////////////////
    public boolean newTeamInsertDAO(String codTeam, String nome, String descrizione, String matricolaL, Integer n_tecnici, Responsabile codR_fk) {

        String query = "INSERT INTO TEAM (Codteam, nome, descrizione ,matricolal, n_tecnici ,codr_fk) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setString(1, codTeam);
            preparedStatement.setString(2, nome);
            preparedStatement.setString(3, descrizione);
            preparedStatement.setString(4, matricolaL);
            preparedStatement.setInt(5, n_tecnici);
            preparedStatement.setString(6, codR_fk.getMatricola());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

    }

    public void deleteTeamDAO(String codteam) {

        TecnicoDAO tecnicoDAO = new TecnicoDAO(currController);
        Team team = teamRecoveryDAO(codteam); // Recupera il team tramite il codice

        List<Tecnico> tecniciDelTeam = tecnicoDAO.recuperoTecniciDalDB(team);

        // Dissocia i tecnici dal team impostando la chiave esterna a null
        for (Tecnico tecnico : tecniciDelTeam) {

            tecnico.setTeam(null);
            tecnicoDAO.techniciansUpdateDAO(tecnico, null); // Aggiorna la chiave esterna a null nel database

        }

        try {

            String query = "DELETE FROM team WHERE codteam = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);
            preparedStatement.setString(1, codteam);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Errore nell'eliminazione del team ");
            e.printStackTrace();

        }

    }

    public void updateTeamLeaderDAO(String codTeam, String leader) {

        try {

            String query = "UPDATE team SET matricolal = ? WHERE codteam = ?";
            PreparedStatement preparedStatement = DBConnection.getPreparedStatement(query);

            preparedStatement.setString(1, leader);
            preparedStatement.setString(2, codTeam);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Errore durante l'aggiornamento del leader del team");
            e.printStackTrace();

        }

    }

}