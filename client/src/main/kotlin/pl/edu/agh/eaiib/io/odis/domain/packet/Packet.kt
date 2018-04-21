package pl.edu.agh.eaiib.io.odis.domain.packet

interface Packet {
    fun getTimestamp(): Long
    fun getLength(): Int
    fun getCapturedLength(): Int
    fun getHeader(): ByteArray
    fun getData(): ByteArray
}