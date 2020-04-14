package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import de.jonashackt.springbootvuejs.service.CertificateDetailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateDetailService certificateDetailService;

    @RequestMapping(path = "/hello")
    public String sayHello() {
        return "Cert controller";
    }

    @PostMapping(path = "")
    public ResponseEntity<CertificateDetail> create(@RequestBody CertificateDetail certificateDetail){
        CertificateDetail createdCertificateDetail = certificateDetailService.createCertificateDetail(certificateDetail);
        if(createdCertificateDetail == null){
            return ResponseEntity.status(400).body(createdCertificateDetail);
        }
        return ResponseEntity.status(201).body(createdCertificateDetail);
    }

}
