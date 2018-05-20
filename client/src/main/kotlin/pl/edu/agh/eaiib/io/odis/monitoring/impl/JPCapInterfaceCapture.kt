package pl.edu.agh.eaiib.io.odis.monitoring.impl

import jpcap.JpcapCaptor
import jpcap.NetworkInterface
import jpcap.packet.Packet
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringFilter
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.function.Consumer

open class JPCapInterfaceCapture(private val captor: JpcapCaptor?) : AutoCloseable {

    private val queue = LinkedBlockingDeque<Packet>()
    private val handlers = CopyOnWriteArrayList<Consumer<Packet>>()

    private val captureWorker = Runnable {
        while (!Thread.interrupted()) {
            val packet = queue.poll()
            if (packet != null) {
                for (handler in handlers) {
                    handler.accept(packet)
                }
            }
        }
    }

    fun start(monitoringFilter: MonitoringFilter = MonitoringFilter.EmptyFilter) {
        captor?.setFilter(monitoringFilter.getFilterConfig(), false)
        EXECUTOR.execute(captureWorker)
        captor?.loopPacket(-1) { p -> queue.offer(p) }
    }

    override fun close() {
        captor?.close()
    }

    fun addPacketConsumer(packetConsumer: Consumer<Packet>?) {
        handlers.add(packetConsumer)
    }

    companion object {
        private val EXECUTOR = Executors.newCachedThreadPool()
        private const val MAX_BYTES_AT_ONCE = Integer.MAX_VALUE
        private const val PROMISCUOUS_MODE = true
        private const val PROCESS_PACKET_TIMEOUT = 5000

        fun fromInterface(networkInterface: NetworkInterface): JPCapInterfaceCapture {
            val captor = JpcapCaptor
                    .openDevice(networkInterface, MAX_BYTES_AT_ONCE, PROMISCUOUS_MODE, PROCESS_PACKET_TIMEOUT)
            return JPCapInterfaceCapture(captor)
        }
    }
}