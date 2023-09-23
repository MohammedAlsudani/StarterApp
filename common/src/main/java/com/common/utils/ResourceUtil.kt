package com.common.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat

object ResourceUtil {

    /**
     * Get a color from an attr styled according to the the context's theme.
     */
    fun getColorFromAttributeId(context: Context, @AttrRes attrColor: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attrColor, value, true)
        return if (value.resourceId != 0) {
            ContextCompat.getColor(context, value.resourceId)
        } else value.data
    }


}