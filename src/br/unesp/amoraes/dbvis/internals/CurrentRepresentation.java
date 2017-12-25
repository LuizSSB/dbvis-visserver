/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.internals;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author alessandro
 */
public class CurrentRepresentation {
    private static CurrentRepresentation instance;
    private BufferedImage imageFar = null;
    private BufferedImage image = null;
    private BufferedImage imageClose = null;
    private Long time = null;
    
    private CurrentRepresentation(){}
    
    public static CurrentRepresentation getInstance(){
        if(instance == null)
            instance = new CurrentRepresentation();
        return instance;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getImageFar() {
        return imageFar;
    }

    public BufferedImage getImageClose() {
        return imageClose;
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
        this.imageClose = FunctionsHelper.resizeImage((Image)image, image.getWidth()*2, image.getHeight()*2);
        this.imageFar = FunctionsHelper.resizeImage((Image)image, image.getWidth()/2, image.getHeight()/2);
        
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
    
    
}
