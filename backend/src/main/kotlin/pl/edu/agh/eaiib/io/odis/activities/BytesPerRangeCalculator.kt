package pl.edu.agh.eaiib.io.odis.activities

import kotlin.math.ceil

class BytesPerRangeCalculator(private val activities: Map<Long, Long>,
                              private val limit: Long,
                              private val dataRangeFromRequest: Pair<Long, Long>?) {

    fun calculate(): Map<Pair<Long, Long>, Long> {
        val numberOfRecords = activities.size
        if (numberOfRecords == 0)
            return mapOf()
        val minTimestamp = activities.minBy { it.key }!!.key
        val maxTimestamp = activities.maxBy { it.key }!!.key
        val timestampRange: Pair<Long, Long> = dataRangeFromRequest?.let { dataRangeFromRequest }
                ?: Pair(minTimestamp, maxTimestamp)
        val timestampLength: Long = timestampRange.second - timestampRange.first;
        val onePartLengthAccurate : Double = timestampLength.toDouble().div(limit)
        val onePartLength = ceil(onePartLengthAccurate).toLong()

        val mapToReturn: MutableMap<Pair<Long, Long>, Long> = mutableMapOf()

        var begin: Long = timestampRange.first
        val end: Long = timestampRange.second
        while (begin < end) {
            val partEnd: Long = begin + onePartLength;
            if (partEnd >= end) {
                mapToReturn[Pair(begin, end)] = activities.asSequence().filter { it.key in begin..end }.sumBy { it.value.toInt() }.toLong()
                break
            } else {
                mapToReturn[Pair(begin, partEnd)] = activities.asSequence().filter { it.key in begin..(partEnd - 1) }.sumBy { it.value.toInt() }.toLong()
            }
            begin = partEnd
        }
        return mapToReturn.toMap()
    }
}
