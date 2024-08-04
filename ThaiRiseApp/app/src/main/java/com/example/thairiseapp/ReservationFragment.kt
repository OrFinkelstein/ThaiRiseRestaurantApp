package com.example.thairiseapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import kotlin.random.Random

// Colors
val green = Color.rgb(204,255,153)
val offWhite = Color.rgb(218,204,204)
val red = Color.rgb(255,0,0)
val darkGreen = Color.rgb(49,78,73)
val black = Color.rgb(0,0,0)

class ReservationFragment : DialogFragment() {

    // Creates the dialog view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.reservation,container,false)

        return rootView
    }

    // Controlling the dialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Exit from seat reservation
        val goBackBtn = view.findViewById<ImageButton>(R.id.goBack)

        goBackBtn.setOnClickListener {
            dismiss()
        }

        // For increase and decrease the number of guests
        val guestNumber = view.findViewById<TextView>(R.id.numberGuests)
        val plusBtn = view.findViewById<ImageButton>(R.id.plus)
        val minusBtn = view.findViewById<ImageButton>(R.id.minus)
        val largeGroup = view.findViewById<TextView>(R.id.large_groups)

        // Increases the number of guests, no more then 10, if more show a message
        plusBtn.setOnClickListener {
            var guestNumberInt = guestNumber.text.toString().toInt()
            // Increase
            if (guestNumberInt < 10) {
                guestNumberInt++
                guestNumber.text = guestNumberInt.toString()
                largeGroup.visibility = View.GONE
            // Large groups message
            } else {
                largeGroup.visibility = View.VISIBLE
            }
        }

        // Decreases the number of guests, no less then 0
        minusBtn.setOnClickListener {
            var guestNumberInt = guestNumber.text.toString().toInt()
            // Decrease
            if (guestNumberInt > 1) {
                guestNumberInt--
                guestNumber.text = guestNumberInt.toString()
                largeGroup.visibility = View.GONE
            }

        }

        // Button for choosing a date
        val dateBtn = view.findViewById<Button>(R.id.date_button)

        // Validates date
        val valid_date = view.findViewById<TextView>(R.id.valid_date)

        // Sets the button text to the chosen date
        val listenerDate = DatePickerDialog.OnDateSetListener { _, year, month, day ->

            // Get today's date
            val calendar = Calendar.getInstance()

            // The selected date
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)

           val date = "$day/${month + 1}/$year"

            // Check if selected date is before today's date, if yes inform the user
            if (selectedDate.before(calendar)) {
                dateBtn.setTextColor(red)
                dateBtn.text = getString(R.string.date)
                valid_date.visibility = View.VISIBLE
            } else {
                valid_date.visibility = View.GONE
                dateBtn.setTextColor(darkGreen)
                dateBtn.text = date
            }
        }

        // Determines the chosen date when the button is clicked
        dateBtn.setOnClickListener {

            // After invalid submit
            dateBtn.setTextColor(darkGreen)

            val c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                listenerDate,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // Button for choosing a time
        val timeBtn = view.findViewById<Button>(R.id.time_button)

        // Sets the button text to the chosen time
        val listenerTime = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            var time = "0"
            // Makes same pattern of 4 digits
            if (minute < 10) {
                if (hour < 10)
                    time = "0$hour:0$minute"
                else
                    time = "$hour:0$minute"
            } else if (hour < 10) {
                time = "0$hour:$minute"
            } else {
                time = "$hour:$minute"
            }
            timeBtn.text = time
        }

        // Determines the chosen time when the button is clicked
        timeBtn.setOnClickListener {

            // After invalid submit
            timeBtn.setTextColor(darkGreen)

            val c = Calendar.getInstance()
            val timeDialog = TimePickerDialog(
                requireContext(),
                listenerTime,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            )
            timeDialog.show()
        }

        val veganBtn = view.findViewById<ToggleButton>(R.id.vegan_button)

        // If the user is vegan so the background color will be green
        veganBtn.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked)
                veganBtn.setBackgroundColor(green)
            else
                veganBtn.setBackgroundColor(offWhite)
        }

        //The Radio Groups options
        val paymentRadio = view.findViewById<RadioGroup>(R.id.payment)
        val seatRadio = view.findViewById<RadioGroup>(R.id.seat)

        // Edit text options
        val nameEdit = view.findViewById<EditText>(R.id.name)
        val emailEdit = view.findViewById<EditText>(R.id.mail)
        val phoneEdit = view.findViewById<EditText>(R.id.phone)

        // Change the email text to black when clicked
        emailEdit.setOnFocusChangeListener { _, clicked ->
            if (clicked) {
                emailEdit.setTextColor(black)
            }
        }

        // Check Box for confirm details
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)
        val makeSure = view.findViewById<TextView>(R.id.make_sure)

        // Inform the customer to confirm and to make sure all the details are right
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkbox.setTextColor(black)
            if (isChecked) {
                makeSure.visibility = View.VISIBLE
            } else {
                makeSure.visibility = View.GONE
            }
        }

        // Submit button
        val submitBtn = view.findViewById<Button>(R.id.submit_button)

        // Submits the form when the submit button is clicked and creates a confirmation
        submitBtn.setOnClickListener {

            // Validation check for date and time and editTexts and checkbox, if the user didn't choose it will be colored red
            if (dateBtn.text == getString(R.string.date)){
                dateBtn.setTextColor(red)
            } else if (timeBtn.text == getString(R.string.time)) {
                timeBtn.setTextColor(red)
            } else if (nameEdit.text.toString().isEmpty()) {
                nameEdit.setHintTextColor(red)
            } else if (nameEdit.text.toString().isEmpty()) {
                emailEdit.setHintTextColor(red)
            } else if (!emailEdit.text.toString().contains("@")) {
                emailEdit.setTextColor(red)
                emailEdit.text = Editable.Factory.getInstance().newEditable(getString(R.string.valid_email))
            } else if (phoneEdit.text.toString().isEmpty()) {
                phoneEdit.setHintTextColor(red)
            } else if (!checkbox.isChecked) {
                checkbox.setTextColor(red)
            } else {
                // Generates a confirmation number
                val confirmation = "" + Random.nextInt(100000, 999999)
                val confirmationTab = confirmation[0] + " " + confirmation[1] + " " +
                        confirmation[2] + " " + confirmation[3] + " " +
                        confirmation[4] + " " + confirmation[5]

                // Creates a new dialog for confirmation
                val dialogSubmit = AlertDialog.Builder(requireContext())
                val dialogView: View = layoutInflater.inflate(R.layout.submit, null)
                dialogSubmit.setView(dialogView)

                // Determines the number of confirmation
                dialogView.findViewById<TextView>(R.id.number_confirm).text = confirmationTab

                // Sets the name, email and phone for confirmation
                dialogView.findViewById<TextView>(R.id.name_confirm).text = nameEdit.text
                dialogView.findViewById<TextView>(R.id.email_confirm).text = emailEdit.text
                dialogView.findViewById<TextView>(R.id.phone_confirm).text = phoneEdit.text

                // Sets number of guests
                dialogView.findViewById<TextView>(R.id.guests_confirm).text = guestNumber.text

                // Sets the time and date as chosen
                dialogView.findViewById<TextView>(R.id.date_confirm).text = dateBtn.text
                dialogView.findViewById<TextView>(R.id.time_confirm).text = timeBtn.text

                // Updates if the user is vegan
                if (veganBtn.isChecked) {
                    dialogView.findViewById<TextView>(R.id.vegan_confirm).text = getString(R.string.yes)
                }

                // Finds the checked button seat and updates the confirmation if it is not empty
                val checkedSeat = seatRadio.checkedRadioButtonId
                if (checkedSeat != -1) {
                    dialogView.findViewById<TextView>(R.id.seat_confirm).text = view.findViewById<RadioButton>(checkedSeat).text
                }

                // Finds the checked button payment and updates the confirmation if it is not empty
                val checkedPayment = paymentRadio.checkedRadioButtonId
                if (checkedPayment != -1) {
                    dialogView.findViewById<TextView>(R.id.payment_confirm).text = view.findViewById<RadioButton>(checkedPayment).text
                }

                // Creates the dialog
                val alertDialog = dialogSubmit.create()

                // For close the confirmation and reservation view
                dialogView.findViewById<Button>(R.id.done).setOnClickListener {
                    dismiss()
                    alertDialog.dismiss()
                }

                alertDialog.show()
            }
        }

    }

}
