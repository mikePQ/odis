package pl.edu.agh.eaiib.io.odis.monitoring

interface MonitoringFilter {
    fun getFilterConfig(): String

    object EmptyFilter: MonitoringFilter {
        override fun getFilterConfig(): String = ""
    }
}