package com.example.tataprototype.DrawFunctionality

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.MotionEvent
import kotlin.math.abs
import android.graphics.PorterDuffXfermode
import com.example.tataprototype.R

class DrawView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) : View(context, attrs)
{
    private var mX = 0f
    private var mY = 0f
    private lateinit var mPath: Path
    private val mPaint: Paint = Paint()

    // ArrayList to store all the strokes
    // drawn by the user on the Canvas
    private val paths: ArrayList<Stroke> = ArrayList()
    private var currentColor = 0
    private var strokeWidth = 0
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)


    // this method instantiate the bitmap and object
    fun init(height: Int, width: Int)
    {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        // set an initial color of the brush
        currentColor = Color.WHITE

        // set an initial brush size
        strokeWidth = 15
        Log.d("Init", "Initialization")
    }

    fun reset()
    {
        //Clear contents of bitmap and repaint bitmap on Canvas to wipe
        mBitmap.eraseColor(Color.TRANSPARENT)
        mCanvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        mCanvas.save()
        paths.clear()
    }

    // this method returns the current bitmap
    fun save(): Bitmap
    {
        return mBitmap
    }

    // this is the main method where
    // the actual drawing takes place
    override fun onDraw(canvas: Canvas)
    {
        // save the current state of the canvas before,
        // to draw the background of the canvas
        canvas.save()

        // now, we iterate over the list of paths
        // and draw each path on the canvas
        for (fp in paths)
        {
            mPaint.strokeWidth = fp.strokeWidth.toFloat() + 1
            mCanvas.drawPath(fp.path, mPaint)
        }
        canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
    }

    // the below methods manages the touch
    // response of the user on the screen
    // firstly, we create a new Stroke
    // and add it to the paths list
    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val fp = Stroke(currentColor, strokeWidth, mPath)
        paths.add(fp)

        // finally remove any curve
        // or line from the path
        mPath.reset()

        // this methods sets the starting
        // point of the line being drawn
        mPath.moveTo(x, y)

        // we save the current
        // coordinates of the finger
        mX = x
        mY = y
    }

    // in this method we check
    // if the move of finger on the
    // screen is greater than the
    // Tolerance we have previously defined,
    // then we call the quadTo() method which
    // actually smooths the turns we create,
    // by calculating the mean position between
    // the previous position and current position
    private fun touchMove(x: Float, y: Float) {
        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    // at the end, we call the lineTo method
    // which simply draws the line until
    // the end position
    private fun touchUp() {
        mPath.lineTo(mX, mY)
    }

    // the onTouchEvent() method provides us with
    // the information about the type of motion
    // which has been taken place, and according
    // to that we call our desired methods
    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        val x = event.x
        val y = event.y
        Log.d("DrawView", "Touch event")
        when (event.action)
        {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4f
    }

    // Init block to initialise all the attributes
    init
    {
        mPaint.apply {
            isAntiAlias = true
            isDither = true
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            alpha = 0xff
        }
    }
}