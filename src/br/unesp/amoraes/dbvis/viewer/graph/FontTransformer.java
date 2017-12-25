/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.KGlobal;
import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import java.awt.Font;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author alessandro
 */
public class FontTransformer implements Transformer<Node, Font>{
    @Override
    public Font transform(Node n) {
        return new Font("Arial", Font.PLAIN, KGlobal.DEFAULT_LABEL_FONT_SIZE+n.getSize());
    }
}