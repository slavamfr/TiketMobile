package com.example.tiket

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        // Retrieve data from intent
        val nama = intent.getStringExtra("EXTRA_NAMA")
        val jam = intent.getStringExtra("EXTRA_JAM")
        val tanggal = intent.getStringExtra("EXTRA_TANGGAL")
        val tujuan = intent.getStringExtra("EXTRA_TUJUAN")

        // Set data to TextViews
        findViewById<TextView>(R.id.namaTextView).text = "Nama: $nama"
        findViewById<TextView>(R.id.jamTextView).text = "Jam: $jam"
        findViewById<TextView>(R.id.tanggalTextView).text = "Tanggal: $tanggal"
        findViewById<TextView>(R.id.tujuanTextView).text = "Tujuan: $tujuan"
    }
}
