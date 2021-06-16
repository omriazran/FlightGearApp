package com.example.flightgearapp.view
//note
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.flightgearapp.R
import com.example.flightgearapp.viewModel.flightGearVM

class MainActivity : AppCompatActivity(),JoystickView.JoystickListener {
    // fields
    var vm : flightGearVM? = null
    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ip and port text boxes
        val ipTxt = findViewById<EditText>(R.id.ipText)
        val portTxt = findViewById<EditText>(R.id.portText)

        // submit button
        val sub = findViewById<Button>(R.id.submit)
        //rudder and throttle seek bar
        val throttle = findViewById<SeekBar>(R.id.SeekBar)
        val rudder = findViewById<SeekBar>(R.id.SeeKBar4)

        // Setting On Click Listener
        sub.setOnClickListener {
            // Getting the input ip and port
            val inputIP = ipTxt.text.toString()
            val inputPort = portTxt.text.toString()
            //creating viewModel
            vm = flightGearVM()
            // viewModel creates model
            vm!!.createModel(inputIP, inputPort)
            sub.text = "submitted"
            sub.setBackgroundColor(Color.parseColor("#90ee90"))
        }

        // define throttle listeners
        throttle?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (vm == null){
                   return
               }
                // change range from 0 - 100 to 0 - 1
                if (seekBar != null) {
                    vm!!.VM_throttle = (seekBar.progress / 100.0)
                }

            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })
        // define rudder listeners
        rudder?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (vm == null){
                    return
                }
                // change range from 0 - 100 to -1 - 1
                if (seekBar != null) {
                    vm!!.VM_rudder = ((seekBar.progress  - 50.0) / 50.0)
                }

            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

    }
    // binding the joystick movement to viewModel attributes
    override fun onJoystickMoved(aileron: Float, elevator: Float, source: Int) {
        if (vm == null){
            return
        }
        vm!!.VM_aileron = aileron.toDouble()
        vm!!.VM_elevator = elevator.toDouble()
    }

}