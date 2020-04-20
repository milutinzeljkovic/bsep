package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import org.bouncycastle.operator.OperatorCreationException;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.text.ParseException;

public interface ICertificateService {

    CertificateDetail issue(String emailIssuer, String emailSubject) throws CertificateException, OperatorCreationException, ParseException, IOException;
    CertificateDetail issueIntermediate(String emailIssuer, String emailSubject) throws CertificateException, OperatorCreationException, ParseException, IOException;
    CertificateDetail issueEE(String emailIssuer, String emailSubject) throws CertificateException, OperatorCreationException, ParseException, IOException;
    String test();
}
