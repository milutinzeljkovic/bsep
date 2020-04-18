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
import java.util.UUID;

@Service
public class CertificateService implements ICertificateService {

    @Autowired
    CertificateDetailRepository certificateDetailRepository;

    @Override
    public CertificateDetail issue(String email_issuer, String  email_subject) throws CertificateException, OperatorCreationException, ParseException {
        CertificateDetail issuer = certificateDetailRepository.findOneByEmail(email_issuer);
        CertificateDetail subject = certificateDetailRepository.findOneByEmail(email_subject);
        CertificateCreatorContext creator = new CertificateCreatorContext();
        creator.setCertificateCreator(new SelfSignedCertificateCreator());
        creator.createCertificate(issuer,subject);
        return subject;
    }
}
