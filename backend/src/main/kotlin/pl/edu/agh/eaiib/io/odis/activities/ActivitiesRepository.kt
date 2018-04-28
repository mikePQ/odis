package pl.edu.agh.eaiib.io.odis.activities

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ActivitiesRepository : MongoRepository<NetworkActivity, String>
{
    fun findActivitiesBySrcAddressHostIpOrDestAddressHostIp(ip1 : String, ip2 : String) : List<NetworkActivity>
    fun findByTimestampBetween(begin: Long, end: Long): List<NetworkActivity>
    @Query("{'srcAddress.host.ip' : '?0', timestamp : {\$gt : ?1, \$lt : ?2}}")
    fun findActivitesInTimestampRangeWithIp(ip: String, begin: Long, end: Long): List<NetworkActivity>
}