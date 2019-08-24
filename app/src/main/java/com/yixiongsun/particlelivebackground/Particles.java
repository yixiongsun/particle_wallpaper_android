package com.yixiongsun.particlelivebackground;

public class Particles {
    // Class that defines a single particle like colour, position, and others
    int radius;
    int size;
    int velocitySize;
    boolean sizeStatus;
    private int sizeSpeed;
    double positionX;
    double positionY;
    double velocityX;
    double velocityY;
    private String colour;
    double opacityValue;
    boolean opacityAnimEnable;
    int velocityOpacity;
    boolean opacityStatus;

    public Particles(String colour, double opacityValue, double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.colour = colour;
        this.opacityValue = opacityValue;
    }

    //TODO: Implement the DRAW function from line 400 from particles.js
    
}