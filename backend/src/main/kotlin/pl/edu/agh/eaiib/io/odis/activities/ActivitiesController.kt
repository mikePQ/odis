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
    fun getActivities(@RequestParam ip: String?, @RequestParam fromTime: Long?, @RequestParam toTime: Long?): ResponseEntity<List<NetworkActivity>>
    {
        var dataRange : Pair <Long, Long>? = null;
        if (fromTime != null || toTime != null) {
            if (fromTime == null || toTime == null) {
                return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST);
            }
            dataRange = Pair(fromTime, toTime);
        }
        ip?.let { ResponseEntity(activitiesService.getAssociatedActivities(it, dataRange), HttpStatus.OK) }
        return ResponseEntity(activitiesService.getAll(dataRange), HttpStatus.OK)
    }


}