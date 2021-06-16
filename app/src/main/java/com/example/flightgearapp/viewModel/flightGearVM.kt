package com.example.flightgearapp.viewModel

import com.example.flightgearapp.model.flightGearModel


class flightGearVM() {
    //fields
    lateinit var model: flightGearModel

    //VM properties
    // joystick properties
    var VM_aileron: Double
        get() = model.aileron
        // binding to model aileron
        set(value)  {
           model.aileron = value
        }
    var VM_elevator: Double
        get() = model.elevator
        // binding to model elevator
        set(value)  {
            model.elevator = value
        }
    // scroll bars properties
    var VM_rudder: Double
        get() = model.rudder
        // binding to model rudder
        set(value)  {
           model.rudder = value
        }
    var VM_throttle: Double
        get() = model.throttle
        // binding to model throttle
        set(value) {
           model.throttle = value
        }
    //initiate when user press on submit
    fun createModel(ip:String, sock:String){
        this.model = flightGearModel(ip,sock)
    }
}