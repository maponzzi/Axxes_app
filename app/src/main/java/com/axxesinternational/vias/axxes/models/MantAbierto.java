package com.axxesinternational.vias.axxes.models;

import com.axxesinternational.vias.axxes.utils.DateUtils;

/**
 * Created by Miguel on 17/01/15.
 */
public class MantAbierto {
    private String fol;
    private String date;
    private String obser;

    public String getFol() {
        return fol;
    }

    public void setFol(String fol) {
        this.fol = fol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = DateUtils.setDateFormat(date);
    }

    public String getObser() {
        return obser;
    }

    public void setObser(String obser) {
        this.obser = obser;
    }

}
