package com.example.flightgearapp.view
//note
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.flightgearapp.R
import com.example.flightgearapp.viewModel.flightGearVM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ip and port text boxes
        val ipTxt = findViewById<EditText>(R.id.ipText)
        val portTxt = findViewById<EditText>(R.id.portText)
        // submit button
        val sub = findViewById<Button>(R.id.submit)
        // Setting On Click Listener
        sub.setOnClickListener {
            // Getting the input ip and port
            val inputIP = ipTxt.text.toString()
            val inputPort = portTxt.text.toString()
            //creating viewModel
            var vm = flightGearVM()
            // viewModel creates model
            vm.createModel(inputIP, inputPort)
        }

    }
}