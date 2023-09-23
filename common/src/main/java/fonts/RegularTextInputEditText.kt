package fonts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText


class RegularTextInputEditText : TextInputEditText {

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
            val tf = Typeface.createFromAsset(context.assets, "fonts/Tajawal-Regular.ttf")
            typeface = tf
        }
    }
}