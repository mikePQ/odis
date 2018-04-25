package pl.edu.agh.eaiib.io.odis.activities

import org.springframework.stereotype.Service

@Service
class ActivitiesService(private val activitiesRepository: ActivitiesRepository) {

    fun addAll(activities: Collection<NetworkActivity>): Collection<NetworkActivity> =
            activitiesRepository.saveAll(activities)

    fun getAll(): List<NetworkActivity> = activitiesRepository.findAll()
    fun getAssociatedActivities(ip: String): List<NetworkActivity> {
        return activitiesRepository.findActivitiesBySrcAddressHostIpOrDestAddressHostIp(ip, ip)
    }
}