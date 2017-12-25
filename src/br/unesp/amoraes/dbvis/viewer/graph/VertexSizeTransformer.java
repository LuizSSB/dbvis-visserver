/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class VertexSizeTransformer implements Transformer<Node, Shape> {

    public VertexSizeTransformer() {
    }

    public Shape transform(Node n) {
        Ellipse2D circle = new Ellipse2D.Double(-15, -15, 30, 30);
        return AffineTransform.getScaleInstance(n.getSize()*0.2, n.getSize()*0.2).createTransformedShape(circle);
    }
    
}
