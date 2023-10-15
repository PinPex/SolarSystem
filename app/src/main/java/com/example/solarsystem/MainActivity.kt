package com.example.solarsystem

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.WindowManager


class MainActivity : Activity() {
    private var g: GLSurfaceView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)
        g = GLSurfaceView(this)
        g!!.setEGLConfigChooser(8, 8, 8, 8, 16, 1)
        g!!.setRenderer(MyRenderer(this))
        g!!.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        setContentView(g)
    }

    override fun onPause() {
        super.onPause()
        g!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        g!!.onResume()
    }
}