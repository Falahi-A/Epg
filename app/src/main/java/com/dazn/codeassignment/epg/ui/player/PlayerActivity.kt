package com.dazn.codeassignment.epg.ui.player

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dazn.codeassignment.epg.R
import com.dazn.codeassignment.epg.databinding.ActivityPlayerBinding
import com.dazn.codeassignment.epg.ui.base.BaseBindingActivity
import com.dazn.codeassignment.epg.utils.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

/**
 * VideoPlayer Activity
 */
class PlayerActivity : BaseBindingActivity<ActivityPlayerBinding>() {
    private var videoUrl: String? = null
    private lateinit var exoPlayer: SimpleExoPlayer
    private var fullscreen = false
    private lateinit var exoFullScreenButton:FrameLayout
    private lateinit var exoFullScreenIcon:ImageView


    override val bindingInflater: (LayoutInflater) -> ActivityPlayerBinding = { layoutInflater ->
        ActivityPlayerBinding.inflate(layoutInflater)
    }


    override fun initView() {
        videoUrl = intent.getStringExtra(Constants.VIDEO_URL_KEY)

        videoUrl?.let {
            setupPlayer(it)
        } ?: Toast.makeText(this, "Video url is empty", Toast.LENGTH_SHORT).show()

    }


    private fun setupPlayer(videoUrl: String) {

        //init exoPLayer
        exoPlayer = SimpleExoPlayer.Builder(this).build().also { player ->
            binding.playerView.player = player //binding exoPLayer to it's view
        }


        // preparing appropriate format item for exoPlayer
        val mediaItem =
            MediaItem.fromUri(videoUrl)


        //playing ExoPLayer
        exoPlayer.apply {
            addMediaItem(mediaItem)
            prepare()
            play()
        }

         exoFullScreenButton =
            binding.playerView.findViewById<FrameLayout>(R.id.exo_fullscreen_button)
         exoFullScreenIcon = binding.playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon)

        exoFullScreenButton
            .setOnClickListener {

                if (fullscreen) {   // Check if screen is in full screen mode or not
                  displayNormalScreen()
                } else {
                  displayFullScreen()
                }
            }

    }

    private fun displayFullScreen() {
        // Change fullScreen Icon
        exoFullScreenIcon.setImageDrawable(
            ContextCompat.getDrawable(
                this@PlayerActivity,
                R.drawable.ic_fullscreen_close
            )
        )

        hideSystemUI()

        if (supportActionBar != null) { // Hide ActionBar if it exist
            supportActionBar!!.hide()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val params = binding.playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.playerView.layoutParams = params
        fullscreen = true
    }

    private fun displayNormalScreen() {
        // Change fullScreen Icon
        exoFullScreenIcon.setImageDrawable(
            ContextCompat.getDrawable(
                this@PlayerActivity,
                R.drawable.ic_fullscreen_open
            )
        )
        showSystemUI()

        if (supportActionBar != null) { //Show ActionBar if it exist
            supportActionBar!!.show()
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val params = binding.playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height =
            (200 * applicationContext.resources.displayMetrics.density).toInt()
        binding.playerView.layoutParams = params
        fullscreen = false
    }

    /**
     * FullScreen state
     */
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }


    /**
     * NormalScreen state
     */
    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            binding.root
        ).show(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }


}