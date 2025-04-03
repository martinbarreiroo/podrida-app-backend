package app.podrida

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PodridaApp {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<PodridaApp>(*args)
        }
    }
}