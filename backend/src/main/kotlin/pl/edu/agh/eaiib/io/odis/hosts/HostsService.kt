package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesRepository

@Service
class HostsService(private val activitiesRepository: ActivitiesRepository) {
    fun getAll() : List<Host> {
        val allHosts: MutableSet<Host> = mutableSetOf()

        val allActivities = activitiesRepository.findAll()
        allActivities.asSequence().forEach { allHosts.add(it.srcAddress.host) }
        allActivities.asSequence().forEach { allHosts.add(it.destAddress.host) }
        return allHosts.asSequence().toList()
    }

    fun getHostWithIp(ip: String): List<Host> {
        val allHostsWithIp: MutableSet<Host> = mutableSetOf();
        val associatedActivites = activitiesRepository.findAll().filter { it.srcAddress.host.ip == ip || it.destAddress.host.ip == ip }
        associatedActivites.asSequence().forEach { allHostsWithIp.add(it.srcAddress.host) }
        associatedActivites.asSequence().forEach { allHostsWithIp.add(it.destAddress.host) }
        return allHostsWithIp.asSequence().toList()
    }
}