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
        var dataRange : Pair <Long, Long>? = null
        if (fromTime != null || toTime != null) {
            if (fromTime == null || toTime == null) {
                return ResponseEntity(emptyList(), HttpStatus.BAD_REQUEST)
            }
            dataRange = Pair(fromTime, toTime)
        }
        return ip?.let { ResponseEntity(activitiesService.getAssociatedActivities(it, dataRange), HttpStatus.OK) }
         ?: ResponseEntity(activitiesService.getAll(dataRange), HttpStatus.OK)
    }

    @RequestMapping("/bytes", method = [RequestMethod.GET])
    fun getAmountOfDataProcessed(@RequestParam ip: String?, @RequestParam fromTime: Long?, @RequestParam toTime: Long?, @RequestParam limit : Long): ResponseEntity<Map<Pair<Long, Long>, Long>>
    {
        var dataRange : Pair <Long, Long>? = null
        if (fromTime != null || toTime != null) {
            if (fromTime == null || toTime == null) {
                return ResponseEntity(emptyMap(), HttpStatus.BAD_REQUEST)
            }
            dataRange = Pair(fromTime, toTime)
        }

        val activitiesList : List<NetworkActivity> = ip?.let { activitiesService.getAssociatedActivities(it, dataRange)}
                ?: activitiesService.getAll(dataRange)

        val bytesPerTimestamp : Map<Long, Long> = activitiesList.asSequence().groupBy { it.timestamp }.mapValues { it.value.sumByDouble { it.bytes.toDouble() }.toLong() }

        return ResponseEntity(getByteTransferredPerRange(bytesPerTimestamp, limit, dataRange), HttpStatus.OK)
    }

    private fun getByteTransferredPerRange(activities : Map<Long, Long>, limit: Long, dataRangeFromRequest : Pair <Long, Long>?): Map<Pair<Long, Long>, Long> {
        val numberOfRecords = activities.size
        if (numberOfRecords == 0)
            return mapOf()
        val minTimestamp = activities.minBy { it.key }!!.key
        val maxTimestamp = activities.maxBy { it.key }!!.key
        val timestampRange : Pair<Long, Long>  = dataRangeFromRequest?.let { dataRangeFromRequest } ?: Pair(minTimestamp, maxTimestamp)
        val timestampLength : Long = timestampRange.second - timestampRange.first;
        val onePartLength = timestampLength / limit

        val mapToReturn : MutableMap<Pair<Long, Long>, Long> = mutableMapOf()

        var begin : Long = minTimestamp
        var end : Long = maxTimestamp
        while (begin < end) {
            val partEnd : Long = begin + timestampLength
            mapToReturn.put(Pair(begin, partEnd), activities.asSequence().filter { it.key >= begin && it.key < end }.sumBy { it.value.toInt() }.toLong())
            begin = partEnd
        }
        if ((begin - onePartLength) < end)
            mapToReturn.put(Pair(begin - onePartLength, end), activities.asSequence().filter { it.key >= begin && it.key <= end}.sumBy { it.value.toInt() }.toLong())
        return mapToReturn.toMap()
    }
}