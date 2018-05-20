package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.stereotype.Service

@Service
class LocalHostsService(private val localHostsRepository: LocalHostsRepository) {
    fun getAll(): List<Host> {
        return localHostsRepository.findAll()
    }

    fun saveHost(host: Host): Host = localHostsRepository.save(host)
}