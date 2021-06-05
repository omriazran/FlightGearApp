package com.example.flightgearapp.viewModel

import com.example.flightgearapp.model.flightGearModel


class flightGearVM() {
    //VM properties
    // joystick properties
    var VM_aileron: Double
        get() = model.aileron
        set(value)  {
           model.aileron = value
        }
    var VM_elevator: Double
        get() = model.elevator
        set(value)  {
            model.elevator = value
        }
    // scroll bars properties
    var VM_rudder: Double
        get() = model.rudder
        set(value)  {
           model.rudder = value
        }
    var VM_throttle: Double
        get() = model.throttle
        set(value) {
           model.throttle = value
        }
    lateinit var model: flightGearModel
    //initiate when user press on submit
    fun createModel(ip:String, sock:String){
        this.model = flightGearModel(ip,sock)
    }
}