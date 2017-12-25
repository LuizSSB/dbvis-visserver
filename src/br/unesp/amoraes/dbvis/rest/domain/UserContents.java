/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

import java.util.HashMap;

/**
 *
 * @author alessandro
 */
public class UserContents {
    private HashMap<Integer, UserText> texts = new HashMap<Integer, UserText>();
    private HashMap<Integer, UserChart> charts = new HashMap<Integer, UserChart>();

    public HashMap<Integer, UserText> getTexts() {
            return texts;
    }

    public void setTexts(HashMap<Integer, UserText> texts) {
            this.texts = texts;
    }

    public HashMap<Integer, UserChart> getCharts() {
        return charts;
    }

    public void setCharts(HashMap<Integer, UserChart> charts) {
        this.charts = charts;
    }
    
}
