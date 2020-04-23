package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.certificates.storage.CertificateLoadSave;
import de.jonashackt.springbootvuejs.certificates.storage.Reader;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class OCSPService implements IOCSPService {
    @Override
    public boolean verify(String email) throws NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidKeyException {
        Reader reader = new Reader();
        X509Certificate subject = (X509Certificate) reader.readX509Certificate("keystore/intermediate.jks","password",email);
        System.out.println(subject);
        X509Certificate issuer = CertificateLoadSave.loadCertificate(subject.getIssuerDN().getName());
        try {
            subject.verify(issuer.getPublicKey());
            return true;
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
