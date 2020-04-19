package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.service.CertificateDetailService;
import de.jonashackt.springbootvuejs.service.CertificateService;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    CertificateDetailService certificateDetailService;

    @Autowired
    CertificateService certificateService;

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
    public CertificateDetail getOneById(@PathVariable UUID id){
        return certificateDetailService.getOneById(id);
    }

    @RequestMapping(path = "/email/{email}", method = RequestMethod.GET)
    public CertificateDetail getOneByEmail(@PathVariable String email){
        return certificateDetailService.getOneByEmail(email);
    }

    @RequestMapping(path = "/issue/{email_issuer}/{email_subject}", method = RequestMethod.POST)
    public CertificateDetail issue(@PathVariable String email_issuer, @PathVariable String email_subject) throws CertificateException, OperatorCreationException, ParseException, IOException {
        return certificateService.issue(email_issuer,email_subject);
    }

    @RequestMapping(path = "/issue/intermediate/{email_issuer}/{email_subject}", method = RequestMethod.POST)
    public CertificateDetail issueIntermediate(@PathVariable String email_issuer, @PathVariable String email_subject) throws CertificateException, OperatorCreationException, ParseException, IOException {
        return certificateService.issueIntermediate(email_issuer,email_subject);
    }

    @RequestMapping(path = "/issue/endentity/{email_issuer}/{email_subject}", method = RequestMethod.POST)
    public CertificateDetail issueEE(@PathVariable String email_issuer, @PathVariable String email_subject) throws CertificateException, OperatorCreationException, ParseException, IOException {
        return certificateService.issueEE(email_issuer,email_subject);
    }

}
