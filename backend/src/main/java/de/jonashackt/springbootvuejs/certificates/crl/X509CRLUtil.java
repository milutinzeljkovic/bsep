package de.jonashackt.springbootvuejs.certificates.crl;

import de.jonashackt.springbootvuejs.certificates.storage.CRLLoadSave;
import de.jonashackt.springbootvuejs.certificates.storage.Reader;
import de.jonashackt.springbootvuejs.model.CertificateWrapper;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.Extension;

import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;

import javax.security.auth.x500.X500Principal;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.*;
import java.util.Date;

//treba napraviti metod za postavljanje issuera da nije ovaj defaultni
public class X509CRLUtil {

    private static X509CRLUtil instance = null;
    private X509v2CRLBuilder x509v2CRLBuilder;
    private CertificateWrapper certificateWrapper;
    private X509CRL previousX509CRL = null;

    private X509CRLUtil() throws CertIOException, CertificateEncodingException {
        Reader reader = new Reader();
        //citanje issuera
        certificateWrapper = reader.readCertificate("keystore/root.jks","milutin123@gmail.com","password".toCharArray(),"milutin".toCharArray());
        X509Certificate certificate = certificateWrapper.getCertificate();
        X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
        x509v2CRLBuilder = new X509v2CRLBuilder(x500name,new Date());
        x509v2CRLBuilder.setNextUpdate(new Date());

    }

    public static X509CRLUtil getInstance() throws CertIOException, CertificateEncodingException {
        if(instance == null){
            instance = new X509CRLUtil();
        }
        return instance;
    }

    public X509CRL revokeCert(X509Certificate certificate) throws OperatorCreationException, CRLException, IOException, CertificateException {
        //povlacenje sertifikata
        x509v2CRLBuilder.addCRLEntry(certificate.getSerialNumber(),new Date(), CRLReason.privilegeWithdrawn);
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        ContentSigner contentSigner = builder.build(certificateWrapper.getPrivateKey());
        //potpis privatnim kljucem ca
        X509CRLHolder crlHolder = x509v2CRLBuilder.build(contentSigner);
        JcaX509CRLConverter converter = new JcaX509CRLConverter();
        converter.setProvider("BC");
        X509CRL x509CRL = converter.getCRL(crlHolder);
        CRLLoadSave.saveCRL(x509CRL);
        return x509CRL;
    }

}
