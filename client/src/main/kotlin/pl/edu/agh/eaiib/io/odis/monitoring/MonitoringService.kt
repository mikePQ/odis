package pl.edu.agh.eaiib.io.odis.monitoring;

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface

interface MonitoringService {
    fun getMonitoredInterfaces(): List<NetworkInterface>
    fun startMonitoringInterface(networkInterface: NetworkInterface, filter: MonitoringFilter)
    fun addMonitorListener(listener: MonitorListener)
}
