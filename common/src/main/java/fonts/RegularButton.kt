package fonts

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatButton

class RegularButton : AppCompatButton {
    private var isElevated = false
    private var elevationLowPx = 0f
    private var elevationHighPx = 0f
    private var animationDurationMs = 0

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets, "fonts/Tajawal-Regular.ttf")
            typeface = tf
        }
        elevationLowPx = dpToPx(ELEVATION_LOW_DP)
        elevationHighPx = dpToPx(ELEVATION_HIGH_DP)
        animationDurationMs = ANIMATION_DURATION_MS
    }

    private fun dpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    fun toggleElevation() {
        if (isElevated) {
            animateElevation(elevationHighPx, elevationLowPx)
        } else {
            animateElevation(elevationLowPx, elevationHighPx)
        }
        isElevated = !isElevated
    }

    private fun animateElevation(fromElevation: Float, toElevation: Float) {
        val anim = ValueAnimator.ofFloat(fromElevation, toElevation)
        anim.duration = animationDurationMs.toLong()
        anim.addUpdateListener { valueAnimator: ValueAnimator ->
            val elevation = valueAnimator.animatedValue as Float
            setElevation(elevation)
        }
        anim.start()
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        super.setBackgroundDrawable(background)
        isClickable = true
    }

    companion object {
        private const val ELEVATION_HIGH_DP = 8f
        private const val ELEVATION_LOW_DP = 2f
        private const val ANIMATION_DURATION_MS = 200
    }
}
