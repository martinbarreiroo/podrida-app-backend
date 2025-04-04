package app.podrida.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/hello")
class HelloController {
    @GetMapping
    fun hello(): String {
        return "Hello, just checking if CD works, again!"
    }
}
