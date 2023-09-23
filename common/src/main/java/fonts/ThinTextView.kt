package fonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ThinTextView : AppCompatTextView {

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
        setLineSpacing(0f, 0.9f)
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets, "fonts/Tajawal-Light.ttf")
            typeface = tf
        }
    }
}