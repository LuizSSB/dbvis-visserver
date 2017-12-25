/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.internals.SelectedData;

/**
 *
 * @author alessandro
 */
public class NodeData {
    private Node node;
    private SelectedData selectedData;
    private Integer idColumn;
    private Integer labelColumn;

    public NodeData(Node node, SelectedData selectedData, Integer idColumn, Integer labelColumn) {
        this.node = node;
        this.selectedData = selectedData;
        this.idColumn = idColumn;
        this.labelColumn = labelColumn;
    }
    
    public String getXml(){
        StringBuilder sb = new StringBuilder();
        sb.append("<Node>\n");
        sb.append("   <id>").append(selectedData.getValue(node.getRow(), idColumn)).append("</id>\n");
        sb.append("   <label>").append(selectedData.getValue(node.getRow(), labelColumn)).append("</label>\n");
        sb.append("   <data>\n");
        for(int c = 0; c < selectedData.getColumnCount(); c++){
            String columnName = selectedData.getColumnNames()[c];
            Object value = selectedData.getValue(node.getRow(), c);
            if(value instanceof String){
                value = correctStrings((String)value);
            }
            sb.append("      <_").append(columnName).append(">").append(value).append("</_").append(columnName).append(">\n");
        }
        sb.append("   </data>\n");
        sb.append("</Node>\n");
        return sb.toString();
    }
    
    private String correctStrings(String s){
        return s;
    }
}
