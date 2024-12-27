package com.anthonycr.mezzanine

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileReader = mezzanine(FileReader::class.java)

        val file = fileReader.readInTestJsonFile()

        findViewById<TextView>(R.id.fileTextView).text = file
    }
}
