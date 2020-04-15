package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.X509Certificate;

public class SelfSignedCertificateCreator implements ICertificateCreator {

    @Autowired
    CertificateDetailRepository certificateDetailRepository;

    @Override
    public X509Certificate createCertificate(String issurEmailDomain, String subjectEmailDomain) {

        CertificateDetail issuerDetails = certificateDetailRepository.findOneByEmail(issurEmailDomain);

        return null;
    }
}
