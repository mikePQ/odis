package pl.edu.agh.eaiib.io.odis.hosts

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalHostsRepository : MongoRepository<Host, String>

