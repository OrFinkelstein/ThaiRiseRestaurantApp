package com.example.thairiseapp

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private val TIME = 6400L

class LoadScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_screen)

        // The widgets on the screen
        val noodles = findViewById<ImageView>(R.id.noodles)
        val chop1 = findViewById<ImageView>(R.id.chopstick1)
        val chop2 = findViewById<ImageView>(R.id.chopstick2)
        val name = findViewById<TextView>(R.id.name)

        // Creates Animation for fade in of the bowl
        val fadeInAnimatorBowl: Animator = ObjectAnimator.ofFloat(noodles, "alpha", 0f, 1f).setDuration(1000)

        // Creates Animation for spinning the rice bowl
        val rotateAnimator = ObjectAnimator.ofFloat(noodles, "rotation", 0f, 360f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 2
        }

        // Creates Animation for fade in of chop1
        val fadeInAnimatorChop1: Animator = ObjectAnimator.ofFloat(chop1, "alpha", 0f, 1f).setDuration(100)

        // Creates Animation for fade in of chop2
        val fadeInAnimatorChop2: Animator = ObjectAnimator.ofFloat(chop2, "alpha", 0f, 1f).setDuration(100)

        // Creates Animation for Chopstick1
        val translationXAnimatorChop1 = ObjectAnimator.ofFloat(chop1, "translationX", 0f, -350f).also {
            it.duration = 500
        }
        val translationYAnimatorChop1 = ObjectAnimator.ofFloat(chop1, "translationY", 0f,350f).also {
            it.duration = 500
        }

        // Creates AnimatorSet for Chopstick1
        val animatorSetChop1 = AnimatorSet().apply {
            interpolator = AccelerateDecelerateInterpolator()
            playTogether(translationXAnimatorChop1, translationYAnimatorChop1)
        }

        // Creates Animation for Chopstick2
        val translationXAnimatorChop2 = ObjectAnimator.ofFloat(chop2, "translationX", 0f, 350f).also {
            it.duration = 500
        }
        val translationYAnimatorChop2 = ObjectAnimator.ofFloat(chop2, "translationY", 0f,350f).also {
            it.duration = 500
        }

        // Create AnimatorSet for Chopstick2
        val animatorSetChop2 = AnimatorSet().apply {
            interpolator = AccelerateDecelerateInterpolator()
            playTogether(translationXAnimatorChop2, translationYAnimatorChop2)
        }

        // Creates Animation for fade in of the restaurant name
        val fadeInAnimatorName: Animator = ObjectAnimator.ofFloat(name, "alpha", 0f, 0.85f).setDuration(1000)

        // Plays the animations sequentially
        val animatorSet = AnimatorSet().apply {
            playSequentially(fadeInAnimatorBowl, rotateAnimator, fadeInAnimatorChop1,
                             animatorSetChop1, fadeInAnimatorChop2, animatorSetChop2, fadeInAnimatorName)
        }

        // Starts the animation set
        animatorSet.start()

        android.os.Handler().postDelayed({
            // Starts main activity
            startActivity(Intent(this, MainActivity::class.java))
            // Close the splash screen
            finish()
        }, TIME)

    }
}