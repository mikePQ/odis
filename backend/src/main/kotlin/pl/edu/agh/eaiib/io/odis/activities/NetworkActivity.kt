package pl.edu.agh.eaiib.io.odis.activities

import java.util.*

data class NetworkActivity(val activityId: String = UUID.randomUUID().toString(),
                           val timestamp: Long = 0,
                           val srcAddress: InetAddress = InetAddress(),
                           val destAddress: InetAddress = InetAddress())

data class InetAddress(private val ip: String = "",
                       private val name: String = "",
                       private val port: Int = 0)
