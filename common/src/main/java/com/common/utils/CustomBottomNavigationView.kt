package com.common.utils

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class CustomBottomNavigationView : BottomNavigationView {
    private var mPath: Path? = null
    private var mPaint: Paint? = null

    /** the CURVE_CIRCLE_RADIUS represent the radius of the fab button  */
    private var CURVE_CIRCLE_RADIUS = 60f // Change this to your desired default value in dp

    // the coordinates of the first curve
    var mFirstCurveStartPoint = Point()
    var mFirstCurveEndPoint = Point()
    var mFirstCurveControlPoint2 = Point()
    var mFirstCurveControlPoint1 = Point()

    //the coordinates of the second curve
    var mSecondCurveStartPoint = Point()
    var mSecondCurveEndPoint = Point()
    var mSecondCurveControlPoint1 = Point()
    var mSecondCurveControlPoint2 = Point()
    var mNavigationBarWidth = 0
    var mNavigationBarHeight = 0

    constructor(context: Context?) : super(context!!) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        mPath = Path()
        mPaint = Paint()
        mPaint?.style = Paint.Style.FILL_AND_STROKE
        mPaint?.color = ResourceUtil.getColorFromAttributeId(context, R.attr.colorBackgroundFloating)
        setBackgroundColor(ContextCompat.getColor(context,R.color.transparent))
        CURVE_CIRCLE_RADIUS = dpToPx(66f) / 2
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        mNavigationBarWidth = width
        mNavigationBarHeight = height
        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint[(mNavigationBarWidth / 2 - CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS / 3).toInt()] =
            0
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint[mNavigationBarWidth / 2] = (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4).toInt()
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint
        mSecondCurveEndPoint[(mNavigationBarWidth / 2 + CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS / 3).toInt()] =
            0

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1[(mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4).toInt()] =
            mFirstCurveStartPoint.y
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2[(mFirstCurveEndPoint.x - CURVE_CIRCLE_RADIUS * 2 + CURVE_CIRCLE_RADIUS).toInt()] =
            mFirstCurveEndPoint.y
        mSecondCurveControlPoint1[(mSecondCurveStartPoint.x + CURVE_CIRCLE_RADIUS * 2 - CURVE_CIRCLE_RADIUS).toInt()] =
            mSecondCurveStartPoint.y
        mSecondCurveControlPoint2[(mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + CURVE_CIRCLE_RADIUS / 4)).toInt()] =
            mSecondCurveEndPoint.y
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath?.reset()
        mPath?.moveTo(0f, 0f)
        mPath?.lineTo(mFirstCurveStartPoint.x.toFloat(), mFirstCurveStartPoint.y.toFloat())
        mPath?.cubicTo(
            mFirstCurveControlPoint1.x.toFloat(), mFirstCurveControlPoint1.y.toFloat(),
            mFirstCurveControlPoint2.x.toFloat(), mFirstCurveControlPoint2.y.toFloat(),
            mFirstCurveEndPoint.x.toFloat(), mFirstCurveEndPoint.y.toFloat()
        )
        mPath?.cubicTo(
            mSecondCurveControlPoint1.x.toFloat(), mSecondCurveControlPoint1.y.toFloat(),
            mSecondCurveControlPoint2.x.toFloat(), mSecondCurveControlPoint2.y.toFloat(),
            mSecondCurveEndPoint.x.toFloat(), mSecondCurveEndPoint.y.toFloat()
        )
        mPath?.lineTo(mNavigationBarWidth.toFloat(), 0f)
        mPath?.lineTo(mNavigationBarWidth.toFloat(), mNavigationBarHeight.toFloat())
        mPath?.lineTo(0f, mNavigationBarHeight.toFloat())
        mPath?.close()
        canvas.drawPath(mPath!!, mPaint!!)
    }

    private fun dpToPx(dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }
}