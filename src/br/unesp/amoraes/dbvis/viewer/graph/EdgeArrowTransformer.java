/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.util.ArrowFactory;
import java.awt.Shape;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class EdgeArrowTransformer implements Transformer<Context<Graph<Node, Edge>, Edge>, Shape> {
    private Shape a = ArrowFactory.getWedgeArrow(3.0f, 7.0f);
    @Override
    public Shape transform(Context<Graph<Node, Edge>, Edge> c) {
        return a;
    }

}
