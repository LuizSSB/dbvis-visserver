/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.algorithm.graph;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.graph.Graph;
import java.util.Iterator;



/**
 * A {@code Layout} implementation that positions vertices equally spaced on a regular circle.
 *
 * @author Masanori Harada
 */
public class EllipseLayout<V, E> extends AbstractLayout<V,E> {

	private double radius;
	private List<V> vertex_ordered_list;
	
	Map<V, CircleVertexData> circleVertexDataMap =
			LazyMap.decorate(new HashMap<V,CircleVertexData>(), 
			new Factory<CircleVertexData>() {
				public CircleVertexData create() {
					return new CircleVertexData();
				}});	

	/**
	 * Creates an instance for the specified graph.
	 */
	public EllipseLayout(Graph<V,E> g) {
		super(g);
	}

	/**
	 * Returns the radius of the circle.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sets the radius of the circle.  Must be called before
	 * {@code initialize()} is called.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Sets the order of the vertices in the layout according to the ordering
	 * specified by {@code comparator}.
	 */
	public void setVertexOrder(Comparator<V> comparator)
	{
	    if (vertex_ordered_list == null)
	        vertex_ordered_list = new ArrayList<V>(getGraph().getVertices());
	    Collections.sort(vertex_ordered_list, comparator);
	}

    /**
     * Sets the order of the vertices in the layout according to the ordering
     * of {@code vertex_list}.
     */
	public void setVertexOrder(List<V> vertex_list)
	{
	    if (!vertex_list.containsAll(getGraph().getVertices())) 
	        throw new IllegalArgumentException("Supplied list must include " +
	        		"all vertices of the graph");
	    this.vertex_ordered_list = vertex_list;
	}
	
	public void reset() {
		initialize();
	}

	public void initialize() 
	{
		Dimension d = getSize();
		
		if (d != null) 
		{
		    if (vertex_ordered_list == null) 
		        setVertexOrder(new ArrayList<V>(getGraph().getVertices()));

			double height = d.getHeight();
			double width = d.getWidth();

                        double a = (height > width)?height/2:width/2;
                        double b = (height < width)?height/2:width/2;
                        a = a - 100;
                        b = b - 100;
                        double x = width/2;
                        double y = height/2;
                        double angle = 0;
                        double beta = -angle * (Math.PI / 180);
                        double sinbeta = Math.sin(beta);
                        double cosbeta = Math.cos(beta);
                        int steps = getGraph().getVertexCount();
                        if(vertex_ordered_list.get(0) instanceof Node){
                            //if is my class node, compute steps based on size
                            steps = 0;
                            for(V v : vertex_ordered_list){
                                Node n = (Node)v;
                                steps += n.getSize();
                            }
                        }
                        
                        
                        Iterator<V> iter = vertex_ordered_list.iterator();
                        double i = 0.0;
                        while(i < 360.0)
			{
                            if(!iter.hasNext())
                                break;
                            V v = iter.next();
                            
                            double increment = 0.0;
                            if(v instanceof Node){
                                Double adjust = new Double(((Node)v).getSize());
                                increment = (360.0 / new Double(steps))*adjust;
                            }else{
                                increment = 360.0 / new Double(steps);
                            }
                            i = i + (increment)/2.0;
                            
                            Point2D coord = transform(v);

                            double alpha = i * (Math.PI / 180);
                            double sinAlpha = Math.sin(alpha);
                            double cosAlpha = Math.cos(alpha);

                            //double angle = (2 * Math.PI * i) / vertex_ordered_list.size();

                            //coord.setLocation(Math.cos(angle) * radius + width / 2,Math.sin(angle) * radius + height / 2);
                            double ax = x + (a * cosAlpha * cosbeta - b * sinAlpha * sinbeta);
                            double ay = y + (a * cosAlpha * sinbeta + b *sinAlpha * cosbeta);
                            coord.setLocation(ax, ay);

                            CircleVertexData data = getCircleData(v);
                            data.setAngle(angle);
                            
                            i = i + (increment/2.0);
			}
		}
	}

	protected CircleVertexData getCircleData(V v) {
		return circleVertexDataMap.get(v);
	}

	protected static class CircleVertexData {
		private double angle;

		protected double getAngle() {
			return angle;
		}

		protected void setAngle(double angle) {
			this.angle = angle;
		}

		@Override
		public String toString() {
			return "CircleVertexData: angle=" + angle;
		}
	}
}
