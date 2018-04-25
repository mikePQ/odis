package pl.edu.agh.eaiib.io.odis.domain

import java.net.InetAddress

interface NetworkInterface {
    fun getId(): String
    fun getAddresses(): List<InetAddress>
}