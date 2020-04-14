package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class CertificateDetailService implements  ICertificateDetailService {

    @Autowired
    CertificateDetailRepository certificateDetailRepository;

    @Override
    public CertificateDetail createCertificateDetail(CertificateDetail certificateDetail) {
        CertificateDetail created = null;
        try{
            created = certificateDetailRepository.save(certificateDetail);
        }catch (Exception ex){
            System.out.println(ex);
        }
        return created;
    }
}
