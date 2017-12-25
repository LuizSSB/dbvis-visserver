/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.ui.palette.graph.GraphHelper;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author alessandro
 */
public class LayeredPanel extends JPanel {
    private JLabel overLabel = null;
    private JLayeredPane layeredPane = null;
    private JLabel selection = null;

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }
    
    
    
    public LayeredPanel(Dimension d, final VisualizationViewer vv){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(d);
        layeredPane.setBackground(Color.BLACK);
        
        overLabel = new JLabel("Graph Viewer");
        overLabel.setVerticalAlignment(JLabel.TOP);
        overLabel.setHorizontalAlignment(JLabel.CENTER);
        overLabel.setOpaque(false);
        overLabel.setForeground(Color.WHITE);
        overLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        overLabel.setBounds(10,10, 300, 15);
        layeredPane.add(overLabel,1);
                
        vv.setBounds(0,0,new Double(d.getWidth()).intValue(),new Double(d.getHeight()).intValue());
        layeredPane.add(vv,2);
        add(layeredPane);
    }
    public void writeOnDisplay(String s){
        if(overLabel != null)
            overLabel.setText(s);
    }
    
    public void showAreaSelection(int x, int y, int width, int height){
        if(selection == null){
            selection = new JLabel("");
            selection.setOpaque(false);
            selection.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        }
        selection.setBounds(x, y, width, height);
        layeredPane.add(selection,1);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            
        }
        layeredPane.remove(selection);
    }
    
}
