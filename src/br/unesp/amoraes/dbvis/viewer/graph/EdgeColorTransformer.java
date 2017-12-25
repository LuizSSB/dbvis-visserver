/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.ui.palette.graph.GraphHelper;
import edu.uci.ics.jung.graph.Graph;
import java.awt.Color;
import java.awt.Paint;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class EdgeColorTransformer implements Transformer<Edge, Paint> {
    private final Graph graph;
    private final GraphViewer viewer;
    public EdgeColorTransformer(Graph g, GraphViewer v){
        this.graph  = g;
        this.viewer = v;
    }
    @Override
    public Paint transform(Edge edge) {
        if(viewer.getCountSelectedNodes() == 0){
            return edge.getSource().getColor();
        }
        if(edge.getSource().isSelected() || edge.getTarget().isSelected()){
            return edge.getSource().getColor();
        }
        return new Color(edge.getSource().getColor().getRed(), edge.getSource().getColor().getGreen(), edge.getSource().getColor().getBlue(),GraphViewer.ALPHA_EDGE);
    }

}
