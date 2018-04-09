package pl.edu.agh.eaiib.odis.odis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OdisApplication

fun main(args: Array<String>) {
    runApplication<OdisApplication>(*args)
}
