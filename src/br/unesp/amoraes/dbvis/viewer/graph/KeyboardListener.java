/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.viewer.graph;

import br.unesp.amoraes.dbvis.internals.CurrentRepresentation;
import br.unesp.amoraes.dbvis.internals.ScreenImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author alessandro
 */
public class KeyboardListener implements KeyListener {

     private JPanel visualizationViewer = null;
     public KeyboardListener(JPanel visualizationServer) {
         this.visualizationViewer = visualizationServer;
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
            //System.out.println("Entrou keyReleased "+e.getKeyCode());
            /*try {
                BufferedImage i = ScreenImage.createImage(visualizationServer);
                ScreenImage.writeImage(i, "/tmp/test.jpg");
            } catch (IOException ex) {
                Logger.getLogger(KeyboardListener.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            CurrentRepresentation.getInstance().setImage(ScreenImage.createImage(visualizationViewer));
            CurrentRepresentation.getInstance().setTime(new Date().getTime());
        }
    }
}


