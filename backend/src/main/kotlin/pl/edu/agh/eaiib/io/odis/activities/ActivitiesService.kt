package pl.edu.agh.eaiib.io.odis.activities

import org.springframework.stereotype.Service

@Service
class ActivitiesService(private val activitiesRepository: ActivitiesRepository) {

    fun addAll(activities: Collection<NetworkActivity>): Collection<NetworkActivity> =
            activitiesRepository.saveAll(activities)

    fun getAll(dataRange : Pair<Long, Long>?): List<NetworkActivity>  {
        dataRange?.let { return activitiesRepository.findByTimestampBetween(dataRange.first, dataRange.second)}
        return activitiesRepository.findAll()
    }
    fun getAssociatedActivities(ip: String, dataRange: Pair<Long, Long>?): List<NetworkActivity> {
        dataRange?.let { return activitiesRepository.findActivitiesByTimestampBetweenAndSrcAddressHostIpOrDestAddressHostIp(dataRange.first, dataRange.second, ip, ip) }
        return activitiesRepository.findActivitiesBySrcAddressHostIpOrDestAddressHostIp(ip, ip)
    }
}