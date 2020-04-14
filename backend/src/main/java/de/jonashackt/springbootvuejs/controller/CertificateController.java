package de.jonashackt.springbootvuejs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/certificates")
public class CertificateController {

    @RequestMapping(path = "/hello")
    public String sayHello() {
        return "Cert controller";
    }
}
