package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.certificates.creator.CertificateCreatorContext;
import de.jonashackt.springbootvuejs.certificates.creator.SelfSignedCertificateCreator;
import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public List<CertificateDetail> getAll() {
        return certificateDetailRepository.findAll();
    }

    @Override
    public Optional<CertificateDetail> getOneById(UUID id) {
        return certificateDetailRepository.findOneById(id);
    }

    @Override
    public CertificateDetail getOneByEmail(String email) {
        CertificateDetail cert =  certificateDetailRepository.findOneByEmail(email);
        return cert;
    }

    @Override
    public String test(){
        CertificateCreatorContext context = new CertificateCreatorContext(new SelfSignedCertificateCreator());
        try {
            CertificateDetail issuer = certificateDetailRepository.findOneByEmail("milutinzeljkovic@gmail.com");
            System.out.println(issuer);
            context.createCertificate(issuer, issuer);
            return "test";
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
