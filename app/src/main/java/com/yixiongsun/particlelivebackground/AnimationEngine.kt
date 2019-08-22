package com.yixiongsun.particlelivebackground

import android.os.Handler
import android.service.wallpaper.WallpaperService.Engine
import android.view.SurfaceHolder

abstract class AnimationEngine: Engine() {
        private val handler = Handler()

        private val iterate = object: Runnable {
            override fun run() {
                iteration()
                drawFrame()
            }
        }

        private var visible: Boolean = false

        override fun onDestroy() {
            super.onDestroy()

            handler.removeCallbacks(iterate)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                iteration()
                drawFrame()
            } else {
                handler.removeCallbacks(iterate)
            }
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            iteration()
            drawFrame()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            visible = false
            handler.removeCallbacks(iterate)
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            iteration()
            drawFrame()
        }

        abstract fun drawFrame()

        open fun iteration() {
            handler.removeCallbacks(iterate)

            if (visible) handler.postDelayed(iterate, 1000/25)
        }

    }