package pl.edu.agh.eaiib.io.odis.domain.packet

interface TCPPacket : IPPacket {
    fun getSourcePort(): Int
    fun getDestinationPort(): Int
}