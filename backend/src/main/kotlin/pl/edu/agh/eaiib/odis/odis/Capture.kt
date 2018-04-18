package pl.edu.agh.eaiib.odis.odis

import jpcap.JpcapCaptor
import jpcap.NetworkInterface
import jpcap.packet.Packet
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque

class Capture(private val captor: JpcapCaptor) : AutoCloseable {
    private val queue = LinkedBlockingDeque<Packet>(10)
    private val handlers = CopyOnWriteArrayList<PacketHandler>()

    private val captureWorker = Runnable {
        while (!Thread.interrupted()) {
            val packet = queue.poll()
            if (packet != null) {
                for (handler in handlers) {
                    handler.handle(packet)
                }
            }
        }
    }

    fun start() {
        EXECUTOR.execute(captureWorker)
        captor.loopPacket(-1) { p -> queue.offer(p) }
    }

    override fun close() {
        captor.close()
    }

    fun addHandler(handler: PacketHandler) {
        handlers.add(handler)
    }

    fun removeHandler(handler: PacketHandler) {
        handlers.remove(handler)
    }

    companion object {
        private val EXECUTOR = Executors.newCachedThreadPool()

        fun fromFile(filename: String): Capture {
            val captor: JpcapCaptor = JpcapCaptor.openFile(filename)
            return Capture(captor)
        }

        fun fromInterface(iface: NetworkInterface): Capture {
            val captor = JpcapCaptor.openDevice(iface, Integer.MAX_VALUE, true, 1000)
            return Capture(captor)
        }
    }
}