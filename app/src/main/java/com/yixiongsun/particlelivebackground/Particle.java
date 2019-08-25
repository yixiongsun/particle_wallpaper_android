package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;

public class Particle {
    // Class that defines a single particle like colour, position, and others
    float radius;
    int size;
    float velocitySize;
    boolean sizeStatus;
    private int sizeSpeed;
    float positionX;
    float positionY;
    float velocityX, velocityY, initialVelocityX, initialVelocityY;

    private String colour;
    float opacityValue;
    boolean opacityAnimEnable;
    float velocityOpacity;
    boolean opacityStatus;
    ParticleShape shape;

    public Particle(ParticleSettings particleSettings, float width, float height) {

        // Size of particle
        this.radius = (float) ((particleSettings.randomSize ? Math.random() : 1) * particleSettings.size);
        if(particleSettings.sizeAnimate){
            this.sizeStatus = false;
            this.velocitySize = particleSettings.sizeAnimateSpeed / 100;
            if(!particleSettings.sizeAnimateSync){
                this.velocitySize *= Math.random();
            }
        }

        // Position of the particle
        // TODO: Add functionality of given positions instea of random
        this.positionX = (float) (Math.random() * width);
        this.positionY = (float) (Math.random() * height);

        // Check if position of particle is within the canvas and move it
        if(this.positionX > width - this.radius*2) this.positionX -= this.radius;
        else if(this.positionX < this.radius*2) this.positionX += this.radius;
        if(this.positionY > height - this.radius*2) this.positionY -= this.radius;
        else if(this.positionY < this.radius*2) this.positionY += this.radius;

        // Check if particles overlap
        if(particleSettings.bounce){
            // TODO: pJS.fn.vendors.checkOverlap(this, position);
        }

        // Colour of the particle, takes a random value out of the array
        this.colour = particleSettings.colour[(int) Math.floor(Math.random() * particleSettings.colour.length)];
        // TODO: Enable random colours

        // Opacity of the particle
        this.opacityValue = (float) ((particleSettings.randomOpacity ? Math.random() : 1) * particleSettings.opacityValue);
        if(particleSettings.opacityAnimate){
            this.opacityStatus = false;
            this.velocityOpacity = particleSettings.opactiyAnimateSpeed / 100;
            if(!particleSettings.opacityAnimateSync){
                this.velocityOpacity *= Math.random();
            }
        }

        // Velocity of partcle
        float ux, uy;
        switch(particleSettings.moveDirection){
            case TOP:
                ux = 0;
                uy = -1;
                break;
            case TOP_RIGHT:
                ux = 0.5f;
                uy = -0.5f;
                break;
            case RIGHT:
                ux = 1;
                uy = 0;
                break;
            case BOTTOM_RIGHT:
                ux = 0.5f;
                uy = 0.5f;
                break;
            case BOTTOM:
                ux = 0;
                uy = 1;
                break;
            case BOTTOM_LEFT:
                ux = -0.5f;
                uy = 0.5f;
                break;
            case LEFT:
                ux = -1;
                uy = 0;
                break;
            case TOP_LEFT:
                ux = -0.5f;
                uy = -0.5f;
                break;
            default:
                ux = 0;
                uy = 0;
                break;
        }

        if (particleSettings.moveStraight) {
            this.velocityX = ux;
            this.velocityY = uy;
            if (particleSettings.randomMove) {
                this.velocityX *= Math.random();
                this.velocityY *= Math.random();
            }
        } else{
            this.velocityX = (float) (ux + Math.random() - 0.5);
            this.velocityX = (float) (uy + Math.random() - 0.5);
        }

        this.initialVelocityX = this.velocityX;
        this.initialVelocityY = this.velocityY;

        // Shape of particle
        this.shape = particleSettings.shape;

    }

    //TODO: Implement the DRAW function from line 400 from particles.js
    public void draw(Canvas canvas) {

    }

}