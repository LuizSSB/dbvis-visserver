/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.ui.palette.graph;

import br.unesp.amoraes.dbvis.KGlobal;
import br.unesp.amoraes.dbvis.algorithm.graph.EllipseLayout;
import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.beans.DisplayEntity;
import br.unesp.amoraes.dbvis.dao.DisplayDAO;
import br.unesp.amoraes.dbvis.internals.CurrentRepresentation;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.internals.ScreenImage;
import br.unesp.amoraes.dbvis.internals.SelectedData;
import br.unesp.amoraes.dbvis.internals.SelectedRelationship;
import br.unesp.amoraes.dbvis.internals.ValueTextItem;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import com.sun.awt.AWTUtilities;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicRenderer;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.ArrowFactory;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.collections15.Transformer;






/**
 * A helper class with some methods to use on GraphPaletteIFrame
 *
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-08
 */
public class GraphHelper {

    
    
    


    public static DefaultComboBoxModel getLayoutAlgorithmComboBox() {
        DefaultComboBoxModel<ValueTextItem<GraphViewer.Algorithm>> model = new DefaultComboBoxModel<ValueTextItem<GraphViewer.Algorithm>>();
        for (GraphViewer.Algorithm a : GraphViewer.Algorithm.values()) {
            model.addElement(new ValueTextItem<GraphViewer.Algorithm>(a, a.getName()));
        }
        return model;
    }

    public static DefaultComboBoxModel getSizeComboBox(SelectedData selectedData) {
        DefaultComboBoxModel<ValueTextItem<Integer>> model = new DefaultComboBoxModel<ValueTextItem<Integer>>();
        model.addElement(new ValueTextItem<Integer>(-1, FunctionsHelper.getString("General.select")));

        if (selectedData == null) {
            return model;
        }
        //types of column allowed to be represented in size on the graph
        ArrayList<String> allowed = new ArrayList<String>(Arrays.asList(KGlobal.NUMBER_CLASSES));


        String[] columnNames = selectedData.getColumnNames();
        String[] columnClasses = selectedData.getColumnClasses();

        int columnNumber = 0;
        for (String columnClass : columnClasses) {
            if (allowed.contains(columnClass)) {
                model.addElement(new ValueTextItem<Integer>(columnNumber, columnNames[columnNumber]));
            }
            columnNumber++;
        }
        return model;
    }

    public static DefaultComboBoxModel getColorComboBox(SelectedData selectedData) {
        DefaultComboBoxModel<ValueTextItem<Integer>> model = new DefaultComboBoxModel<ValueTextItem<Integer>>();
        model.addElement(new ValueTextItem<Integer>(-1, FunctionsHelper.getString("General.select")));

        if (selectedData == null) {
            return model;
        }
        //types of column allowed to be represented in size on the graph
        ArrayList<String> allowed = new ArrayList<String>(Arrays.asList(KGlobal.NUMBER_CLASSES));
        allowed.add(String.class.getName());


        String[] columnNames = selectedData.getColumnNames();
        String[] columnClasses = selectedData.getColumnClasses();

        int columnNumber = 0;
        for (String columnClass : columnClasses) {
            if (allowed.contains(columnClass)) {
                model.addElement(new ValueTextItem<Integer>(columnNumber, columnNames[columnNumber]));
            }
            columnNumber++;
        }
        return model;
    }

    public static DefaultComboBoxModel getLabelComboBox(SelectedData selectedData) {
        DefaultComboBoxModel<ValueTextItem<Integer>> model = new DefaultComboBoxModel<ValueTextItem<Integer>>();
        model.addElement(new ValueTextItem<Integer>(-1, FunctionsHelper.getString("General.select")));

        if (selectedData == null) {
            return model;
        }

        String[] columnNames = selectedData.getColumnNames();
        String[] columnClasses = selectedData.getColumnClasses();

        int columnNumber = 0;
        for (String columnClass : columnClasses) {
            model.addElement(new ValueTextItem<Integer>(columnNumber, columnNames[columnNumber]));
            columnNumber++;
        }
        return model;
    }

    
    
}


class CustomKeyListener implements KeyListener {

     private JPanel visualizationServer = null;
     public CustomKeyListener(JPanel visualizationServer) {
         this.visualizationServer = visualizationServer;
     }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
         
        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
            System.out.println("Enstrou keytyped "+e.getKeyCode());
            try {
                BufferedImage i = ScreenImage.createImage(visualizationServer);
                ScreenImage.writeImage(i, "/home/alessandro/Pictures/test.jpg");
            } catch (IOException ex) {
                Logger.getLogger(CustomKeyListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}



