package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;

import java.util.HashSet;

public class ParticleController {

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
    public void createParticles(int number) {
        for (int i = 0; i < number; i++) {
            // Create the particle and add it to the Set
        }
    }

    // Function to update each particle, modifies the particles in the Set
    private void updateParticles() {
        // Loop through each particle
        for (Particles particle: particles) {

            // 1. Move the particle

            // 2. Change the opacity

            // 3. Change the size

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

    }

    // Remove all particles from array
    public void removeParticles() {
        this.particles = new HashSet<Particles>();
    }


}
