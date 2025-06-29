package seg.work.carog.server.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noauth")
public class NoAuthController {

    @GetMapping
    public ResponseEntity<?> noAuth() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
