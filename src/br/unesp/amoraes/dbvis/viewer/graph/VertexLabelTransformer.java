/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.internals.SelectedData;
import edu.uci.ics.jung.graph.Graph;
import java.util.Collection;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class VertexLabelTransformer  implements Transformer<Node, String> {
    
    private final SelectedData selectedData;
    private final Integer labelColumn;
    private final Boolean alwaysShowLabel;
    private final Graph graph;
    
    public VertexLabelTransformer(SelectedData data, Integer labelColumn, Graph g, Boolean alwaysShowLabel){
        this.selectedData = data;
        this.labelColumn = labelColumn;
        this.alwaysShowLabel = alwaysShowLabel;
        this.graph = g;
    }
    
    public String transform(Node n){
        if(n.isSelected() || alwaysShowLabel)
            return selectedData.getValue(n.getRow(), labelColumn).toString();
        //verify if a neighbor is selected
        Collection<Node> neighs = graph.getNeighbors(n);
        for(Node ne : neighs){
            if(ne.isSelected())
                return selectedData.getValue(n.getRow(), labelColumn).toString();
        }
        return "";
    }
}
    
