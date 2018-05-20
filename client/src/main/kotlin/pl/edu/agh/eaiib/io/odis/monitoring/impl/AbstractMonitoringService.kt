package pl.edu.agh.eaiib.io.odis.monitoring.impl

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet
import pl.edu.agh.eaiib.io.odis.monitoring.MonitorListener
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringFilter
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.CopyOnWriteArrayList

abstract class AbstractMonitoringService : MonitoringService {
    private val monitoredInterfaces: ConcurrentMap<NetworkInterface, CopyOnWriteArrayList<MonitoringFilter>>
            = ConcurrentHashMap<NetworkInterface, CopyOnWriteArrayList<MonitoringFilter>>()

    private val monitorListeners = CopyOnWriteArrayList<MonitorListener>()

    override fun getMonitoredInterfaces(): List<NetworkInterface> = ArrayList(monitoredInterfaces.keys)

    override fun startMonitoringInterface(networkInterface: NetworkInterface, filter: MonitoringFilter) {
        val newFilters = CopyOnWriteArrayList<MonitoringFilter>()
        val oldFilters = monitoredInterfaces.putIfAbsent(networkInterface, newFilters)
        val filters = oldFilters ?: newFilters
        filters.addIfAbsent(filter)
        startMonitoringInterfaceImpl(networkInterface, filter)
    }

    override fun addMonitorListener(listener: MonitorListener) {
        monitorListeners.addIfAbsent(listener)
    }

    abstract fun startMonitoringInterfaceImpl(networkInterface: NetworkInterface, filter: MonitoringFilter)

    fun notifyPacketReceived(packet: Packet, networkInterface: NetworkInterface) {
        monitorListeners.filter { it.monitorsInterface(networkInterface) }
                .forEach { it.packetReceived(packet, networkInterface) }
    }
}