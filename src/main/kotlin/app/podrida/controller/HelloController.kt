package app.podrida.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/hello")
class HelloController {
    @GetMapping
    fun hello(): String {
        return "Hello, World!"
    }
}
