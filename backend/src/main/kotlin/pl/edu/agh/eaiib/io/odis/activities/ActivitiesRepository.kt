package pl.edu.agh.eaiib.io.odis.activities

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivitiesRepository : MongoRepository<NetworkActivity, String>