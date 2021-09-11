package eu.tutorials.kidsdrawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context,attrs:AttributeSet): View(context,attrs) {
    private var mDrawPath: CustomPath? =
        null // An variable of CustomPath inner class to use it further.
    private var mCanvasBitmap: Bitmap? = null // An instance of the Bitmap.

    private var mDrawPaint: Paint? =
        null // The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
    private var mCanvasPaint: Paint? = null // Instance of canvas paint view.

    private var mBrushSize: Float =
        0.toFloat() // A variable for stroke/brush size to draw on the canvas.

    // A variable to hold a color of the stroke.
    private var color = Color.BLACK

    /**
     * A variable for canvas which will be initialized later and used.
     *
     *The Canvas class holds the "draw" calls. To draw something, you need 4 basic components: A Bitmap to hold the pixels, a Canvas to host
     * the draw calls (writing into the bitmap), a drawing primitive (e.g. Rect,
     * Path, text, Bitmap), and a paint (to describe the colors and styles for the
     * drawing)
     */
    private var canvas: Canvas? = null

    private val mPaths = ArrayList<CustomPath>() // ArrayList for Paths

    private val mUndoPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    /**
     * This method initializes the attributes of the
     * ViewForDrawing class.
     */
    private fun setUpDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)

        mDrawPaint?.color = color

        mDrawPaint?.style = Paint.Style.STROKE // This is to draw a STROKE style
        mDrawPaint?.strokeJoin = Paint.Join.ROUND // This is for store join
        mDrawPaint?.strokeCap = Paint.Cap.ROUND // This is for stroke Cap

        mCanvasPaint = Paint(Paint.DITHER_FLAG) // Paint flag that enables dithering when blitting.

    }

    override fun onSizeChanged(w: Int, h: Int, wprev: Int, hprev: Int) {
        super.onSizeChanged(w, h, wprev, hprev)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }


    /**
     * This method is called when a stroke is drawn on the canvas
     * as a part of the painting.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /**
         * Draw the specified bitmap, with its top/left corner at (x,y), using the specified paint,
         * transformed by the current matrix.
         *
         *If the bitmap and canvas have different densities, this function will take care of
         * automatically scaling the bitmap to draw at the same density as the canvas.
         *
         * @param bitmap The bitmap to be drawn
         * @param left The position of the left side of the bitmap being drawn
         * @param top The position of the top side of the bitmap being drawn
         * @param paint The paint used to draw the bitmap (may be null)
         */
        mCanvasBitmap?.let {
            canvas.drawBitmap(it, 0f,   0f, mCanvasPaint)
        }


        for (p in mPaths) {
            mDrawPaint?.strokeWidth = p.brushThickness
            mDrawPaint?.color = p.color
            canvas.drawPath(p, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    /**
     * This method acts as an event listener when a touch
     * event is detected on the device.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset() // Clear any lines and curves from the path, making it empty.
                mDrawPath!!.moveTo(
                    touchX,
                    touchY
                ) // Set the beginning of the next contour to the point (x,y).
            }

            MotionEvent.ACTION_MOVE -> {
                mDrawPath!!.lineTo(
                    touchX,
                    touchY
                ) // Add a line from the last point to the specified point (x,y).
            }

            MotionEvent.ACTION_UP -> {

                mPaths.add(mDrawPath!!) //Add when to stroke is drawn to canvas and added in the path arraylist

                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }

        invalidate()
        return true
    }

    /**
     * This method is called when either the brush or the eraser
     * sizes are to be changed. This method sets the brush/eraser
     * sizes to the new values depending on user selection.
     */
    fun setSizeForBrush(newSize: Float) {
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, newSize,
            resources.displayMetrics
        )
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    /**
     * This function is called when the user desires a color change.
     * This functions sets the color of a store to selected color and able to draw on view using that color.
     *
     * @param newColor
     */
    fun setColor(newColor: String) {
        color = Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }

    /**
     * This function is called when the user selects the undo
     * command from the application. This function removes the
     * last stroke input by the user depending on the
     * number of times undo has been activated.
     */
    fun onClickUndo() {
        if (mPaths.size > 0) {

            mUndoPaths.add(mPaths.removeAt(mPaths.size - 1))
            invalidate() // Invalidate the whole view. If the view is visible
        }
    }
    internal inner class CustomPath(var color:Int,var brushThickness:Float):Path()
}