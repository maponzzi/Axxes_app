package com.axxesinternational.vias.axxes.models;

import com.axxesinternational.vias.axxes.utils.DateUtils;

/**
 * Created by Miguel on 17/01/15.
 */
public class Plano {

    private String codb;
    private String herrNombre;
    private String fUltMant;
    private String sttHrj;
    private String ubicacion;


    public String getCodb() {
        return codb;
    }

    public void setCodb(String codb) {
        this.codb = codb;
    }

    public String getHerrNombre() {
        return herrNombre;
    }

    public void setHerrNombre(String herrNombre) {
        this.herrNombre = herrNombre;
    }

    public String getfUltMant() {
        return fUltMant;
    }

    public void setfUltMant(String fUltMant) {
        this.fUltMant = fUltMant;
    }

    public String getSttHrj() {
        return sttHrj;
    }

    public void setSttHrj(String sttHrj) {
        this.sttHrj = sttHrj;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
