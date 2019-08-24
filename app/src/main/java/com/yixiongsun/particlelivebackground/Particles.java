package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;

public class Particles {
    // Class that defines a single particle like colour, position, and others
    int radius;
    int size;
    int velocitySize;
    boolean sizeStatus;
    private int sizeSpeed;
    float positionX;
    float positionY;
    float velocityX;
    float velocityY;
    private String colour;
    float opacityValue;
    boolean opacityAnimEnable;
    int velocityOpacity;
    boolean opacityStatus;

    public Particles(String colour, float opacityValue, float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.colour = colour;
        this.opacityValue = opacityValue;
    }

    //TODO: Implement the DRAW function from line 400 from particles.js
    public void draw(Canvas canvas) {

    }

}