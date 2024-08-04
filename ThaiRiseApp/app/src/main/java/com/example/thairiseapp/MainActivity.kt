package com.example.thairiseapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Colors
val light_yellow = Color.rgb(255, 223, 128)
val white = Color.rgb(255,255,255)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handling the switcher for welcome greeting
        val welcSwitcher = findViewById<SwitchCompat>(R.id.lang_switcher)
        val langLogo = findViewById<TextView>(R.id.lang_logo)

        // Greeting / Language
        welcSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                langLogo.text = getString(R.string.welc)
            else {
                langLogo.text = getString(R.string.lang)
            }
        }

        // Encouraging the user to scroll down with a jumping up and down arrow
        val scrollDown = findViewById<ImageButton>(R.id.arrow_down)

        // Jumping arrow button for scrolling down
        val jumperAnim = TranslateAnimation(0f, 0f, 0f, 100f)
        jumperAnim.duration = 500L
        jumperAnim.interpolator = OvershootInterpolator()
        jumperAnim.repeatCount = Animation.INFINITE
        jumperAnim.repeatMode = Animation.REVERSE
        scrollDown.startAnimation(jumperAnim)

        // For scrolling down to main page
        val scroll = findViewById<ScrollView>(R.id.scroll)
        val mainPage = findViewById<LinearLayout>(R.id.main_page)

        // Go Down to main page
        scrollDown.setOnClickListener {

            // Get the y-coordinate of the main page
            val targetY = mainPage.y.toInt()

            // Scroll down to main page
            scroll.smoothScrollTo(0, targetY)
        }

        // For reserve seats
        val reservationBtn = findViewById<Button>(R.id.button_reservation)

        // Opens dialog if the reservation seats button is clicked
        reservationBtn.setOnClickListener {
            val dialog = ReservationFragment()
            dialog.show(supportFragmentManager, "Reservation")
        }

        // Menu button
        val bottomContentMenu = findViewById<LinearLayout>(R.id.dishes)
        val buttonMenu = findViewById<Button>(R.id.button_menu)
        var menu_clicked = false

        // Content of dishes options
        val mainCourseContent = findViewById<LinearLayout>(R.id.main_course_content)
        val startersContent = findViewById<LinearLayout>(R.id.starters_content)
        val drinksContent = findViewById<LinearLayout>(R.id.drinks_content)

        // handling clicks on dishes buttons options
        val buttonStarters = findViewById<Button>(R.id.starters_button)
        val buttonMainCourse = findViewById<Button>(R.id.main_course_button)
        val buttonDrinks = findViewById<Button>(R.id.drinks_button)

        // button : content - map
        val btnContent = mapOf(
            buttonStarters to startersContent,
            buttonMainCourse to mainCourseContent,
            buttonDrinks to drinksContent
        )

        // Description of Starters
        val somTamDesc = findViewById<LinearLayout>(R.id.som_tam_desc)
        val tomYumDesc = findViewById<LinearLayout>(R.id.tom_yum_desc)
        val springRollDesc = findViewById<LinearLayout>(R.id.spring_roll_desc)

        // handling clicks on Starters buttons options
        val buttonSomTam = findViewById<ImageButton>(R.id.som_tam_button)
        val buttonTomYum = findViewById<ImageButton>(R.id.tom_yum_button)
        val buttonSpringRoll = findViewById<ImageButton>(R.id.spring_roll_button)

        // Description of Main Courses
        val greenCurryDesc = findViewById<LinearLayout>(R.id.green_curry_desc)
        val padKraPaoDesc = findViewById<LinearLayout>(R.id.pad_kra_pao_desc)
        val padThaiDesc = findViewById<LinearLayout>(R.id.pad_thai_desc)

        // handling clicks on Main Course buttons options
        val buttonGreenCurry = findViewById<ImageButton>(R.id.green_curry_button)
        val buttonPadKraPao = findViewById<ImageButton>(R.id.pad_kra_pao_button)
        val buttonPadThai = findViewById<ImageButton>(R.id.pad_thai_button)

        // Description of Drinks
        val waterDesc = findViewById<LinearLayout>(R.id.water_desc)
        val colaDesc = findViewById<LinearLayout>(R.id.cola_desc)
        val coconutDesc = findViewById<LinearLayout>(R.id.coconut_desc)

        // handling clicks on Drinks buttons options
        val buttonWater = findViewById<ImageButton>(R.id.water_button)
        val buttonCola = findViewById<ImageButton>(R.id.cola_button)
        val buttonCoconut = findViewById<ImageButton>(R.id.coconut_button)

        // Click for details text
        val click_details = findViewById<LinearLayout>(R.id.details)

        // button : description - Map
        val btnDesc = mapOf(
            buttonSomTam to somTamDesc,
            buttonTomYum to tomYumDesc,
            buttonSpringRoll to springRollDesc,
            buttonGreenCurry to greenCurryDesc,
            buttonPadKraPao to padKraPaoDesc,
            buttonPadThai to padThaiDesc,
            buttonWater to waterDesc,
            buttonCola to colaDesc,
            buttonCoconut to coconutDesc
        )

        // Seek bar rate message and header
        val seekRate = findViewById<SeekBar>(R.id.seekRate)
        val rate = findViewById<TextView>(R.id.rate)
        val rating = findViewById<LinearLayout>(R.id.rate_layout)

        // Click on Menu button -> change color for text button and change the visibility

        buttonMenu.setOnClickListener {

            // Checked if the menu button is clicked or not
            menu_clicked = !menu_clicked

            // If menu button clicked, close the rating layout, otherwise shows the rating layout
            if (menu_clicked) {
                rating.visibility = View.GONE
            } else {
                rating.visibility = View.VISIBLE
                click_details.visibility = View.GONE
            }

            // open the content
            if (bottomContentMenu.visibility == View.GONE) {
                buttonMenu.setTextColor(light_yellow)
                bottomContentMenu.visibility = View.VISIBLE

                // close all children
            } else {
                // Changes text to white
                buttonMenu.setTextColor(white)
                for (entry in btnContent.entries.iterator()) {
                    entry.key.setTextColor(white)
                }

                // Closes all the content
                bottomContentMenu.visibility = View.GONE
                for (entry in btnContent.entries.iterator()) {
                    entry.value.visibility = View.GONE
                }

                // Closes all the dishes description and changes their background to white
                for (entry in btnDesc.entries.iterator()) {
                    entry.value.visibility = View.GONE
                    entry.key.setBackgroundColor(white)
                }

            }
        }

        // Click on Starters button -> show/unshow dishes and change color of text
        pressKindButton(buttonStarters, btnContent, btnDesc, click_details)


        // Click on Main Course button -> show/unshow dishes and change color of text
        pressKindButton(buttonMainCourse, btnContent, btnDesc, click_details)


        // Click on drinks button -> show/unshow dishes and change color of text
        pressKindButton(buttonDrinks, btnContent, btnDesc, click_details)


        // Press dish buttons - open and close the descriptions
        for (entry in btnDesc.entries.iterator()) {
            pressDishButton(entry.key, entry.value, btnDesc, click_details)
        }

        // Control the rate
        seekRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // var for save the progress value
            var progressVal = 0

            // Shows the rate value
            override fun onProgressChanged(seekRate: SeekBar, progress: Int, fromUser: Boolean) {
                rate.visibility = View.VISIBLE
                progressVal = progress.toString().toInt()
                rate.text = "${getString(R.string.your_rate)}  $progressVal"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do Nothing
            }

            // Gives a message from the restaurant after rating
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                rate.visibility = View.VISIBLE
                if (progressVal < 5) {
                    rate.text = getString(R.string.sorry)
                } else if (progressVal < 8)
                    rate.text = getString(R.string.feedback)
                else {
                    rate.text = getString(R.string.thanks)
                }
            }
        })

        // For go up button
        val goUpBtn = findViewById<Button>(R.id.go_up)
        val welcomePage = findViewById<TextView>(R.id.welcome)


        // Go back to welcome page
        goUpBtn.setOnClickListener {

            // Get the y-coordinate of the welcome page
            val targetY = welcomePage.y.toInt()

            // Scroll back to welcome page
            scroll.smoothScrollTo(0, targetY)
        }

    }

    /**
     *  Opens and close the menu details for the pressed mainBtn and show/unshow its content
     *  Also if it needs to show its content, it will close the content of the secondary buttons
     */
    private fun pressKindButton(
        mainBtn: Button, btnContent: Map<Button, LinearLayout>,
        btnDesc: Map<ImageButton, LinearLayout>, click_image: LinearLayout
    ) {

        mainBtn.setOnClickListener {

            // Closes all the dishes descriptions and changes their background color to white
            for (entry in btnDesc.entries.iterator()) {
                entry.value.visibility = View.GONE
                entry.key.setBackgroundColor(white)
            }

            // The content of the pressed button
            val mainContent = btnContent[mainBtn]

            if (mainContent == null)
                throw NullPointerException()

            if (mainContent.visibility == View.GONE) {

                // Shows the text for clicking
                click_image.visibility = View.VISIBLE

                // Opens the pressed button content and color it in blue
                mainBtn.setTextColor(light_yellow)
                mainContent.visibility = View.VISIBLE

                // Closes the secondary dishes options and set their color to white
                for (entry in btnContent.entries.iterator()) {
                    if (entry.key != mainBtn) {
                        entry.value.visibility = View.GONE
                        entry.key.setTextColor(white)
                    }
                }
            } else {
                // Closes the pressed button
                mainBtn.setTextColor(white)
                mainContent.visibility = View.GONE
                click_image.visibility = View.GONE
            }
        }
    }


    /**
     * Open and close the description of a dish by press the image
     */
    private fun pressDishButton(
        btn: ImageButton,
        desc: LinearLayout,
        btnDesc: Map<ImageButton,LinearLayout>,
        click_image: LinearLayout
    ) {
        btn.setOnClickListener {

            if (desc.visibility == View.GONE) {

                // Closes the click text
                click_image.visibility = View.GONE

                // Changes background color to yellow (clicked) and show description
                desc.visibility = View.VISIBLE
                btn.setBackgroundColor(light_yellow)

                // Closes the unclicked descriptions and changes their background color to white
                for (entry in btnDesc.entries.iterator())
                    if (entry.key != btn) {
                        entry.value.visibility = View.GONE
                        entry.key.setBackgroundColor(white)
                    }
            } else {
                // Close and change the background color to white
                desc.visibility = View.GONE
                click_image.visibility = View.VISIBLE
                btn.setBackgroundColor(white)
            }
        }

    }

}