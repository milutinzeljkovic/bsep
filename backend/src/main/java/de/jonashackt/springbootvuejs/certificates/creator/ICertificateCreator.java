package de.jonashackt.springbootvuejs.certificates.creator;

import java.security.cert.X509Certificate;

public interface ICertificateCreator {
    public X509Certificate createCertificate(String issurEmailDomain, String subjectEmailDomain);
}
