package pl.edu.agh.eaiib.io.odis.domain.packet.impl

import pl.edu.agh.eaiib.io.odis.domain.packet.IPPacket
import java.net.InetAddress

class JPCapIPPacket(packet: jpcap.packet.IPPacket) : JPCapPacket(packet), IPPacket {
    private val sourceAddress: InetAddress = packet.src_ip
    private val destinationAddress: InetAddress = packet.dst_ip

    override fun getSourceAddress(): InetAddress = sourceAddress
    override fun getDestinationAddress(): InetAddress = destinationAddress
}