package pl.edu.agh.eaiib.io.odis.activities

import java.time.LocalDate

data class NetworkActivity(
        val id: Int = 0,
        val ip: String? = null,
        val date: LocalDate? = null
)
