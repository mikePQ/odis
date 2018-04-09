package pl.edu.agh.eaiib.odis.odis.activities

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NetworkActivitiesRepository : CrudRepository<NetworkActivity, Int>