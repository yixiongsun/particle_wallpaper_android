package com.yixiongsun.particlelivebackground

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.service.wallpaper.WallpaperService

import android.view.SurfaceHolder
import java.util.HashSet

class ParticleWallpaperService: WallpaperService() {


    override fun onCreateEngine(): Engine {
        return BokehEngine()
    }

    internal inner class BokehEngine : AnimationEngine() {
        var offsetX: Int = 0
        var offsetY: Int = 0
        var height: Int = 0
        var width: Int = 0
        var visibleWidth: Int = 0

        var circles: MutableSet<BokehRainbowCircle> = HashSet()

        var iterationCount = 0

        var paint = Paint()

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)

            // By default we don't get touch events, so enable them.
            setTouchEventsEnabled(true)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            this.height = height
            if (this.isPreview()) {
                this.width = width
            } else {
                this.width = 2 * width
            }
            this.visibleWidth = width

            for (i in 0..19) {
                this.createRandomCircle()
            }

            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onOffsetsChanged(
            xOffset: Float, yOffset: Float,
            xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            // store the offsets
            this.offsetX = xPixelOffset
            this.offsetY = yPixelOffset

            super.onOffsetsChanged(
                xOffset, yOffset, xOffsetStep, yOffsetStep,
                xPixelOffset, yPixelOffset
            )
        }

        override fun onCommand(
            action: String, x: Int, y: Int, z: Int,
            extras: Bundle, resultRequested: Boolean
        ): Bundle {
            if ("android.wallpaper.tap" == action) {
                createCircle(x - this.offsetX, y - this.offsetY)
            }
            return super.onCommand(action, x, y, z, extras, resultRequested)
        }

        override fun drawFrame() {
            val holder = surfaceHolder

            var c: Canvas? = null
            try {
                c = holder.lockCanvas()
                if (c != null) {
                    draw(c!!)
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c)
            }
        }

        private fun draw(c: Canvas) {
            c.save()
            c.drawColor(-0x1000000)

            synchronized(circles) {
                for (circle in circles) {
                    if (circle.alpha === 0)
                        continue

                    // intersects with the screen?
                    val minX = circle.x - circle.radius
                    if (minX > -this.offsetX + this.visibleWidth) {
                        continue
                    }
                    val maxX = circle.x + circle.radius
                    if (maxX < -this.offsetX) {
                        continue
                    }

                    paint.isAntiAlias = true

                    // paint the fill
                    paint.color = Color.argb(
                        circle.alpha, Color
                            .red(circle.color), Color.green(circle.color),
                        Color.blue(circle.color)
                    )
                    paint.style = Paint.Style.FILL_AND_STROKE
                    c.drawCircle(
                        circle.x + this.offsetX,
                        circle.y + this.offsetY,
                        circle.radius,
                        paint
                    )

                    // paint the contour
                    paint.color = Color.argb(
                        circle.alpha, 63 + 3 * Color
                            .red(circle.color) / 4, 63 + 3 * Color
                            .green(circle.color) / 4, 63 + 3 * Color
                            .blue(circle.color) / 4
                    )
                    paint.style = Paint.Style.STROKE
                    paint.strokeWidth = 3.0f
                    c.drawCircle(
                        circle.x + this.offsetX,
                        circle.y + this.offsetY,
                        circle.radius,
                        paint
                    )
                }
            }

            c.restore()
        }

        override fun iteration() {
            synchronized(circles) {
                val it = circles.iterator()
                while (it
                        .hasNext()
                ) {
                    val circle = it.next()
                    circle.tick()
                    if (circle.isDone)
                        it.remove()
                }
                iterationCount++
                if (isPreview || iterationCount % 2 == 0)
                    createRandomCircle()
            }

            super.iteration()
        }

        private fun createRandomCircle() {
            val x = (width * Math.random()).toInt()
            val y = (height * Math.random()).toInt()
            createCircle(x, y)
        }

        private fun getColor(yFraction: Float): Int {
            return Color.HSVToColor(floatArrayOf(360.0f * yFraction, 1.0f, 1.0f))
        }

        private fun createCircle(x: Int, y: Int) {
            val radius = (40 + 20 * Math.random()).toFloat()

            var yFraction = y.toFloat() / height.toFloat()
            yFraction = yFraction + 0.05f - (0.1f * Math.random()).toFloat()
            if (yFraction < 0.0f)
                yFraction += 1.0f
            if (yFraction > 1.0f)
                yFraction -= 1.0f
            val color = getColor(yFraction)

            val steps = 40 + (20 * Math.random()).toInt()
            val circle = BokehRainbowCircle( x.toFloat(), y.toFloat(), radius, color, steps )
            synchronized(this.circles) {
                this.circles.add(circle)
            }
        }
    }



}