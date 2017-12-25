/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

import br.unesp.amoraes.dbvis.internals.SelectedData;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;

/**
 *
 * @author alessandro
 */
public class Metadata {
    private final SelectedData data;
    public Metadata(SelectedData data){
        this.data = data;
    }
    
    public String getXml(){
        String xml = "<collection>\n";
        xml += "<id>"+data.getColumnNames()[GraphViewer.getInstance().getIdColumn()]+"</id>\n";
        xml += "<label>"+data.getColumnNames()[GraphViewer.getInstance().getLabelColumn()]+"</label>\n";
        xml += "<columns>\n";
        for(int i = 0; i < data.getColumnCount(); i++){
            xml += "<column>\n"
                    + "<name>"+data.getColumnNames()[i]+"</name>\n"
                    + "<type>"+data.getColumnClasses()[i]+"</type>\n"
                    + "</column>\n";
        }
        xml += "</columns>\n</collection>";
        return xml;
    }
}
