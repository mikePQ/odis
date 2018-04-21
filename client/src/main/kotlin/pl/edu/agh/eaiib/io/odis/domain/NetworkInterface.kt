package pl.edu.agh.eaiib.io.odis.domain

interface NetworkInterface {
    fun getId(): InterfaceId

    interface InterfaceId {
        fun asString(): String
    }
}