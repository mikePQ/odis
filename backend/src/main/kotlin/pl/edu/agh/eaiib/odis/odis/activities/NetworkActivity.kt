package pl.edu.agh.eaiib.odis.odis.activities

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class NetworkActivity(
        @Id
        @GeneratedValue
        val id: Int = 0,
        val ip: String? = null,
        val date: LocalDate? = null
)
