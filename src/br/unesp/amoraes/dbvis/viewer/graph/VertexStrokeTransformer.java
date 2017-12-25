/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import java.awt.BasicStroke;
import java.awt.Stroke;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Alessandro Moraes
 * @since 2013-05-21
 */
public class VertexStrokeTransformer implements Transformer<Node,Stroke> {
    private Stroke s = new BasicStroke(1.0f);
    private Stroke sSelected = new BasicStroke(3f);
    @Override
    public Stroke transform(Node n) {
        if(n.isSelected()){
            return sSelected;
        }
        return s;
    }
}
