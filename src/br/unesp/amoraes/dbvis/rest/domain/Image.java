/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alessandro
 */
@XmlRootElement
public class Image {
    private String id;
    private String url;
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    public String getXml(){
        String xml = "<image>\n"
                + "<id>"+id+"</id>\n"
                + "<url>"+url+"</url>\n"
                + "<time>"+time+"</time>\n"
                + "</image>\n";
        return xml;
    }
}
