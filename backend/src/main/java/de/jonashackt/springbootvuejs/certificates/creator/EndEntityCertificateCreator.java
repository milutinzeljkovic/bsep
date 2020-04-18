package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.model.CertificateWrapper;
import org.bouncycastle.operator.OperatorCreationException;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class EndEntityCertificateCreator implements  ICertificateCreator {

    @Override
    public CertificateWrapper createCertificate(CertificateDetail issuerDetail, CertificateDetail subjectDetail) throws OperatorCreationException, CertificateException {
        return null;
    }
}
