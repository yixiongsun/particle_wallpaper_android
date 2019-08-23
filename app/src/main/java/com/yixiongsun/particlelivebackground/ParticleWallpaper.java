package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ParticleWallpaper extends ParticleWallpaperService {

    // Override onCreateEngine to return a custom ParticleEngine object
    @Override
    public Engine onCreateEngine() {
        return new ParticleEngine();
    }

    // ParticleEngine inherits from ParticleWallpaperService.Engine
    class ParticleEngine extends AnimationEngine {

        int offsetX;
        int offsetY;
        int height;
        int width;
        int visibleWidth;

        // HashSet of Particles
        Set<Particles> particles = new HashSet<Particles>();


        // Override onCreate and enable touch events
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // By default we don't get touch events, so enable them.
            setTouchEventsEnabled(true);
        }

        // Overrides onSurfaceChanged and sets local variables to the updated dimensions
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {

            // height
            this.height = height;

            // width
            if (this.isPreview()) {
                this.width = width;
            } else {
                this.width = 2 * width;
            }

            // visibleWidth
            this.visibleWidth = width;

            // Reinitialize particles
            /*for (int i = 0; i < 20; i++) {
                this.createRandomCircle();
            }*/

            super.onSurfaceChanged(holder, format, width, height);
        }


        // Overrides onOffsetsChanged and sets local variables to updated offsets
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                     int yPixelOffset) {
            // store the offsets
            this.offsetX = xPixelOffset;
            this.offsetY = yPixelOffset;

            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                    xPixelOffset, yPixelOffset);
        }

        // Overrides onCommand and does particle action on tap
        @Override
        public Bundle onCommand(String action, int x, int y, int z,
                                Bundle extras, boolean resultRequested) {

            // Tap action
            if ("android.wallpaper.tap".equals(action)) {
                //createCircle(x - this.offsetX, y - this.offsetY);
            }
            return super.onCommand(action, x, y, z, extras, resultRequested);
        }

        // Overrides drawFrame and locks the canvas and draws the updates to the canvas
        @Override
        protected void drawFrame() {
            SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    draw(c);
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }

        // Draws the particles on the canvas
        void draw(Canvas c) {
            c.save();
            c.drawColor(0xff000000);

            synchronized (particles) {
                for (Particles particle : particles) {

                }
            }

            c.restore();
        }

        @Override
        protected void iteration() {

            // Iteration logic

            /*
            synchronized (particles) {
                for (Iterator<Particles> it = particles.iterator(); it
                        .hasNext();) {
                    Particles particle = it.next();
                    particle.tick();
                    if (particle.isDone())
                        it.remove();
                }
                iterationCount++;
                if (isPreview() || iterationCount % 2 == 0)
                    createRandomCircle();
            }*/

            super.iteration();
        }

        // Create random particle
        void createRandomParticle() {
            int x = (int) (width * Math.random());
            int y = (int) (height * Math.random());
            createParticle(x, y);
        }

        int getColor(float yFraction) {
            return Color.HSVToColor(new float[] { 360.0f * yFraction, 1.0f,
                    1.0f });
        }

        void createParticle(int x, int y) {
            float radius = (float) (40 + 20 * Math.random());

            float yFraction = (float) y / (float) height;
            yFraction = yFraction + 0.05f - (float) (0.1f * (Math.random()));
            if (yFraction < 0.0f)
                yFraction += 1.0f;
            if (yFraction > 1.0f)
                yFraction -= 1.0f;
            int color = getColor(yFraction);

            int steps = 40 + (int) (20 * Math.random());
            //BokehRainbowCircle particle = new BokehRainbowCircle(x, y, radius,
            //        color, steps);
            synchronized (this.particles) {
                //this.particles.add(particle);
            }
        }


    }


}
