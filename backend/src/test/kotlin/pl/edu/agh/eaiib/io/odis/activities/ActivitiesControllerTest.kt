package pl.edu.agh.eaiib.io.odis.activities

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(Arquillian::class)
class ActivitiesControllerTest {

    @Test
    fun getAmountOfDataProcessed() {
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(ActivitiesController::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
