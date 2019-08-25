package com.yixiongsun.particlelivebackground;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;


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

        // Particle Controller
        private ParticleController particleController = new ParticleController();

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
            initParticle(this.width, this.height);

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
                    particleController.drawParticles(c);
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }

        void initParticle(float width, float height) {
            particleController.createParticles(width, height);
        }


        @Override
        protected void iteration() {

            super.iteration();
        }

    }


}
