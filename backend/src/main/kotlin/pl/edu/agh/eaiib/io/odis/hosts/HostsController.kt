package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hosts")
class HostController(private val hostsService : HostsService) {
    @GetMapping
    fun getAllHosts(): List<Host> = hostsService.getAll()
}