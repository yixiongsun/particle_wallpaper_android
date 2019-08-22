package com.yixiongsun.particlelivebackground

import android.graphics.Bitmap

class BokehRainbowCircle(
    internal var x: Float, internal var y: Float, internal var origRadius: Float,
    internal var color: Int, internal var steps: Int
) {

    internal var deltaRadius: Float = 0.toFloat()

    internal var radius: Float = 0.toFloat()

    internal var origX: Float = 0.toFloat()

    internal var deltaX: Float = 0.toFloat()

    internal var origY: Float = 0.toFloat()

    internal var deltaY: Float = 0.toFloat()

    internal var alpha: Int = 0

    internal var currentStep: Int = 0

    internal var bitmap: Bitmap? = null

    internal val isDone: Boolean
        get() = this.currentStep > this.steps

    init {
        this.origX = x
        this.deltaX = (40.0 * Math.random() - 20.0).toFloat()
        this.origY = y
        this.deltaY = (40.0 * Math.random() - 20.0).toFloat()
        this.radius = origRadius
        this.deltaRadius = 0.5f * origRadius
        this.alpha = 0
    }

    internal fun tick() {
        this.currentStep++

        val fraction = this.currentStep.toFloat() / this.steps.toFloat()

        this.radius = this.origRadius + fraction * this.deltaRadius
        this.x = this.origX + fraction * this.deltaX
        this.y = this.origY + fraction * this.deltaY

        if (fraction <= 0.25f) {
            this.alpha = (128f * 4.0f * fraction).toInt()
        } else {
            this.alpha = (-128 * (fraction - 1) / 0.75f).toInt()
        }
    }
}