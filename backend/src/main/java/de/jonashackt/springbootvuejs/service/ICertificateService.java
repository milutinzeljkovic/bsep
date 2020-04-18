package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import org.bouncycastle.operator.OperatorCreationException;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.UUID;

public interface ICertificateService {

    CertificateDetail issue(String emailIssuer, String emailSubject) throws CertificateException, OperatorCreationException, ParseException;
    CertificateDetail issueIntermediate(String emailIssuer, String emailSubject) throws CertificateException, OperatorCreationException, ParseException;

}
