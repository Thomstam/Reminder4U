package com.example.reminder4u

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.view.setPadding
import java.io.InputStream

class FormSetup : AppCompatActivity() {

    private val SELECT_IMAGE = 101
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private var notesText: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_setup)

        setDueTime()

        setNotificationSpinner()

        setRepeatSpinner()

        setRemindersImage()

        setViews()

        setRemindersNotes()
    }

    private fun setRemindersImage() {
        val remindersImageButton = findViewById<AppCompatButton>(R.id.form_setup_image_button)
        remindersImageButton.setOnClickListener {
            val permission =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permission == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
            } else {
                verifyStoragePermissions(this)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun setRemindersNotes() {
        val remindersNotes = findViewById<AppCompatButton>(R.id.form_setup_notes_button)
        remindersNotes.setOnClickListener {

            val editText = EditText(this)
            editText.setMaxLength(150)
            editText.setTextColor(resources.getColor(R.color.black))
            editText.setPadding(16)

            if (notesText.isNotEmpty()) {
                editText.setText(notesText)
            }

            val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Notes")
                .setPositiveButton("Done") { _, _ ->
                    if (editText.text.toString() != "") {
                        notesText = editText.text.toString()
                        remindersNotes.setBackgroundResource(R.drawable.form_setup_round_corners_red)
                    } else {
                        remindersNotes.setBackgroundResource(R.drawable.form_setup_edittext_round_corners)
                    }
                }
                .setNeutralButton("Clear Text") { _, _ ->
                    notesText = ""
                    remindersNotes.setBackgroundResource(R.drawable.form_setup_edittext_round_corners)
                }
                .setView(editText)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.form_setup_edittext_round_corners)
            dialog.show()
        }
    }

    private fun EditText.setMaxLength(maxLength: Int) {
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                val inputStream: InputStream? =
                    data?.data?.let { this.contentResolver.openInputStream(it) }
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 64, 60, true)
            }
        }
    }

    private fun setViews() {
        val reminderName = findViewById<EditText>(R.id.form_setup_reminder_name)
        val dueDate = findViewById<CalendarView>(R.id.form_setup_due_date)
    }

    private fun setNotificationSpinner() {
        val notificationSpinner = findViewById<Spinner>(R.id.form_setup_notification_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.notification_list,
            R.layout.form_setup_adapters_textview
        )
        notificationSpinner.adapter = adapter
    }

    private fun setRepeatSpinner() {
        val repeatSpinner = findViewById<Spinner>(R.id.form_setup_repeat_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.repeat_list,
            R.layout.form_setup_adapters_textview
        )
        repeatSpinner.adapter = adapter
    }

    private fun setTransportRadioGroup() {
    }


    private fun setDueTime() {
        val dueTime = findViewById<TimePicker>(R.id.form_setup_due_time)
        dueTime.setIs24HourView(true)
    }

    private fun verifyStoragePermissions(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}