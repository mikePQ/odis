package pl.edu.agh.eaiib.io.odis.rest

import io.reactivex.Flowable
import pl.edu.agh.eaiib.io.odis.domain.NetworkActivity
import pl.edu.agh.eaiib.io.odis.domain.NetworkInterface
import pl.edu.agh.eaiib.io.odis.domain.packet.Packet
import pl.edu.agh.eaiib.io.odis.monitoring.MonitorListener
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class NetworkActivityPublisher(serverBaseUrl: String,
                               private val publishPeriod: Int) : MonitorListener {

    private val api: NetworkActivitiesApi = NetworkActivitiesApi.create(serverBaseUrl)
    private val publishQueue = ConcurrentLinkedQueue<NetworkActivity>()
    private val elementsToPublish = ConcurrentLinkedQueue<NetworkActivity>()
    private var lastPublishMillis = System.currentTimeMillis()

    private val executor: Executor = Executors.newSingleThreadExecutor { runnable ->
        val thread = Thread(runnable)
        thread.isDaemon = true
        thread
    }

    init {
        executor.execute({
            while (true) {
                tryPublishData()
            }
        })
    }

    override fun packetReceived(packet: Packet, networkInterface: NetworkInterface) {
        val activity = NetworkActivity.create(packet)
        publishQueue.add(activity)
    }

    private fun tryPublishData() {
        try {
            if (publishQueue.isEmpty()) {
                return
            }

            val data = publishQueue.poll()
            elementsToPublish.add(data)

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastPublishMillis < publishPeriod) {
                return
            }

            val elements = elementsToPublish.toList()
            val result = publish(elements)
            result.subscribe()

            lastPublishMillis = System.currentTimeMillis()
            elementsToPublish.clear()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun publish(networkActivities: List<NetworkActivity>): Flowable<Any> {
        val activities = NetworkActivityDTO.from(networkActivities)
        return publishImpl(activities)
    }

    protected open fun publishImpl(activities: List<NetworkActivityDTO>): Flowable<Any> {
        return api.addActivities(activities)
    }
}