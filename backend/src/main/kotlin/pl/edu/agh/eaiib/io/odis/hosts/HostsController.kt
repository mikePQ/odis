package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hosts")
class HostController(private val hostsService : HostsService) {
    @GetMapping
    fun getAllHosts(@RequestParam ip: String?): List<Host> {
        ip?.let {
            return hostsService.getHostWithIp(ip)
        }
        return hostsService.getAll()
    }
}

@RestController
@RequestMapping("/api/localHosts")
class LocalHostController(private val localHostsService : LocalHostsService) {
    @GetMapping
    fun getAllLocalHosts(): ResponseEntity<List<Host>> {
        return ResponseEntity(localHostsService.getAll(), HttpStatus.OK)
    }

    @PostMapping
    fun saveLocalHost(@RequestBody localHost: Host): ResponseEntity<Host> {
        val added =  localHostsService.saveHost(localHost)
        return ResponseEntity(added, HttpStatus.OK)
    }
}