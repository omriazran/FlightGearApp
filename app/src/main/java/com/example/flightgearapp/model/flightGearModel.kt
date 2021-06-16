package com.example.flightgearapp.model

import android.util.Log
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class flightGearModel(ip:String, port:String) {
    // joystick properties
    var aileron: Double = 0.0
        get() = field
        set(value)  {
            field = (value * -1)
            val task = Runnable {
                this.out.printf("set /controls/flight/aileron $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    var elevator: Double = 0.0
        get() = field
        set(value)  {
            field = value
            //  val task = Runnable { this.client.outputStream.write(("set /controls/flight/elevator " + value + "\\r\\n").toByteArray()) }
            val task = Runnable {
                // change direction
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
           // val task = Runnable { this.client.getOutputStream().write(("set /controls/flight/rudder " + value +  "\\r\\n").toByteArray())
           // this.client.getOutputStream().flush()}
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
         //   val task = Runnable { this.client.outputStream.write(("set /controls/engines/current-engine/throttle $value\\r\\n").toByteArray()) }

            val task = Runnable {
                this.out.printf("set /controls/engines/current-engine/throttle $field\r\n")
                this.out.flush()
            }
            this.executor.execute(task)
        }
    //fields
    lateinit private var client: Socket
    lateinit private var out : PrintWriter
    private var executor: Executor


    init {
        this.executor = Executors.newSingleThreadExecutor()
        val task = Runnable {
            this.client = Socket()
            this.client.connect(InetSocketAddress(ip,port.toInt()),1500)
            this.out = PrintWriter(this.client.getOutputStream())


//            this.client = Socket(ip,port.toInt())
        }
        this.executor.execute(task)
    }

}