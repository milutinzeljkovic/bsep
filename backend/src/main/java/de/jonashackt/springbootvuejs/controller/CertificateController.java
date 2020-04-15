package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import de.jonashackt.springbootvuejs.service.CertificateDetailService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateDetailService certificateDetailService;

    @PostMapping(path = "")
    public ResponseEntity<CertificateDetail> create(@RequestBody CertificateDetail certificateDetail){
        CertificateDetail createdCertificateDetail = certificateDetailService.createCertificateDetail(certificateDetail);
        if(createdCertificateDetail == null){
            return ResponseEntity.status(400).body(createdCertificateDetail);
        }
        return ResponseEntity.status(201).body(createdCertificateDetail);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CertificateDetail> getAll(){
        return certificateDetailService.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Optional<CertificateDetail> getOneById(@PathVariable UUID id){
        return certificateDetailService.getOneById(id);
    }

    @RequestMapping(path = "/email/{email}", method = RequestMethod.GET)
    public Optional<CertificateDetail> getOneByEmail(@PathVariable String email){
        return certificateDetailService.getOneByEmail(email);
    }




}
