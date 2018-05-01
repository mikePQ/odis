package pl.edu.agh.eaiib.io.odis.hosts

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