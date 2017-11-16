package com.example.pc.myapplication

class liveWallpaper : WallpaperService {
    override fun onCreateEngine(){
        mcontext = this; // set current context

        return new LiveWall(); // This call contains the wallpaper code
    }

    /**
        This class extends the engine for the live wallpaper
        implements all the draw calls required to draw the wallpaper
     */
     inner class LiveWall {

    }
}
