package pl.edu.agh.eaiib.odis.odis

import jpcap.JpcapCaptor
import jpcap.packet.Packet

interface PacketHandler {
    fun handle(packet: Packet)
}

class DummyHandler : PacketHandler {
    override fun handle(packet: Packet) {
        println(packet)
    }
}

fun main(args: Array<String>) {
    val device = JpcapCaptor.getDeviceList().first()
    val captor = JpcapCaptor.openDevice(device, MAX_BYTES_AT_ONCE, PROMISCUOUS_MODE, PROCESS_PACKET_TIMEOUT)
    captor.setFilter(FILTER, false)
    val capture = Capture(captor)
    capture.addHandler(DummyHandler())
    capture.start()
}

private const val MAX_BYTES_AT_ONCE = 2000
private const val PROMISCUOUS_MODE = false
private const val PROCESS_PACKET_TIMEOUT = 5000
private const val FILTER = ""