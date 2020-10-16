package com.test.notesapp.Utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import com.test.notesapp.R

class CTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    internal fun initAttrs(context: Context, attrs: AttributeSet?) {

        if (attrs != null) {
            val attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView
            )

            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            var drawableBottom: Drawable? = null
            var drawableTop: Drawable? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CustomTextView_drawableLeftCompat)
                drawableRight = attributeArray.getDrawable(R.styleable.CustomTextView_drawableRightCompat)
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomTextView_drawableBottomCompat)
                drawableTop = attributeArray.getDrawable(R.styleable.CustomTextView_drawableTopCompat)
            } else {
                val drawableLeftId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableLeftCompat, -1)
                val drawableRightId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableRightCompat, -1)
                val drawableBottomId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableBottomCompat, -1)
                val drawableTopId = attributeArray.getResourceId(R.styleable.CustomTextView_drawableTopCompat, -1)

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId)
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId)
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId)
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId)
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)
            attributeArray.recycle()
        }

    }


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

}
