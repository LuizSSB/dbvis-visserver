/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.rest.domain;

/**
 *
 * @author alessandro
 */
public class Device {
    private String name;
    private String token;
    private int x;
    private int y;
    private int width;
    private int height;
    private UserContents userContents;

    public Device(String name, String token) {
        this.name = name;
        this.token = token;
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        userContents = new UserContents();
    }

    public UserContents getUserContents() {
        return userContents;
    }

    public void setUserContents(UserContents userContents) {
        this.userContents = userContents;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

  

    
    
    
    
    
}
