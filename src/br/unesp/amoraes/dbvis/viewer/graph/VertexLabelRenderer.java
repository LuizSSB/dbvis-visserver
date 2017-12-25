/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;

/**
 *
 * @author alessandro
 */
public class VertexLabelRenderer extends DefaultVertexLabelRenderer
{
    protected Color unpickedVertexLabelColor = Color.WHITE;

    public VertexLabelRenderer(Color unpickedVertexLabelColor, Color pickedVertexLabelColor)
    {
        super(pickedVertexLabelColor);
        this.unpickedVertexLabelColor = unpickedVertexLabelColor;
    }

    public <V> Component getVertexLabelRendererComponent(JComponent vv, Object value, Font font, boolean isSelected, V vertex)
    {
        super.setForeground(unpickedVertexLabelColor);
        if (isSelected) setForeground(pickedVertexLabelColor);
        super.setBackground(vv.getBackground());
        if (font != null)
        {
            setFont(font);
        }
        else
        {
            setFont(vv.getFont());
        }
        setIcon(null);
        setBorder(noFocusBorder);
        setValue(value);
        return this;
    }
}
