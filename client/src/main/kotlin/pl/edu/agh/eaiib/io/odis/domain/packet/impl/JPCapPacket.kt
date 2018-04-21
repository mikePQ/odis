package pl.edu.agh.eaiib.io.odis.domain.packet.impl

import pl.edu.agh.eaiib.io.odis.domain.packet.Packet

open class JPCapPacket(packet: jpcap.packet.Packet): Packet {
    private val timestamp: Long = packet.sec
    private val length: Int = packet.len
    private val capturedLength: Int = packet.caplen
    private val header: ByteArray = packet.header
    private val data: ByteArray = packet.data

    override fun getTimestamp(): Long = timestamp
    override fun getLength(): Int = length
    override fun getCapturedLength(): Int = capturedLength
    override fun getHeader(): ByteArray = header
    override fun getData(): ByteArray = data
}