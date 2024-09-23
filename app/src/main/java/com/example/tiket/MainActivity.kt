package com.example.tiket

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val hargaTiketMap = mapOf(
        "Jakarta" to 200_000,
        "Bandung" to 150_000,
        "Solo" to 100_000
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val namaPemesan: EditText = findViewById(R.id.namaPemesan)
        val timePicker: TextView = findViewById(R.id.jamKeberangkatan)
        val datePicker: TextView = findViewById(R.id.tanggalKeberangkatan)
        val tujuanSpinner: Spinner = findViewById(R.id.tujuan_spinner)
        val pesanTiketButton: Button = findViewById(R.id.pesanTiket)

        ArrayAdapter.createFromResource(
            this,
            R.array.tujuan_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tujuanSpinner.adapter = adapter
        }

        // Logic for date picker
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        datePicker.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                datePicker.text = dateFormat.format(calendar.time)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // Logic for time picker
        timePicker.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_time_picker, null)
            val timePickerDialog = dialogView.findViewById<TimePicker>(R.id.timePicker)
            timePickerDialog.setIs24HourView(true)

            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)

            val dialog = builder.create()

            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
            val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

            btnCancel.setOnClickListener { dialog.dismiss() }

            btnOk.setOnClickListener {
                val hour = timePickerDialog.hour
                val minute = timePickerDialog.minute
                val selectedTime = String.format("%02d:%02d", hour, minute)
                timePicker.text = selectedTime
                dialog.dismiss()
            }

            dialog.show()
        }

        // Button action to show selected details
        pesanTiketButton.setOnClickListener {
            val nama = namaPemesan.text.toString()
            val jam = timePicker.text.toString()
            val tanggal = datePicker.text.toString()
            val tujuan = tujuanSpinner.selectedItem.toString()

            // Get the ticket price based on the selected city
            val harga = hargaTiketMap[tujuan] ?: 0

            // Show confirmation dialog with price
            showConfirmationDialog(nama, jam, tanggal, tujuan, harga)
        }
    }

    private fun showConfirmationDialog(nama: String, jam: String, tanggal: String, tujuan: String, harga: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        val hargaTiketTextView = dialogView.findViewById<TextView>(R.id.hargaTiket)
        hargaTiketTextView.text = "Rp${String.format("%,d", harga)}"

        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)

        cancelButton.setOnClickListener { dialog.dismiss() }

        confirmButton.setOnClickListener {
            dialog.dismiss()
            // Proceed to ConfirmationActivity
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("EXTRA_NAMA", nama)
                putExtra("EXTRA_JAM", jam)
                putExtra("EXTRA_TANGGAL", tanggal)
                putExtra("EXTRA_TUJUAN", tujuan)
                putExtra("EXTRA_HARGA", harga)
            }
            startActivity(intent)
        }

        dialog.show()
    }
}

