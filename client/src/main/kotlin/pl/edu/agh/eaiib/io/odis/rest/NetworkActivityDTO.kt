package pl.edu.agh.eaiib.io.odis.rest

import pl.edu.agh.eaiib.io.odis.domain.NetworkActivity

data class NetworkActivityDTO(val srcAddress: InetAddressDTO,
                              val destAddress: InetAddressDTO,
                              val bytes: Int,
                              val timestamp: Long,
                              val port: Int) {
    companion object {
        fun from(networkActivities: List<NetworkActivity>): List<NetworkActivityDTO> {
            TODO()
        }
    }

    data class InetAddressDTO(private val ip: String,
                              private val name: String)
}