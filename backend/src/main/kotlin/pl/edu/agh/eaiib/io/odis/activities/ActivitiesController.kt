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
    fun getActivities(@RequestParam hostIp: String?, @RequestParam fromTime: Long?, @RequestParam toTime: Long?, @RequestParam peersIps: Array<String>?): ResponseEntity<List<NetworkActivity>> {
        var dataRange: Pair<Long, Long>? = null
        if (fromTime != null || toTime != null) {
            if (fromTime == null || toTime == null) {
                return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
            }
            dataRange = Pair(fromTime, toTime)
        }
        var activitiesList: List<NetworkActivity> = hostIp?.let { activitiesService.getAssociatedActivities(it, dataRange)}
                ?: activitiesService.getAll(dataRange)
        if (hostIp == null && peersIps != null)
            return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
        activitiesList = filterByPeersIp(hostIp, peersIps, activitiesList);

        return ResponseEntity(activitiesList, HttpStatus.OK)
    }

    @GetMapping("/bytes")
    fun getAmountOfDataProcessed(@RequestParam hostIp: String?, @RequestParam fromTime: Long?, @RequestParam toTime: Long?,
                                 @RequestParam limit: Long, @RequestParam direction: String?, @RequestParam peersIps: Array<String>?): ResponseEntity<List<BytesPerRange>> {
        var dataRange: Pair<Long, Long>? = null
        if (fromTime != null || toTime != null) {
            if (fromTime == null || toTime == null) {
                return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
            }
            dataRange = Pair(fromTime, toTime)
        }

        var activitiesList: List<NetworkActivity> = hostIp?.let { activitiesService.getAssociatedActivities(it, dataRange) }
                ?: activitiesService.getAll(dataRange)


        if (hostIp == null && (direction != null || peersIps != null))
            return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
        activitiesList = filterByDirection(hostIp, direction, activitiesList);
        activitiesList = filterByPeersIp(hostIp, peersIps, activitiesList);

        val bytesPerTimestamp: Map<Long, Long> = activitiesList.asSequence().groupBy { it.timestamp }.mapValues { it.value.sumByDouble { it.bytes.toDouble() }.toLong() }

        return ResponseEntity(getByteTransferredPerRange(bytesPerTimestamp, limit, dataRange), HttpStatus.OK)
    }

    private fun filterByPeersIp(hostIp: String?, peersIps: Array<String>?, activitiesList: List<NetworkActivity>): List<NetworkActivity> {
        peersIps?.let {
            return activitiesList.filter {
                if (it.srcAddress.host.ip == hostIp) {
                    peersIps.contains(it.destAddress.host.ip)
                } else {
                    peersIps.contains(it.srcAddress.host.ip)
                }
            }
        }
        return activitiesList
    }

    private fun filterByDirection(ip: String?, direction: String?, activitiesList: List<NetworkActivity> ) :List<NetworkActivity> {
        direction?.let {
            if (it == "from") {
                return activitiesList.filter { it.srcAddress.host.ip == ip!! }
            } else if (it == "to") {
                return activitiesList.filter { it.destAddress.host.ip == ip!! }
            }
        }
        return activitiesList;
    }

    private fun getByteTransferredPerRange(activities: Map<Long, Long>, limit: Long, dataRangeFromRequest: Pair<Long, Long>?): List<BytesPerRange> {
        val bytesPerRangeCalculator = BytesPerRangeCalculator(activities, limit, dataRangeFromRequest)
        return bytesPerRangeCalculator.calculate().asSequence().map { BytesPerRange(it.key.first, it.key.second, it.value) }.toList()
    }
}