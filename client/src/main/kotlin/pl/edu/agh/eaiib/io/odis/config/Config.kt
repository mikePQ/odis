package pl.edu.agh.eaiib.io.odis.config

import pl.edu.agh.eaiib.io.odis.monitoring.MonitoringFilter
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

class Config(val serverBaseUrl: String,
             val publishPeriod: Int,
             val monitoredInterfacesIds: List<String>,
             val monitoringFilter: MonitoringFilter) {

    companion object {
        private const val SERVER_BASE_URL_KEY = "server.base.url"
        private const val PUBLISH_PERIOD_KEY = "publish.period"
        private const val MONITORED_INTERFACES_KEY = "monitor.interfaces"
        private const val MONITORING_FILTER_KEY = "monitor.filter"

        fun fromInputStream(inputStream: InputStream): Config {
            val properties = Properties()
            properties.load(inputStream)

            val serverBaseUrl = readProperty(properties, SERVER_BASE_URL_KEY)
            val publishPeriod = readProperty(properties, PUBLISH_PERIOD_KEY).toInt()
            val monitoredInterfaces = readCsv(readProperty(properties, MONITORED_INTERFACES_KEY, ""))
            val monitoringFilter = readProperty(properties, MONITORING_FILTER_KEY, "")

            return Config(serverBaseUrl, publishPeriod, monitoredInterfaces, object : MonitoringFilter {
                override fun getFilterConfig(): String = monitoringFilter
            })
        }

        private fun readProperty(properties: Properties, property: String, defaultValue: String? = null): String {
            val value = properties.getProperty(property)
            if (value != null) {
                return value
            }

            if (defaultValue != null) {
                return defaultValue
            }

            throw IllegalArgumentException("Property $property is required")
        }

        private fun readCsv(csvString: String): List<String> {
            return csvString.split(",").map { it.trim() }
        }
    }
}