package app.podrida.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/error")
class ErrorController {
    @GetMapping
    fun error(): ResponseEntity<String> {
        return ResponseEntity.status(500).body("This is an error endpoint")
    }
}
