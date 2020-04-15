package de.jonashackt.springbootvuejs.certificates.creator;

import java.security.cert.X509Certificate;

public class IntermediateCertificateCreator implements ICertificateCreator {
    @Override
    public X509Certificate createCertificate(String issurEmailDomain, String subjectEmailDomain) {
        return null;
    }
}
