package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesRepository

@Service
class HostsService(private val activitiesRepository: ActivitiesRepository) {
    fun getAll(): List<Host> = activitiesRepository.findAll()
            .asSequence()
            .map { it.srcAddress.host }
            .toSet()
            .asSequence()
            .toList()

    fun getHostWithIp(ip: String): List<Host> {
        return getAll().filter { it.ip == ip }
    }
}