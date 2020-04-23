package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.certificates.storage.CertificateLoadSave;
import de.jonashackt.springbootvuejs.certificates.storage.Reader;
import de.jonashackt.springbootvuejs.model.CertificateWrapper;
import org.bouncycastle.asn1.x509.X509Name;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

@Service
public class OCSPService implements IOCSPService {
    @Override
    public boolean verify(String email) throws NoSuchProviderException, CertificateException, NoSuchAlgorithmException, InvalidKeyException {
        Reader reader = new Reader();
        X509Certificate subject = ( reader.readCertificate("keystore/intermediate.jks",email,"password".toCharArray(),"milutin".toCharArray())).getCertificate();
        X509Certificate issuer = ( reader.readCertificate("keystore/root.jks","milutin123@gmail.com","password".toCharArray(),"milutin".toCharArray())).getCertificate();
        try {
            subject.verify(issuer.getPublicKey());
            return true;
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
