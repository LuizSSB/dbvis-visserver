/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.KGlobal;
import br.unesp.amoraes.dbvis.algorithm.graph.Edge;
import br.unesp.amoraes.dbvis.algorithm.graph.EllipseLayout;
import br.unesp.amoraes.dbvis.algorithm.graph.Node;
import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.beans.DisplayEntity;
import br.unesp.amoraes.dbvis.dao.DisplayDAO;
import br.unesp.amoraes.dbvis.internals.CurrentRepresentation;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.internals.ScreenImage;
import br.unesp.amoraes.dbvis.internals.SelectedData;
import br.unesp.amoraes.dbvis.internals.SelectedRelationship;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Graph Viewer based on JUNG library
 * @author Alessandro Moraes
 * @since 2013-05-21
 */
public class GraphViewer {
    private static GraphViewer instance;
    
    public static GraphViewer getInstance(){
        return instance;
    }
    
    private int countSelectedNodes;
    private LayeredPanel layeredPanel;
    protected static final int ALPHA = 150; 
    protected static final int ALPHA_EDGE = 90;
    
    public static int SCALE_RED_TO_GREEN = 0;
    public static int SCALE_BLUE_TO_YELLOW = 1;
    public static int SCALE_BLACK_TO_WHITE = 2;
    private Integer idColumn;
    private SelectedData selectedData;
    private List<SelectedRelationship> relationships;
    private Integer labelColumn;
    private TreeMap<String, Node> nodes;
    
    private TreeMap<String, JLabel> devices = new TreeMap<String, JLabel>();  
    private VisualizationViewer<Node, Edge> vv;
    private AbstractLayout layout;

    public TreeMap<String, Node> getNodes() {
        return nodes;
    }
    
    public TreeMap<String, JLabel> getDevices(){
        return devices;
    }

    
    
    public Integer getIdColumn() {
        return idColumn;
    }

    public SelectedData getSelectedData() {
        return selectedData;
    }

    public Integer getLabelColumn() {
        return labelColumn;
    }
    
    
    
    

    private void calculateNodesSizes(SelectedData data, Integer sizeDimension, TreeMap<String, Node> nodes) throws NumberFormatException {
        int steps = 10;
        Double minValue = Double.MAX_VALUE;
        Double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < data.getRowCount(); i++) {
            Double d = new Double(data.getValue(i, sizeDimension).toString());
            if (d.doubleValue() < minValue) {
                minValue = d;
            }
            if(d.doubleValue() > maxValue){
                maxValue = d;
            }
        }
        for(Node n : nodes.values()){
            Double d = new Double(data.getValue(n.getRow(), sizeDimension).toString());
            Double total = maxValue - minValue; 
            Double interval = total / steps;
            int size = 1;
            for(int s = 0; s < steps; s++){
                double stepMin = minValue + interval * s;
                double stepMax = minValue + interval * (s+1);
                if((d >= stepMin && d < stepMax) || (d >= stepMin && s == steps-1)){
                    size = s + 1;
                    break;
                }
            }     
            n.setSize(size);
        }
    }

    private void calculateNodesColors(String scaleOrder, SelectedData data, Integer colorDimension, Integer colorDimensionScale, TreeMap<String, Node> nodes) throws NumberFormatException {
        Float minValue = Float.MAX_VALUE;;
        Float maxValue = Float.MIN_VALUE;
        boolean invert = (scaleOrder.equals("ASC") ? false : true);
        Color color1 = null;
        Color color2 = null;
        for (int i = 0; i < data.getRowCount(); i++) {
            Float f = new Float(data.getValue(i, colorDimension).toString());
            if (f.floatValue() < minValue) {
                minValue = f;
            }
            if (f.floatValue() > maxValue) {
                maxValue = f;
            }
        }
        if (colorDimensionScale == SCALE_RED_TO_GREEN) {
            color1 = Color.RED;
            color2 = Color.GREEN;
        }
        if (colorDimensionScale == SCALE_BLUE_TO_YELLOW) {
            color1 = Color.BLUE;
            color2 = Color.YELLOW;
        }
        if (colorDimensionScale == SCALE_BLACK_TO_WHITE) {
            color1 = Color.BLACK;
            color2 = Color.WHITE;
        }
        //calcula a cor dos nós
        for(Node n : nodes.values()){
            Float f = new Float(data.getValue(n.getRow(), colorDimension).toString());
            Float oldRange = (maxValue - minValue);
            Float newRange = 100f;
            Float value = (((f - minValue) * newRange) / oldRange) + 0;
            if (!invert) {
                Color c = new Color(FunctionsHelper.linearColorInterpolate(color1.getRGB(), color2.getRGB(), value.intValue(), 100));
                n.setColor(c);
            } else {
                Color c = new Color(FunctionsHelper.linearColorInterpolate(color2.getRGB(), color1.getRGB(), value.intValue(), 100));
                n.setColor(c);
            }
        }
    }

    private void calculateNodesColorsQualitative(TreeMap<String, Node> nodes, SelectedData data, Integer colorDimension) {
        TreeMap<Object, Color> colorMap = new TreeMap<Object,Color>();
        int colorIndex = 0;
         for(Node n : nodes.values()){
            Object value = data.getValue(n.getRow(), colorDimension.intValue());
            if(colorMap.containsKey(value)){
                n.setColor(colorMap.get(value));
            }else{
                Color c = KGlobal.COLORS_FOR_CATEGORIES[colorIndex];
                colorMap.put(value, c);
                n.setColor(c);
                colorIndex++;
            }
        }
    }
    
    
    
    public VisualizationViewer<Node, Edge> getVisualizationViewer(){
        return vv;
    }
    
    public AbstractLayout getLayout(){
        return layout;
    }
    
    public int getCountSelectedNodes(){
        return countSelectedNodes;
    }

    public String exportXml() {
        String xml = "<graph>\n";
        xml += "<nodes>\n";
        for(int i = 0; i < selectedData.getRowCount(); i++){
            String id = selectedData.getValue(i, idColumn).toString();
            Node node = nodes.get(id);
            xml += "<node>\n";
            xml += "<id>"+id+"</id>\n";
            xml += "<label>"+selectedData.getValue(i, labelColumn)+"</label>\n";
            xml += "<x>"+layout.getX(node)+"</x>\n";
            xml += "<y>"+layout.getY(node)+"</y>\n";
            xml += "<size>"+node.getSize()+"</size>\n";
            xml += "<color>"+node.getColor().getRed()+","+node.getColor().getGreen()+","+node.getColor().getBlue()+"</color>\n";
            xml += "<properties>\n";
            for(int c = 0; c < selectedData.getColumnCount(); c++){
                if(c == idColumn || c == labelColumn){ 
                    continue;
                }
                String columnName = selectedData.getColumnNames()[c];
                Object value = selectedData.getValue(node.getRow(), c);
                String columnClass = selectedData.getColumnClasses()[c];
                xml += "<property name=\""+columnName+"\" type=\""+columnClass+"\">"+value+"</property>\n";
            }
            xml += "</properties>\n";
            xml += "</node>\n";
        }
        xml += "</nodes>\n";
        xml += "<edges>\n";
        for(int i = 0; i < relationships.size(); i++){
            xml += "<edge>\n";
            xml += "<source>"+relationships.get(i).getSource()+"</source>\n";
            xml += "<target>"+relationships.get(i).getTarget()+"</target>\n";
            xml += "<directed>"+((relationships.get(i).isDirected())?"true":"false")+"</directed>\n";
            xml += "</edge>\n";
        }
        xml += "</edges>\n";
        xml += "</graph>";
        return xml;
    }
    /**
     * Layout algorithms available
     */
    public enum Algorithm {

        CIRCLE_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                CircleLayout l = new CircleLayout<Node, Edge>(g);
                l.setVertexOrder(new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Node n1 = (Node)o1;
                        Node n2 = (Node)o2;
                        return n1.getRow().compareTo(n2.getRow());
                    }
                });
                return l;
            }

            @Override
            public String getName() {
                return "Circle";
            }
        },
        ELLIPSE_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                EllipseLayout l = new EllipseLayout<Node, Edge>(g);
                l.setVertexOrder(new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Node n1 = (Node)o1;
                        Node n2 = (Node)o2;
                        return n1.getRow().compareTo(n2.getRow());
                    }
                });
                return l;
            }

            @Override
            public String getName() {
                return "Ellipse";
            }
        },
        /*FR_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                return new FRLayout<Node,Edge>(g);
            }

            @Override
            public String getName() {
                return "Force Directed";
            }
        },
        FR2_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                return new FRLayout2<Node,Edge>(g);
            }

            @Override
            public String getName() {
                return "Force Directed 2";
            }
        },*/
        ISOM_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                return new ISOMLayout<Node,Edge>(g);
            }

            @Override
            public String getName() {
                return "Self-Organizing Map";
            }
        },
        KK_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                return new KKLayout<Node,Edge>(g);
            }

            @Override
            public String getName() {
                return "Kamada-Kawai";
            }
        },
        SPRING_LAYOUT {
            @Override
            public AbstractLayout getLayoutInstace(Graph g) {
                return new SpringLayout<Node,Edge>(g);
            }

            @Override
            public String getName() {
                return "Spring";
            }
        };

        public abstract AbstractLayout getLayoutInstace(Graph g);

        public abstract String getName();
    }

    /**
     * Create and show the graph
     * @param data
     * @param idColumn
     * @param sqlRelationships
     * @param databaseConnection
     * @param sizeDimension
     * @param colorDimension
     * @param colorDimensionQualitative
     * @param colorDimensionScale
     * @param scaleOrder
     * @param layoutAlgorithm
     * @param alwaysShowLabel
     * @param labelColumn 
     */
    
    public static void create(SelectedData data, 
            Integer idColumn, 
            String sqlRelationships, 
            DatabaseConnectionEntity databaseConnection,  
            Integer sizeDimension, 
            Integer colorDimension, 
            Boolean colorDimensionQualitative, 
            Integer colorDimensionScale, 
            String scaleOrder, 
            Algorithm layoutAlgorithm, 
            Boolean alwaysShowLabel, 
            Integer labelColumn){
        instance = new GraphViewer(data, idColumn, sqlRelationships, databaseConnection, sizeDimension, colorDimension, colorDimensionQualitative, colorDimensionScale, scaleOrder, layoutAlgorithm, alwaysShowLabel, labelColumn);
    }
    
    private GraphViewer(SelectedData data, 
            Integer idColumn, 
            String sqlRelationships, 
            DatabaseConnectionEntity databaseConnection,  
            Integer sizeDimension, 
            Integer colorDimension, 
            Boolean colorDimensionQualitative, 
            Integer colorDimensionScale, 
            String scaleOrder, 
            Algorithm layoutAlgorithm, 
            Boolean alwaysShowLabel, 
            Integer labelColumn) {
        
        //get the config of the active display
        DisplayEntity activeDisplay = DisplayDAO.getActive();
        //current selected nodes is zero
        countSelectedNodes = 0;
        this.idColumn = idColumn;
        this.labelColumn = labelColumn;
        this.selectedData = data;
        //create a sparse graph
        final SparseMultigraph<Node, Edge> graph = new SparseMultigraph<Node, Edge>();
        //create a map with all nodes based with the value of Id column as key
        nodes = new TreeMap<String, Node>();
        for (int i = 0; i < data.getRowCount(); i++) {
            String id = data.getValue(i, idColumn).toString();
            Node n = new Node(id,i);
            nodes.put(id, n);
            graph.addVertex(n);
        }
        
        //try to connect to the database to retrieve the edges of each node
        Connection conn = null;
        try{
            conn = FunctionsHelper.getJDBCConnection(databaseConnection);
        }catch(SQLException ex){
            FunctionsHelper.logOnConsole(FunctionsHelper.getString("DataSelectIFrame.message.SQLError")+" ("+ex.getMessage()+")", Color.RED);
            return;
        }
        relationships = new ArrayList<>();
        for (int i = 0; i < data.getRowCount(); i++) {
            List<SelectedRelationship> rels = FunctionsHelper.getRelationshionship(data, i, idColumn, sqlRelationships, conn);
            for(SelectedRelationship r : rels){
                relationships.add(r);
                Node source = nodes.get(r.getSource());
                Node target = nodes.get(r.getTarget());
                if(source != null && target != null){
                    graph.addEdge(new Edge(source, target, r.isDirected()), source, target, r.isDirected()?EdgeType.DIRECTED:EdgeType.UNDIRECTED);
                }
            }
        }
        //calculate the size of all nodes
        if (sizeDimension != null) {
            calculateNodesSizes(data, sizeDimension, nodes); 
        }
        
        //set color of all nodes
        if (colorDimension != null && colorDimensionQualitative != null
                && colorDimensionQualitative == false
                && colorDimensionScale != null) {
            calculateNodesColors(scaleOrder, data, colorDimension, colorDimensionScale, nodes);
        }else if (colorDimension != null && colorDimensionQualitative != null
                && colorDimensionQualitative == true) {
            calculateNodesColorsQualitative(nodes, data, colorDimension);
        }

        layout = layoutAlgorithm.getLayoutInstace(graph);
        
        
        vv = new VisualizationViewer<Node, Edge>(layout,  new Dimension(activeDisplay.getWidth(), activeDisplay.getHeight()));
        vv.getRenderContext().setVertexShapeTransformer(new VertexSizeTransformer());
       
        vv.getRenderContext().setVertexFillPaintTransformer(new VertexColorTransformer(graph,this));
        vv.getRenderContext().setVertexDrawPaintTransformer(new VertexBorderColorTransformer(graph,this));
        vv.getRenderContext().setEdgeDrawPaintTransformer(new EdgeColorTransformer(graph,this));
        vv.getRenderContext().setEdgeArrowTransformer(new EdgeArrowTransformer());
        vv.getRenderContext().setArrowFillPaintTransformer(new EdgeColorTransformer(graph,this));
        vv.getRenderContext().setArrowDrawPaintTransformer(new EdgeColorTransformer(graph,this));
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Node, Edge>());
        vv.getRenderContext().setVertexLabelTransformer(new VertexLabelTransformer(data, labelColumn,graph,alwaysShowLabel));
        BasicVertexLabelRenderer<Node,Edge> vertexLabelRenderer = new BasicVertexLabelRenderer(Renderer.VertexLabel.Position.S);
        vv.getRenderer().setVertexLabelRenderer(vertexLabelRenderer);
        vv.getRenderContext().setVertexLabelRenderer(new VertexLabelRenderer(Color.WHITE, Color.WHITE));
        vv.getRenderContext().setVertexFontTransformer(new FontTransformer());
        vv.getRenderContext().setVertexStrokeTransformer(new VertexStrokeTransformer());
        
        DefaultModalGraphMouse graphMouse = new GraphMouseCustom();
        graphMouse.setMode(edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);

        
        final PickedState<Node> pickedState = vv.getPickedVertexState();
        pickedState.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            Object subject = e.getItem();
                // The graph uses Integers for vertices.
                if (subject instanceof Node) {
                    Node n = (Node) subject;
                    if (pickedState.isPicked(n)) {
                        n.setSelected(true);
                        countSelectedNodes++;
                        layeredPanel.writeOnDisplay(FunctionsHelper.getString("GraphViewer.vertexSelected", new String[]{n.getId(),layout.getX(n)+"",layout.getY(n)+""}));
                    } else {
                        layeredPanel.writeOnDisplay(FunctionsHelper.getString("GraphViewer.vertexUnselected", new String[]{n.getId()}));
                        n.setSelected(false);
                        Collection<Node> all = graph.getNeighbors(n);
                        countSelectedNodes--;
                    }
                }
            }
        });
        vv.setBackground(Color.BLACK);
        JFrame frame = new JFrame();
        layeredPanel = new LayeredPanel(new Dimension(activeDisplay.getWidth(), activeDisplay.getHeight()),vv);
        frame.getContentPane().add(layeredPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.setUndecorated(true);
        frame.setLocation(0, 0);
        frame.setResizable(false);
        
        
        vv.addKeyListener(new KeyboardListener(vv));
        frame.addWindowListener(new OnCloseWindowListener());
        
        frame.pack();
        
        frame.setVisible(true);
        frame.setState(JFrame.NORMAL);
        
        
        
        
        

        
        
        
        CurrentRepresentation.getInstance().setImage(ScreenImage.createImage(vv));
        CurrentRepresentation.getInstance().setTime(new Date().getTime());
        
        
    }

    public void showDevice(int x, int y, int widht, int height, String device){
        JLabel jLabel = null;
        if(devices.containsKey(device)){
            jLabel = devices.get(device);
            jLabel.setBounds(x,y, widht, height);
        }else{
            jLabel = new JLabel();
            jLabel.setText(device);
            jLabel.setVerticalAlignment(JLabel.TOP);
            jLabel.setHorizontalAlignment(JLabel.LEFT);
            jLabel.setOpaque(false);
            jLabel.setForeground(new Color(1f, 1f, 1f, .5f));
            jLabel.setBorder(BorderFactory.createLineBorder(new Color(1f, 1f, 1f, .3f)));
            jLabel.setBounds(x,y, widht, height);
            devices.put(device, jLabel);
            layeredPanel.getLayeredPane().add(jLabel,1);
            
        }
        
    }
    
    public void removeDevice(String device){
        if(devices.containsKey(device)){
            JLabel jLabel = devices.get(device);
            devices.remove(device);
            layeredPanel.getLayeredPane().remove(jLabel);
        }
    }
    
    public void showAreaSelection(int x, int y, int width, int height){
        layeredPanel.showAreaSelection(x, y, width, height);
    }
    
    
    class OnCloseWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
                
        }
    }
}
