package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;

public interface ICertificateCreator {

    public X509Certificate createCertificate(CertificateDetail issuerDetail, CertificateDetail subjectDetail) throws OperatorCreationException, CertificateException, ParseException;
}
