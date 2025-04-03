package app.podrida

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active=test"])
class PodridaAppTest {
    @Test
    fun contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }
}
