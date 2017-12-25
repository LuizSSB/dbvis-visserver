/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.algorithm.graph;

/**
 *
 * @author alessandro
 */
public class Edge {
    private Node source;
    private Node target;
    private boolean directed = false;

    public Edge(Node source, Node target, boolean directed) {
        this.source = source;
        this.target = target;
        this.directed = directed;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }
    
    
}
