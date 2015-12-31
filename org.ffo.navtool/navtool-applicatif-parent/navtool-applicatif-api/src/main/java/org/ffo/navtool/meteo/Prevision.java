package org.ffo.navtool.meteo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Prevision {

    @Id
    @GeneratedValue
    int id;

    @Column
    private Timestamp dateCreation;

    @Column
    private Timestamp dateDebut;

    @Column
    int validite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public int getValidite() {
        return validite;
    }

    public void setValidite(int validite) {
        this.validite = validite;
    }

}
