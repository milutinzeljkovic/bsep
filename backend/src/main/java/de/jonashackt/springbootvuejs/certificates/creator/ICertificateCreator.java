package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.X509Certificate;

public interface ICertificateCreator {

    public X509Certificate createCertificate(String issurEmailDomain, String subjectEmailDomain);
}
