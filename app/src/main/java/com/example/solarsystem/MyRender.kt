package com.example.solarsystem

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.EGLConfig
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import javax.microedition.khronos.opengles.GL10


class MyRenderer(var context: Context) : GLSurfaceView.Renderer {
    private var Sun: Sphere = Sphere(2f)
    private var Earth: Sphere = Sphere(1f)
    private var Moon: Sphere = Sphere(0.5f)
    private var p = 0f
    private var angle = 0f

    init {
        Sun = Sphere(2f)
        Earth = Sphere(1f)
        Moon = Sphere(0.5f)
    }

    override fun onSurfaceCreated(gl: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        gl?.glClearColor(0.0f, 0.0f, 0f, 1.0f)
        gl?.glClearDepthf(1f)
        gl?.glEnable(GL10.GL_DEPTH_TEST)
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()
        gl?.glOrthof(-10f, 10f, -10f, 10f, -10f, 10f)
        gl?.glMatrixMode(GL10.GL_MODELVIEW)
        gl?.glLoadIdentity()
        gl?.glScalef(2f, 1f, 1f)
        if (gl != null) {
            loadGLTexture(gl)
        }
    }

    private fun loadGLTexture(gl: GL10) {
        gl.glGenTextures(3, textures, 0)
        for (i in texture_name.indices) {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i])
            gl.glTexParameterf(
                GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR.toFloat()
            )
            val `is` = context.resources.openRawResource(texture_name[i])
            val bitmap = BitmapFactory.decodeStream(`is`)
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()
        }
    }


    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {}
    override fun onDrawFrame(gl: GL10) {
        var RotationOffset: Float
        var RotationSpeed: Float
        p = if (p == 360f) 0F else p + 2
        angle = if (angle == 360f) 0F else angle + 0.15f
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl.glEnable(GL10.GL_TEXTURE_2D)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0])
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, Sun.textureBuffer)
        gl.glPushMatrix()
        gl.glRotatef(90f, 1f, 0f, 0f)
        gl.glRotatef(p, 0f, 0f, 0.1f)
        gl.glColor4f(1f, 1f, 0f, 1f)
        Sun.onDrawFrame(gl)
        gl.glPopMatrix()
        RotationOffset = 6.0f
        RotationSpeed = 0.1f
        gl.glPushMatrix()
        gl.glTranslatef(
            RotationOffset * Math.cos((angle * RotationSpeed).toDouble())
                .toFloat(),  /*RotationOffset * (float)(Math.cos(angle * RotationSpeed))*/
            0f,
            RotationOffset * Math.sin((angle * RotationSpeed).toDouble()).toFloat()
        )
        gl.glRotatef(90f, 1f, 0f, 0f)
        gl.glRotatef(p, 0f, 0f, 2f)
        gl.glPushMatrix()
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1])
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, Earth.textureBuffer)
        gl.glColor4f(1f, 1f, 1f, 1f)
        Earth.onDrawFrame(gl)
        gl.glRotatef(-p, 0.3f, 1f, 0f)
        RotationOffset = 1.5f
        RotationSpeed = 0.2f
        gl.glTranslatef(
            RotationOffset * Math.cos((1 * RotationSpeed).toDouble())
                .toFloat(),  /*RotationOffset * (float)(-0.5f * Math.cos(angle * RotationSpeed))*/
            0f,
            RotationOffset * Math.sin((1 * RotationSpeed).toDouble()).toFloat()
        )
        gl.glRotatef(p, 0f, 1f, 0f)
        gl.glColor4f(0f, 0f, 1f, 1f)
        //gl.glRotatef(angle, 1.0f, 1.0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2])
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, Moon.textureBuffer)
        gl.glColor4f(1f, 1f, 1f, 1f)
        Moon.onDrawFrame(gl)
        gl.glPopMatrix()
        gl.glPopMatrix()
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
        gl.glDisable(GL10.GL_TEXTURE_2D)
    }

    companion object {
        var texture_name = intArrayOf(
            R.drawable.sun,
            R.drawable.earth,
            R.drawable.moon


        )
        var textures = IntArray(texture_name.size)
    }
}