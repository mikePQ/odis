package pl.edu.agh.eaiib.io.odis

import pl.edu.agh.eaiib.io.odis.config.Config
import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringService
import pl.edu.agh.eaiib.io.odis.monitoring.impl.JPCapMonitoringService
import pl.edu.agh.eaiib.io.odis.rest.NetworkActivityPublisher

class OdisClientApplication(private val config: Config) {

    fun start(monitoringService: MonitoringService) {
        val activitiesPublisher = NetworkActivityPublisher(config.serverBaseUrl, config.publishPeriod)
        monitoringService.addMonitorListener(activitiesPublisher)

        val monitoringFilter = config.monitoringFilter
        val interfaces = if (config.monitoredInterfacesIds.isEmpty()) {
            monitoringService.getAvailableInterfaces()
        } else {
            config.monitoredInterfacesIds.mapNotNull { monitoringService.findNetworkInterface(it) }
        }

        interfaces.forEach { monitoringService.startMonitoringInterface(it, monitoringFilter) }
    }

}

fun main(args: Array<String>) {
    val config = Config.fromInputStream(OdisClientApplication::class.java.getResourceAsStream(DEFAULT_CONFIG_FILE))
    val app = OdisClientApplication(config)
    val monitoringService = JPCapMonitoringService()
    app.start(monitoringService)
}

private const val DEFAULT_CONFIG_FILE = "config.properties"