package com.go.mindconnect

import android.media.MediaPlayer
import java.util.TimerTask

class WolfTimer(var mMediaPlayer: MediaPlayer): TimerTask() {
    override fun run() {
        mMediaPlayer.start()
    }
}