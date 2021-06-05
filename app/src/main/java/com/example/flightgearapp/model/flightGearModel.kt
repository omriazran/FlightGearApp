package com.example.flightgearapp.model

import java.net.Socket
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class flightGearModel(ip:String, port:String) {
    // joystick properties
    var aileron: Double = 0.0
        get() = field
        set(value)  {
            field = value
            val task = Runnable { this.client.outputStream.write(("set/controls/aileron $value\\r\\n").toByteArray()) }
            this.executor.execute(task)
        }
    var elevator: Double = 0.0
        get() = field
        set(value)  {
            field = value
            val task = Runnable { this.client.outputStream.write(("set/controls/elevator $value\\r\\n").toByteArray()) }
            this.executor.execute(task)
        }
    // scroll bars properties
    var rudder: Double = 0.0
        get() = field
        set(value)  {
            field = value
            val task = Runnable { this.client.outputStream.write(("set/controls/rudder $value\\r\\n").toByteArray()) }
            this.executor.execute(task)
        }
    var throttle: Double  = 0.0
        get() = field
        set(value) {
            field = value
            val task = Runnable { this.client.outputStream.write(("set/controls/throttle $value\\r\\n").toByteArray()) }
            this.executor.execute(task)
        }
    //fields
    private var client: Socket
    private var executor: Executor

    init {
        this.client = Socket(ip,port.toInt())
        this.executor = Executors.newSingleThreadExecutor()
    }

}