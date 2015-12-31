package org.ffo.navtool.meteo;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Objet représentant une case de vent : vitesse et direction. on symbolise un
 * vent allant du nord au sud. L'angle varie positivement dans le sens des
 * auguille d'une montre. Ainsi un vent ouest->Est est de 90° Unité = degrés
 * 
 * @author Fred
 *
 */
@Entity
public class PrevisionParPointGPS {

    private Prevision prevision;

    /**
     * Vitesse en m/s
     */
    @Column
    private float vitesse;

    /**
     * angle en degré.
     */
    @Column
    private float angle;

    @Column
    private float latitude;

    @Column
    private float longitude;

    public PrevisionParPointGPS() {
        super();
    }

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
