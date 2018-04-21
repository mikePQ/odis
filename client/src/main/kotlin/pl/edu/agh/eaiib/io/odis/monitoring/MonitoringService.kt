package pl.edu.agh.eaiib.io.odis.monitoring;

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface

interface MonitoringService {

    /**
     * @return list of currently monitored network interfaces
     */
    fun getMonitoredInterfaces(): List<NetworkInterface>

    /**
     * @param networkInterface
     * @param filter
     */
    fun startMonitoringInterface(networkInterface: NetworkInterface, filter: MonitoringFilter)

    /**
     * @param listener
     * @param monitoredInterfaces list of monitored interfaces to listen for events,
     *                            by default events from all interfaces are send
     */
    fun addMonitorListener(listener: MonitorListener, monitoredInterfaces: List<NetworkInterface> = emptyList())
}
