package pl.edu.agh.eaiib.io.odis.rest

import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkActivitiesApi {

    @POST("activities")
    fun addActivities(@Body activities: List<NetworkActivityDTO>): Flowable<Any>

    companion object {
        fun create(serverBaseUrl: String): NetworkActivitiesApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(serverBaseUrl)
                    .build()

            return retrofit.create(NetworkActivitiesApi::class.java)
        }
    }
}