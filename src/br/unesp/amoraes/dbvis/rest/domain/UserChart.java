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
public class UserChart {

    private int id;
    private String text;
    private Date date;
    private String deviceName;
    private String fileName;
    private String nodesInfo;
    private String axisX;
    private String axisY;
    private String axisZ;
    private int type;
    private boolean deleted = false;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getNodesInfo() {
        return nodesInfo;
    }

    public void setNodesInfo(String nodesInfo) {
        this.nodesInfo = nodesInfo;
    }

    public String getAxisX() {
        return axisX;
    }

    public void setAxisX(String axisX) {
        this.axisX = axisX;
    }

    public String getAxisY() {
        return axisY;
    }

    public void setAxisY(String axisY) {
        this.axisY = axisY;
    }

    public String getAxisZ() {
        return axisZ;
    }

    public void setAxisZ(String axisZ) {
        this.axisZ = axisZ;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    
    public String getXml(){
        String xml = "<userChart>\n"
                + "<id>"+id+"</id>\n"
                + "<text>"+text+"</text>\n"
                + "<date>"+date.getTime()+"</date>\n"
                + "<deviceName>"+deviceName+"</deviceName>\n"
                + "<axisX>"+axisX+"</axisX>\n"
                + "<axisY>"+axisY+"</axisY>\n"
                + "<axisZ>"+axisZ+"</axisZ>\n"
                + "<type>"+type+"</type>\n"
                + "<deleted>"+deleted+"</deleted>\n"
                + "<nodesInfo><![CDATA["+nodesInfo.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "") +"]]></nodesInfo>\n"
                + "</userChart>\n";
        return xml;
    }
}
