package DTO;

public class Team {
    private String codTeam;
    private String nome;
    private String descrizione;
    private String matricolaL;
    private Responsabile codR_fk;
    private DTO.Responsabile Responsabile;

    public Team(String codTeam,String nome,String des,String matricolaLeader, Responsabile res) {
        this.codTeam = codTeam;
        this.nome = nome;
        this.descrizione = des;
        this.matricolaL = matricolaLeader;
        this.codR_fk = res;
    }

    public String getCodTeam() {
        return this.codTeam;
    }

    public void setCodTeam(String codTeam) {
        this.codTeam = codTeam;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMatricolaL() {
        return this.matricolaL;
    }

    public void setMatricolaL(String matricolaL) {
        this.matricolaL = matricolaL;
    }

    public Responsabile getResponsabile() {
        return this.Responsabile;
    }

    public void setResponsabile(Responsabile responsabile) {
        this.codR_fk = responsabile;
    }
}