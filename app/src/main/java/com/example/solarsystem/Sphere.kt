package com.example.solarsystem

import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


class Sphere(R: Float) : GLSurfaceView.Renderer {
    var mVertexBuffer: FloatBuffer
    var textureBuffer: FloatBuffer
    var n = 0
    var sz = 0
    private val colors = arrayOf(
        floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f),
        floatArrayOf(0.95f, 0.5f, 0.5f, 1.0f),
        floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)
    )

    init {
        val dtheta = 15
        val dphi = 15
        val DTOR = (Math.PI / 180.0f).toFloat()
        var byteBuf = ByteBuffer.allocateDirect(5000 * 3 * 4)
        byteBuf.order(ByteOrder.nativeOrder())
        mVertexBuffer = byteBuf.asFloatBuffer()
        byteBuf = ByteBuffer.allocateDirect(5000 * 2 * 4)
        byteBuf.order(ByteOrder.nativeOrder())
        textureBuffer = byteBuf.asFloatBuffer()
        var theta = -90
        while (theta <= 90 - dtheta) {
            var phi = 0
            while (phi <= 360 - dphi) {
                sz++
                mVertexBuffer.put((Math.cos((theta * DTOR).toDouble()) * Math.cos((phi * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put((Math.cos((theta * DTOR).toDouble()) * Math.sin((phi * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put(Math.sin((theta * DTOR).toDouble()).toFloat() * R)
                mVertexBuffer.put((Math.cos(((theta + dtheta) * DTOR).toDouble()) * Math.cos((phi * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put((Math.cos(((theta + dtheta) * DTOR).toDouble()) * Math.sin((phi * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put(Math.sin(((theta + dtheta) * DTOR).toDouble()).toFloat() * R)
                mVertexBuffer.put((Math.cos(((theta + dtheta) * DTOR).toDouble()) * Math.cos(((phi + dphi) * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put((Math.cos(((theta + dtheta) * DTOR).toDouble()) * Math.sin(((phi + dphi) * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put(Math.sin(((theta + dtheta) * DTOR).toDouble()).toFloat() * R)
                mVertexBuffer.put((Math.cos((theta * DTOR).toDouble()) * Math.cos(((phi + dphi) * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put((Math.cos((theta * DTOR).toDouble()) * Math.sin(((phi + dphi) * DTOR).toDouble())).toFloat() * R)
                mVertexBuffer.put(Math.sin((theta * DTOR).toDouble()).toFloat() * R)
                n += 4
                textureBuffer.put(phi / 360.0f)
                textureBuffer.put((90 + theta) / 180.0f)
                textureBuffer.put(phi / 360.0f)
                textureBuffer.put((90 + theta + dtheta) / 180.0f)
                textureBuffer.put((phi + dphi) / 360.0f)
                textureBuffer.put((90 + theta + dtheta) / 180.0f)
                textureBuffer.put((phi + dphi) / 360.0f)
                textureBuffer.put((90 + theta) / 180.0f)
                phi += dphi
            }
            theta += dtheta
        }
        mVertexBuffer.position(0)
        textureBuffer.position(0)
    }

    override fun onSurfaceCreated(p0: GL10?, p1: javax.microedition.khronos.egl.EGLConfig?) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {}
    override fun onDrawFrame(gl: GL10) {
        gl.glFrontFace(GL10.GL_CCW)
        gl.glEnable(GL10.GL_CULL_FACE)
        gl.glCullFace(GL10.GL_BACK)
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer)
        var i = 0
        while (i < n) {

// gl.glColor4f(colors[i % 3][0], colors[i % 3][1], colors[i % 3][2], colors[i % 3][3]);
            gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, i, 4)
            i += 4
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisable(GL10.GL_CULL_FACE)
    }
}