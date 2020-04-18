package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.model.CertificateDetail;
import org.bouncycastle.operator.OperatorCreationException;

import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.UUID;

public interface ICertificateService {

    CertificateDetail issue(String email_issuer, String email_subject) throws CertificateException, OperatorCreationException, ParseException;

}
