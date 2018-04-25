package pl.edu.agh.eaiib.io.odis.domain.packet

import java.net.InetAddress

interface IPPacket : Packet {
    fun getSourceAddress(): InetAddress
    fun getDestinationAddress(): InetAddress
}