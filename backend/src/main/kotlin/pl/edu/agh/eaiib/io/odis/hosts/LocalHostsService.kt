package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.stereotype.Service
import pl.edu.agh.eaiib.io.odis.activities.ActivitiesRepository

@Service
class LocalHostsService(private val localHostsRepository: LocalHostsRepository) {
    fun getAll(): List<Host> {
        return localHostsRepository.findAll();
    }

    fun saveHost(host: Host) : Host = localHostsRepository.save(host)
}