package com.example.flightgearapp.model

import android.util.Log
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executor
import java.util.concurrent.Executors
//The model class
class flightGearModel(ip:String, port:String) {
    // joystick properties
    var aileron: Double = 0.0
        get() = field
        set(value)  {
            field = value
            // build the attribute update as task and pass it to the executor
            val task = Runnable {
                this.out.printf("set /controls/flight/aileron $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    var elevator: Double = 0.0
        get() = field
        set(value)  {
            // * -1 for change of direction
            field = (value * -1)
            // build the attribute update as task and pass it to the executor
            val task = Runnable {
                this.out.printf("set /controls/flight/elevator $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    // scroll bars properties
    var rudder: Double = 0.0
        get() = field
        set(value)  {
            field = value
            // build the attribute update as task and pass it to the executor
            val task = Runnable {
                this.out.printf("set /controls/flight/rudder $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    var throttle: Double  = 0.0
        get() = field
        set(value) {
            field = value
            // build the attribute update as task and pass it to the executor
            val task = Runnable {
                this.out.printf("set /controls/engines/current-engine/throttle $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    //fields
    // client will connect to the FG app via TCP
    lateinit private var client: Socket
    // Out will pass the attributes values to the FG
    lateinit private var out : PrintWriter
    // executor will act as a singled thread threadPool
    private var executor: Executor

// constructor
    init {
        this.executor = Executors.newSingleThreadExecutor()
    // build the creation of socket and connection as task and pass is to the executor
        val task = Runnable {
            this.client = Socket()
            this.client.connect(InetSocketAddress(ip,port.toInt()),1500)
            this.out = PrintWriter(this.client.getOutputStream())
        }
        this.executor.execute(task)
    }

}