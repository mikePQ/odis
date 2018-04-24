package pl.edu.agh.eaiib.io.odis.activities

import pl.edu.agh.eaiib.io.odis.hosts.Host
import java.util.*

data class  NetworkActivity(val activityId: String = UUID.randomUUID().toString(),
                           val srcAddress: InetAddress = InetAddress(),
                           val destAddress: InetAddress = InetAddress(),
                           val bytes: Long = 0,
                           val timestamp: Long = 0)

data class InetAddress(val host: Host = Host(),
                       val port: Int = 0)
