package pl.edu.agh.eaiib.io.odis.activities

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activities")
class ActivitiesController(private val activitiesService: ActivitiesService) {

    @PostMapping
    fun addActivities(@RequestBody activities: List<NetworkActivity>): ResponseEntity<Collection<NetworkActivity>> {
        val added = activitiesService.addAll(activities)
        return ResponseEntity(added, HttpStatus.OK)
    }

    @GetMapping
    fun getActivities(@RequestParam ip: String?): List<NetworkActivity>
    {
        ip?.let { return activitiesService.getAssociatedActivities(it) }
        return activitiesService.getAll()
    }
}