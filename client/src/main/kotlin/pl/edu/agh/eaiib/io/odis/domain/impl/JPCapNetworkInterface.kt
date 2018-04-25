package pl.edu.agh.eaiib.io.odis.domain.impl

import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import java.net.InetAddress

class JPCapNetworkInterface(val jpcapInterface: jpcap.NetworkInterface) : NetworkInterface {

    override fun getId(): String = jpcapInterface.name

    override fun getAddresses(): List<InetAddress> {
        return jpcapInterface.addresses.map { it.address }
    }

}