/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author alessandro
 */
public class GraphMouseCustom extends DefaultModalGraphMouse<Node, Edge>{

    public GraphMouseCustom(){
        super();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Mouse click em: "+e.getX()+","+e.getY()+". Botão: "+e.getButton());
        redrawPositions();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e); //To change body of generated methods, choose Tools | Templates.
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Mouse press em: "+e.getX()+","+e.getY()+". Botão: "+e.getButton());
        redrawPositions();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
        Logger.getLogger(FunctionsHelper.class.getName()).log(Level.INFO, "Mouse released em: "+e.getX()+","+e.getY()+". Botão: "+e.getButton());
        redrawPositions();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        return; //disable zoom
    }
    
    private void redrawPositions(){
        Collection<JLabel> devices = GraphViewer.getInstance().getDevices().values();
        for(JLabel device : devices){
            device.repaint();
        }
    }
    
    
    
    
    
    
}
