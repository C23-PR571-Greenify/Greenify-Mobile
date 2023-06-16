package com.rememberdev.greenify.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.rememberdev.greenify.R

class CustomButton: AppCompatButton {

    private lateinit var backgroundEnabled: Drawable
    private lateinit var backgroundDisabled: Drawable
    private var textColor: Int = 0

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) backgroundEnabled else backgroundDisabled
        setTextColor(textColor)
        textSize = 16f
        gravity = Gravity.CENTER
    }

    private fun init() {
        textColor = ContextCompat.getColor(context, android.R.color.background_light)
        backgroundEnabled = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        backgroundDisabled = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}