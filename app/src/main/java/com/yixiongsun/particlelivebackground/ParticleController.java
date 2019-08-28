package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ParticleController {


    // Local variables
    private ArrayList<Particle> particles;
    private ParticleSettings particleSettings = new ParticleSettings();

    // Variables to track events
    private float clickPosX = 0;
    private float clickPosY = 0;
    private boolean bubbleClicking = false;
    private boolean repulseClicking = false;
    private int repulseCount = 0;
    private boolean repulseFinish = true;


    // Default constructor
    public ParticleController() {
        this.particles = new ArrayList<Particle>();
    }

    // Constructor from particles
    public ParticleController(ArrayList<Particle> particles) {
        this.particles = particles;
    }


    // Function to create new particles
    public void createParticles(float width, float height) {
        synchronized (particles) {
            pushParticles(particleSettings.numberOfParticles, width, height);
            particleAutoDensity(width, height);
        }

    }

    // Function to update each particle, modifies the particles in the Set
    private void updateParticles(Canvas canvas) {
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        // Loop through each particle
        for (int i = 0; i < particles.size(); i++) {

            Particle particle = this.particles.get(i);
            // 1. Move the particle

            float ms = particleSettings.moveSpeed / 2;
            particle.positionX += particle.velocityX * ms;
            particle.positionY += particle.velocityY * ms;

            // 2. Change the opacity
            if (particleSettings.opacityAnimate) {
                if (particle.opacityStatus == true) {
                    if (particle.opacityValue >= particleSettings.opacityValue)
                        particle.opacityStatus = false;
                    particle.opacityValue += particle.velocityOpacity;
                } else {
                    if (particle.opacityValue <= particleSettings.opacityMin)
                        particle.opacityStatus = true;
                    particle.opacityValue -= particle.velocityOpacity;
                }
                if (particle.opacityValue < 0) particle.opacityValue = 0;
            }


            // 3. Change the size
            if (particle.sizeStatus == true) {
                if (particle.radius >= particleSettings.size) particle.sizeStatus = false;
                particle.radius += particle.velocitySize;
            } else {
                if (particle.radius <= particleSettings.sizeMin) particle.sizeStatus = true;
                particle.radius -= particle.velocitySize;
            }
            if (particle.radius < 0) particle.radius = 0;


            // 4. Change the location if it is out of the canvas
            float xLeft, xRight, yTop, yBottom;
            if (particleSettings.moveOutMode.equals("bounce")) {
                xLeft = particle.radius;
                xRight = width;
                yTop = particle.radius;
                yBottom = height;
            } else {
                xLeft = -1 * particle.radius;
                xRight = width + particle.radius;
                yTop = -1 * particle.radius;
                yBottom = height + particle.radius;
            }

            // Check if x position is out of bounds
            if (particle.positionX - particle.radius > width) {
                particle.positionX = xLeft;
                particle.positionY = (float) (Math.random() * height);
            } else if (particle.positionX + particle.radius < 0) {
                particle.positionX = xRight;
                particle.positionY = (float) (Math.random() * height);
            }

            // Check if y position is out of bounds
            if (particle.positionY - particle.radius > height) {
                particle.positionY = yTop;
                particle.positionX = (float) (Math.random() * width);
            } else if (particle.positionY + particle.radius < 0) {
                particle.positionY = yBottom;
                particle.positionX = (float) (Math.random() * width);
            }

            // Velocity changes
            switch (particleSettings.moveOutMode) {
                case "bounce":
                    if (particle.positionX + particle.radius > width)
                        particle.velocityX = -1 * particle.velocityX;
                    else if (particle.positionX - particle.radius < 0)
                        particle.velocityX = -1 * particle.velocityX;
                    if (particle.positionY + particle.radius > height)
                        particle.velocityY = -1 * particle.velocityY;
                    else if (particle.positionY - particle.radius < 0)
                        particle.velocityY = -1 * particle.velocityY;
                    break;
            }

            // TODO: 5. Particle events
            /*
            if(isInArray('grab', pJS.interactivity.events.onhover.mode)){
                pJS.fn.modes.grabParticle(p);
            }

            if(isInArray('bubble', pJS.interactivity.events.onhover.mode) || isInArray('bubble', pJS.interactivity.events.onclick.mode)){
                pJS.fn.modes.bubbleParticle(p);
            }
            */

            // Repulse event
            if (particleSettings.onClickEvent == ParticleEvent.REPUSLE) {
                repulseParticle(particle, canvas);
            }

            // 6. Interaction between particles
            if (particleSettings.lineLinked || particleSettings.attract) {
                for (int j = i + 1; j < this.particles.size(); j++) {
                    Particle particle2 = this.particles.get(j);

                    /* link particles */
                    if (particleSettings.lineLinked) {
                        linkParticles(canvas, particle, particle2);
                    }

                    /* attract particles */
                    if (particleSettings.attract) {
                        attractParticles(particle, particle2);
                    }

                    /* bounce particles */
                    if (particleSettings.bounce) {
                        bounceParticles(particle, particle2);
                    }
                }
            }
        }
    }

    // Function to draw the particles onto the canvas
    public void drawParticles(Canvas canvas) {
        // 1. Clear canvas
        // TODO: take background colour from parameter/local variable
        canvas.save();
        canvas.drawColor(0xff000000);

        // 2. Update the particles
        updateParticles(canvas);

        // 3. Draw each particle
        // synchronized (particles) {
        for (Particle particle : particles) {
            particle.draw(canvas, particleSettings);
        }
        //}


        canvas.restore();
    }

    // Remove all particles from array
    public void removeParticles() {
        this.particles = new ArrayList<Particle>();
    }

    // Function to link particles
    private void linkParticles(Canvas canvas, Particle p1, Particle p2) {
        float dx = p1.positionX - p2.positionX;
        float dy = p1.positionY - p2.positionY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        // draw a line between p1 and p2 if the distance between them is under the config distance
        if (dist <= particleSettings.lineDistance) {
            float lineOpacity = particleSettings.lineOpacity - (dist / (1 / particleSettings.lineOpacity)) / particleSettings.lineDistance;

            if (lineOpacity > 0) {

                // style
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(colourInt(particleSettings.lineColour, lineOpacity));
                paint.setStrokeWidth(particleSettings.lineWidth);

                // path
                canvas.drawLine(p1.positionX, p1.positionY, p2.positionX, p2.positionY, paint);

            }

        }
    }

    // Function to attract particles
    private void attractParticles(Particle p1, Particle p2) {

        float dx = p1.positionX - p2.positionX;
        float dy = p1.positionY - p2.positionY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist <= particleSettings.lineDistance) {

            float ax = dx / (particleSettings.attractRotateX * 1000);
            float ay = dy / (particleSettings.attractRotateY * 1000);

            p1.velocityX -= ax;
            p1.velocityY -= ay;

            p2.velocityX += ax;
            p2.velocityY += ay;
        }

    }


    // Function to bounce particles
    private void bounceParticles(Particle p1, Particle p2) {

        float dx = p1.positionX - p2.positionX;
        float dy = p1.positionY - p2.positionY;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        float dist_p = p1.radius + p2.radius;

        if (dist <= dist_p) {
            p1.velocityX = -1 * p1.velocityX;
            p1.velocityY = -1 * p1.velocityY;

            p2.velocityX = -1 * p2.velocityX;
            p2.velocityY = -1 * p2.velocityY;
        }
    }

    // Static function to get a colour int from a hex string and opacity float
    public static int colourInt(String colour, float opacity) {
        int colourInt = Color.parseColor(colour);
        int R = (colourInt >> 16) & 0xff;
        int G = (colourInt >> 8) & 0xff;
        int B = (colourInt) & 0xff;
        int A = (int) (opacity * 100);
        return Color.argb(A, R, G, B);
    }

    // Function to push number of particles to particles array
    private void pushParticles(int number, float width, float height) {
        // synchronized (particles) {
        for (int i = 0; i < number; i++) {
            // Create the particle and add it to the Set

            particles.add(new Particle(particleSettings, width, height));

        }
        // }
    }

    // Function to remove number of particles from particles array
    private void removeParticles(int number) {
        for (int i = 0; i < number; i++) {
            particles.remove(0);
        }
    }


    private void particleAutoDensity(float width, float height) {
        if (particleSettings.densityEnabled) {

            // calculate area
            float area = width * height / 1000;
            /*
            if(pJS.tmp.retina){
                area = area/(pJS.canvas.pxratio*2);
            }*/

            // calc number of particles based on density area
            int numberOfParticles = (int) (area * particleSettings.numberOfParticles / particleSettings.density);

            // add or remove X particles
            int missingParticles = this.particles.size() - numberOfParticles;
            if (missingParticles < 0) pushParticles(Math.abs(missingParticles), width, height);
            else removeParticles(missingParticles);

        }
    }


    // Gestures
    public void onClick(float x, float y) {
        if (particleSettings.onClickEnable) {

            this.clickPosX = x;
            this.clickPosY = y;

            switch (particleSettings.onClickEvent) {
                case BUBBLE:
                    this.bubbleClicking = true;
                    break;
                case REPUSLE:
                    this.repulseClicking = true;
                    this.repulseCount = 0;
                    this.repulseFinish = false;

                    // Create a timer to execute after duration
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    repulseClicking = false;

                                }
                            }, (int) (particleSettings.repulseDuration * 1000));

                    break;
                default:
                    break;
            }


            //pJS.interactivity.mouse.click_time = new Date().getTime();

        }
    }


    // Particle Events
    private void repulseParticle(Particle particle, Canvas canvas) {

        /*
        if(pJS.interactivity.events.onhover.enable && isInArray('repulse', pJS.interactivity.events.onhover.mode) && pJS.interactivity.status == 'mousemove') {

            var dx_mouse = p.x - pJS.interactivity.mouse.pos_x,
                    dy_mouse = p.y - pJS.interactivity.mouse.pos_y,
                    dist_mouse = Math.sqrt(dx_mouse*dx_mouse + dy_mouse*dy_mouse);

            var normVec = {x: dx_mouse/dist_mouse, y: dy_mouse/dist_mouse},
            repulseRadius = pJS.interactivity.modes.repulse.distance,
                    velocity = 100,
                    repulseFactor = clamp((1/repulseRadius)*(-1*Math.pow(dist_mouse/repulseRadius,2)+1)*repulseRadius*velocity, 0, 50);

            var pos = {
                    x: p.x + normVec.x * repulseFactor,
                    y: p.y + normVec.y * repulseFactor
      }

            if(pJS.particles.move.out_mode == 'bounce'){
                if(pos.x - p.radius > 0 && pos.x + p.radius < pJS.canvas.w) p.x = pos.x;
                if(pos.y - p.radius > 0 && pos.y + p.radius < pJS.canvas.h) p.y = pos.y;
            }else{
                p.x = pos.x;
                p.y = pos.y;
            }

        }*/

        if (particleSettings.onClickEnable && particleSettings.onClickEvent == ParticleEvent.REPUSLE) {

            // If repulse hasn't finished yet
            if (!this.repulseFinish) {
                this.repulseCount++;

                // Stop repulse after count has reached number of particles
                if (this.repulseCount == particles.size()) {
                    this.repulseFinish = true;
                }
            }

            // If repulse is clicking, ie Timer hasn't finished
            if (this.repulseClicking) {

                double repulseRadius = Math.pow(particleSettings.repulseDistance / 6, 3);

                double dx = this.clickPosX - particle.positionX, dy = this.clickPosY - particle.positionY, d = dx * dx + dy * dy;

                double force = -1 * repulseRadius / d * 1;

                // default
                if (d <= repulseRadius) {
                    double f = Math.atan2(dy, dx);
                    particle.velocityX = (float) (force * Math.cos(f));
                    particle.velocityY = (float) (force * Math.sin(f));

                    if (particleSettings.moveOutMode.equals("bounce")) {
                        double x = particle.positionX + particle.velocityX;
                        double y = particle.positionY + particle.velocityY;

                        if (x + particle.radius > canvas.getWidth())
                            particle.velocityX = -1 * particle.velocityX;
                        else if (x - particle.radius < 0)
                            particle.velocityX = -1 * particle.velocityX;
                        if (y + particle.radius > canvas.getHeight())
                            particle.velocityY = -1 * particle.velocityY;
                        else if (y - particle.radius < 0)
                            particle.velocityY = -1 * particle.velocityY;
                    }
                }

                // Repulse is not clicking
            } else {
                particle.velocityX = particle.initialVelocityX;
                particle.velocityY = particle.initialVelocityY;
            }

        }

    }


}
