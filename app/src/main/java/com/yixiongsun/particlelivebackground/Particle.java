package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Particle {
    // Class that defines a single particle like colour, position, and others
    float radius;
    float velocitySize;
    boolean sizeStatus;
    float positionX;
    float positionY;
    float velocityX, velocityY, initialVelocityX, initialVelocityY;

    private String colour;
    float opacityValue;
    float velocityOpacity;
    boolean opacityStatus;
    ParticleShape shape;

    boolean bubbleRadius;
    float bubbleRadiusValue;
    boolean opacityRadius;
    float opacityRadiusValue;


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
            this.velocityOpacity = particleSettings.opacityAnimateSpeed / 100;
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
            this.velocityY = (float) (uy + Math.random() - 0.5);
        }

        this.initialVelocityX = this.velocityX;
        this.initialVelocityY = this.velocityY;

        // Shape of particle
        this.shape = particleSettings.shape;

    }

    //TODO: Implement the DRAW function from line 400 from particles.js
    public void draw(Canvas canvas, ParticleSettings particleSettings) {


        float radius, opacity;
        // Check for bubbling particles
        if(this.bubbleRadius){
            radius = this.bubbleRadiusValue;
        }else{
            radius = this.radius;
        }

        if(this.opacityRadius){
            opacity = this.opacityRadiusValue;
        }else{
            opacity = this.opacityValue;
        }




        // Paint
        Paint p1 = new Paint(), p2 = new Paint();
        p1.setColor(ParticleController.colourInt(this.colour, opacity));

        boolean drawStroke = false;
        if(particleSettings.strokeWith > 0){
            drawStroke = true;
            p2.setColor(ParticleController.colourInt(particleSettings.strokeColour, opacity));
            p2.setStrokeWidth(particleSettings.strokeWith);
            p2.setStyle(Paint.Style.STROKE);
        } else {
            p1.setStyle(Paint.Style.FILL);

        }

        switch(this.shape){
            case CIRCLE:
                canvas.drawCircle(this.positionX, this.positionY, radius, p1);
                if (drawStroke) canvas.drawCircle(this.positionX, this.positionY, radius, p2);
                break;

                // TODO: Implement other shapes
                /*
            case 'edge':
                pJS.canvas.ctx.rect(p.x-radius, p.y-radius, radius*2, radius*2);
                break;

            case 'triangle':
                pJS.fn.vendors.drawShape(pJS.canvas.ctx, p.x-radius, p.y+radius / 1.66, radius*2, 3, 2);
                break;

            case 'polygon':
                pJS.fn.vendors.drawShape(
                        pJS.canvas.ctx,
                        p.x - radius / (pJS.particles.shape.polygon.nb_sides/3.5), // startX
                        p.y - radius / (2.66/3.5), // startY
                        radius*2.66 / (pJS.particles.shape.polygon.nb_sides/3), // sideLength
                        pJS.particles.shape.polygon.nb_sides, // sideCountNumerator
                        1 // sideCountDenominator
                );
                break;

            case 'star':
                pJS.fn.vendors.drawShape(
                        pJS.canvas.ctx,
                        p.x - radius*2 / (pJS.particles.shape.polygon.nb_sides/4), // startX
                        p.y - radius / (2*2.66/3.5), // startY
                        radius*2*2.66 / (pJS.particles.shape.polygon.nb_sides/3), // sideLength
                        pJS.particles.shape.polygon.nb_sides, // sideCountNumerator
                        2 // sideCountDenominator
                );
                break;

            case 'image':

                function draw(){
                pJS.canvas.ctx.drawImage(
                        img_obj,
                        p.x-radius,
                        p.y-radius,
                        radius*2,
                        radius*2 / p.img.ratio
                );
            }

            if(pJS.tmp.img_type == 'svg'){
                var img_obj = p.img.obj;
            }else{
                var img_obj = pJS.tmp.img_obj;
            }

            if(img_obj){
                draw();
            }

            break;*/

        }


    }

}