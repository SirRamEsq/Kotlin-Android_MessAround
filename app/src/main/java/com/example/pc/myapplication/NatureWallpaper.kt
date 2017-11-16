package com.example.pc.myapplication

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

class NatureWallpaper : WallpaperService(){
    private var mVisible: Boolean = true  // visible flag
    var canvas: Canvas? = Canvas()      // canvas reference
    var Drawspeed: Long = 10   // thread call delay time
    var mcontext: Context? = null  //reference to the current context

    override fun onCreateEngine(): NatureWallpaper.LiveWall {
        mcontext = this // set current context
        return LiveWall() // This call contains the wallpaper code
    }

    /**
    This class extends the engine for the live wallpaper
    implements all the draw calls required to draw the wallpaper
     */
    inner class LiveWall : android.service.wallpaper.WallpaperService.Engine() {

        val mHandler = Handler() // this is to handle the thread

        //the thread responsible for drawing this thread get calls every time
        //drawspeed vars set the execution speed
        private val mDrawFrame = Runnable() {
            fun run() {
                // This method get called each time to draw thw frame
                // Engine class does not provide any invalidate methods
                // as used in canvas
                // set your draw call here
                drawFrame()
            }
        }

        //Called when the surface is created
        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)

            //call the draw method
            // this is where you must call your draw code
            drawFrame()
        }

        // remove thread
        override fun onDestroy() {
            super.onDestroy()
            mHandler.removeCallbacks(mDrawFrame)
        }


        //called when varaible changed
        override fun onVisibilityChanged(visible: Boolean) {
            mVisible = visible
            if (visible) {
                //call the drawFunction
                drawFrame()
            } else {
                //this is necessay to remove the call back
                mHandler.removeCallbacks(mDrawFrame)
            }
        }

        //called when surface destroyed
        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            mVisible = false
            //this is necessay to remove the call back
            mHandler.removeCallbacks(mDrawFrame)
        }


        // my function which contain the code to draw
        //this function contain the the main draw call
        /// this function need to call every time the code is executed
        // the thread call this function with some delay "drawspeed"
        fun drawFrame() {
            //getting the surface holder
            val holder = getSurfaceHolder()

            canvas = null  // canvas
            try {
                canvas = holder.lockCanvas()  //get the canvas
                if (canvas != null) {
                    // draw something
                    // my draw code

                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas)
            }

            // Reschedule the next redraw
            // this is the replacement for the invilidate funtion
            // every time call the drawFrame to draw the matrix
            mHandler.removeCallbacks(mDrawFrame)
            if (mVisible) {
                // set the execution delay
                mHandler.postDelayed(mDrawFrame, Drawspeed)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            // update when surface changed
        }
    }
}
