/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.internals;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alessandro
 */
public class SelectedRelationship {
    private String source;
    private String target;
    private boolean directed;

    public SelectedRelationship(String source, String target, boolean directed) {
        this.source = source;
        this.target = target;
        this.directed = directed;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }
    
    public static DefaultTableModel getTableModel(List<SelectedRelationship> list){
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("source");
        tableModel.addColumn("target");
        tableModel.addColumn("directed");
        int count = 0;
        for(SelectedRelationship line : list){
            count++;
            Object[] modelLine = new Object[3];
            modelLine[0] = line.source;
            modelLine[1] = line.target;
            modelLine[2] = line.directed;
            tableModel.addRow(modelLine);
        }
        return tableModel;
    }
}
