package pl.edu.agh.eaiib.io.odis.domain.packet.impl

import pl.edu.agh.eaiib.io.odis.domain.packet.IPPacket
import java.net.InetAddress

class JPCapIPPacket(packet: jpcap.packet.IPPacket) : IPPacket {
    private val sourceAddress: InetAddress = packet.src_ip
    private val destinationAddress: InetAddress = packet.dst_ip
    private val timestamp: Long = packet.sec
    private val length: Int = packet.len
    private val capturedLength: Int = packet.caplen
    private val header: ByteArray = packet.header
    private val data: ByteArray = packet.data

    override fun getSourceAddress(): InetAddress = sourceAddress
    override fun getDestinationAddress(): InetAddress = destinationAddress
    override fun getTimestamp(): Long = timestamp
    override fun getLength(): Int = length
    override fun getCapturedLength(): Int = capturedLength
    override fun getHeader(): ByteArray = header
    override fun getData(): ByteArray = data
}