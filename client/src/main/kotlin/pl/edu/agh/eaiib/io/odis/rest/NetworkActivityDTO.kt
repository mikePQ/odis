package pl.edu.agh.eaiib.io.odis.rest

import pl.edu.agh.eaiib.io.odis.domain.NetworkActivity
import pl.edu.agh.eaiib.io.odis.domain.packet.TCPPacket

data class NetworkActivityDTO(val srcAddress: InetAddressDTO,
                              val destAddress: InetAddressDTO,
                              val bytes: Int,
                              val timestamp: Long) {
    companion object {
        fun from(networkActivities: List<NetworkActivity>): List<NetworkActivityDTO> {
            val packets = networkActivities.filter { it.packet is TCPPacket }
                    .groupBy { KeySelector.from(it) }

            return packets.map { reduceActivities(it.key, it.value) }
        }

        private fun reduceActivities(key: KeySelector, networkActivities: List<NetworkActivity>): NetworkActivityDTO {
            val bytes = networkActivities.map { it.packet.getData().size + it.packet.getHeader().size }
                    .reduce { v1, v2 -> v1 + v2 }
            return NetworkActivityDTO(key.srcAddress, key.destAddress, bytes, key.timestamp)
        }

        data class KeySelector(val timestamp: Long,
                               val srcAddress: InetAddressDTO,
                               val destAddress: InetAddressDTO) {
            companion object {
                fun from(networkActivity: NetworkActivity): KeySelector {
                    val packet = networkActivity.packet as TCPPacket
                    val timestamp = packet.getTimestamp()
                    val srcAddress = InetAddressDTO(packet.getSourceAddress().hostAddress,
                            packet.getSourceAddress().canonicalHostName, packet.getSourcePort())
                    val destAddress = InetAddressDTO(packet.getDestinationAddress().hostAddress,
                            packet.getDestinationAddress().canonicalHostName, packet.getDestinationPort())

                    return KeySelector(timestamp, srcAddress, destAddress)
                }
            }
        }
    }

    data class InetAddressDTO(private val ip: String,
                              private val name: String,
                              private val port: Int)
}