package com.example.mediaplayback

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var playPauseButton: Button
    private lateinit var stopButton: Button
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        videoView = findViewById(R.id.videoView)
        playPauseButton = findViewById(R.id.playPauseButton)
        stopButton = findViewById(R.id.stopButton)

        // Set up media controller
        val controller = MediaController(this).apply {
            setMediaPlayer(videoView)
        }
        videoView.setMediaController(controller)

        // Set video source (replace with your video resource)
        val mediaName = "sample"
        val videoUri = Uri.parse("android.resource://$packageName/raw/$mediaName")
        videoView.setVideoURI(videoUri)

        // Prepare listener
        videoView.setOnPreparedListener { mediaPlayer ->
            if (currentPosition > 0) {
                videoView.seekTo(currentPosition)
            }
        }

        // Completion listener
        videoView.setOnCompletionListener {
            Toast.makeText(this, "Playback completed", Toast.LENGTH_SHORT).show()
            videoView.seekTo(0)
        }

        // Play/Pause button
        playPauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }

        // Stop button
        stopButton.setOnClickListener {
            videoView.stopPlayback()
            // Reload video
            videoView.setVideoURI(videoUri)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("CurrentPosition", videoView.currentPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentPosition = savedInstanceState.getInt("CurrentPosition", 0)
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }
}