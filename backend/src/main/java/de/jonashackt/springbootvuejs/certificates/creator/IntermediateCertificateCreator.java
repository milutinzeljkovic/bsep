package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import org.bouncycastle.operator.OperatorCreationException;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class IntermediateCertificateCreator implements ICertificateCreator {
    @Override
    public X509Certificate createCertificate(CertificateDetail issuerDetail, CertificateDetail subjectDetail) throws OperatorCreationException, CertificateException {
        return null;
    }
}
