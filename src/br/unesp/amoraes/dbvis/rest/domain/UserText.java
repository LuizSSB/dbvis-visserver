/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

import java.util.Date;

/**
 *
 * @author alessandro
 */
public class UserText {

    private int id;
    private int x;
    private int y;
    private String text;
    private Date date;
    private String deviceName;
    private boolean deleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
    
    public String getXml(){
        String xml = "<userText>\n"
                + "<id>"+id+"</id>\n"
                + "<x>"+x+"</x>\n"
                + "<y>"+y+"</y>\n"
                + "<text>"+text+"</text>\n"
                + "<date>"+date.getTime()+"</date>\n"
                + "<deviceName>"+deviceName+"</deviceName>\n"
                + "<deleted>"+deleted+"</deleted>\n"
                + "</userText>\n";
        return xml;
    }
}
