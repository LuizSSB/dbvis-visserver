/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.ui.palette.graph.GraphHelper;
import edu.uci.ics.jung.graph.Graph;
import java.awt.Color;
import java.awt.Paint;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class VertexColorTransformer  implements Transformer<Node, Paint> {
    private final Graph graph;
    private final GraphViewer viewer;
    public VertexColorTransformer(Graph g, GraphViewer v){
        this.graph = g;
        this.viewer = v;
    }
    @Override
    public Paint transform(Node n) {
        if(viewer.getCountSelectedNodes() == 0){
            return n.getColor();
        }
        if(n.isSelected()){
            return n.getColor();
        }else{
           for(Object nobj : graph.getNeighbors(n)) {
               if(((Node)nobj).isSelected()){
                   return n.getColor();
               }
           }
        }
        return new Color(n.getColor().getRed(), n.getColor().getGreen(), n.getColor().getBlue(), GraphViewer.ALPHA);
    }
}
    
