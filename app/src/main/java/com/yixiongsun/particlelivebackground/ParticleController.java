package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;

import java.util.HashSet;

public class ParticleController {

    private double moveSpeed;
    private boolean animateOpacity;
    private double opacityValue;
    private double opacityMin;
    private double sizeValue;
    private double sizeMin;
    private String moveOutMode;

    // Local variables
    HashSet<Particles> particles;


    // Default constructor
    public ParticleController() {
        this.particles = new HashSet<Particles>();
    }

    // Constructor from particles
    public ParticleController(HashSet<Particles> particles) {
        this.particles = particles;
    }



    // Function to create new particles
    public void createParticles(int number, double width, double height) {
        for (int i = 0; i < number; i++) {
            // Create the particle and add it to the Set
            // Dummy variables for colours and opacity
            String[] colours = {"#ffffff"};
            double opacity = 1;

            particles.add(new Particles("#ffffff", opacity, width, height));

        }
    }

    // Function to update each particle, modifies the particles in the Set
    private void updateParticles(double width, double heights) {
        // Loop through each particle
        for (Particles particle: particles) {

            // 1. Move the particle
            double ms = moveSpeed/2;
            particle.positionX += particle.velocityX * ms;
            particle.positionY += particle.velocityY * ms;

            // 2. Change the opacity
            if (animateOpacity) {
                if (particle.opacityStatus == true) {
                    if (particle.opacityValue >= this.opacityValue) particle.opacityStatus = false;
                    particle.opacityValue += particle.velocityOpacity;
                } else {
                    if (particle.opacityValue <= this.opacityMin) particle.opacityStatus = true;
                    particle.opacityValue -= particle.velocityOpacity;
                }
                if (particle.opacityValue < 0) particle.opacityValue = 0;
            }





            // 3. Change the size
            if (particle.sizeStatus == true) {
                if(particle.radius >= this.sizeValue) particle.sizeStatus = false;
                particle.radius += particle.velocitySize;
            } else {
                if(particle.radius <= this.sizeMin) particle.sizeStatus = true;
                particle.radius -= particle.velocitySize;
            }
            if(particle.radius < 0) particle.radius = 0;

            // change particle position if it is out of canvas
            double xLeft, xRight, yTop, yBottom;
            if(this.moveOutMode.equals("bounce")){
                xLeft = particle.radius;
                xRight = width;
                yTop = particle.radius;


            }
                var new_pos = {
                        x_left:p.radius,
                        x_right:pJS.canvas.w,
                        y_top:p.radius,
                        y_bottom:pJS.canvas.h
            } else {
                var new_pos = {
                        x_left: -p.radius,
                        x_right: pJS.canvas.w + p.radius,
                        y_top: -p.radius,
                        y_bottom: pJS.canvas.h + p.radius
                }
                    if(p.x - p.radius > pJS.canvas.w){
                        p.x = new_pos.x_left;
                        p.y = Math.random() * pJS.canvas.h;
                    }
                    else if(p.x + p.radius < 0){
                        p.x = new_pos.x_right;
                        p.y = Math.random() * pJS.canvas.h;
                    }
                    if(p.y - p.radius > pJS.canvas.h){
                        p.y = new_pos.y_top;
                        p.x = Math.random() * pJS.canvas.w;
                    }
                    else if(p.y + p.radius < 0){
                        p.y = new_pos.y_bottom;
                        p.x = Math.random() * pJS.canvas.w;
                    }

            // 4. Change the location if it is out of the canvas

            // 5. Particle events

            // 6. Interaction between particles

        }
    }

    // Function to draw the particles onto the canvas
    public void drawParticles(Canvas c) {
        // 1. Clear canvas
        // TODO: take background colour from parameter/local variable
        c.save();
        c.drawColor(0xff000000);

        // 2. Update the particles
        updateParticles();

        // 3. Draw each particle
        for (Particles particle: particles) {

            // TODO: particle.draw(c);
        }

        c.restore();
    }

    // Remove all particles from array
    public void removeParticles() {
        this.particles = new HashSet<Particles>();
    }


}
