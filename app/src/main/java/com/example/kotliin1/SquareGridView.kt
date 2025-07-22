package com.example.kotliin1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.*
import kotlin.math.ceil
import kotlin.math.sqrt

class SquareGridView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var squareCount = 0
    private var drawBitmap: Bitmap? = null
    private var drawCanvas: Canvas? = null

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 0.5f
    }

    private var drawJob: Job? = null

    fun setSquareCount(count: Int) {
        squareCount = count
        drawBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(drawBitmap!!)
        drawJob?.cancel()
        startDrawingInChunks()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }

    private fun startDrawingInChunks() {
        if (width == 0 || height == 0 || squareCount == 0) return

        val containerSize = width
        val columns = ceil(sqrt(squareCount.toDouble())).toInt()
        val squareSize = containerSize.toFloat() / columns

        drawJob = CoroutineScope(Dispatchers.Default).launch {
            for (i in 0 until squareCount) {
                if (!isActive) break

                val row = i / columns
                val col = i % columns
                val left = col * squareSize
                val top = row * squareSize
                val right = left + squareSize
                val bottom = top + squareSize

                drawCanvas?.drawRect(left, top, right, bottom, paint)

                // Every 1000 rectangles, delay a bit and refresh UI
                if (i % 80000 == 0) {
                    withContext(Dispatchers.Main) {
                        invalidate()
                    }
                    delay(5)  // Prevent blocking
                }
            }

            withContext(Dispatchers.Main) {
                invalidate() // Final update
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        drawJob?.cancel()
    }
}
