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

public class X509CRLUtil {

    private static X509CRLUtil instance = null;
    private X509v2CRLBuilder crlGenerator;
    private CertificateWrapper certificateWrapper;

    private X509CRLUtil() throws CertIOException, CertificateEncodingException {
        Reader reader = new Reader();
        //neki root cert mora postojati koji moze da revokuje sertifikate
        certificateWrapper = reader.readCertificate("keystore/root.jks","milutin123@gmail.com","password".toCharArray(),"milutin".toCharArray());
        X509Certificate certificate = certificateWrapper.getCertificate();
        X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
        crlGenerator = new X509v2CRLBuilder(x500name,new Date());
        crlGenerator.setNextUpdate(new Date());
    }

    public static X509CRLUtil getInstance() throws CertIOException, CertificateEncodingException {
        if(instance == null){
            instance = new X509CRLUtil();
        }
        return instance;
    }

    public X509CRL revokeCert(X509Certificate certificate) throws OperatorCreationException, CRLException, IOException {
        //povlacenje sertifikata
        crlGenerator.addCRLEntry(certificate.getSerialNumber(),new Date(), CRLReason.privilegeWithdrawn);
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        ContentSigner contentSigner = builder.build(certificateWrapper.getPrivateKey());
        X509CRLHolder crlHolder = crlGenerator.build(contentSigner);
        JcaX509CRLConverter converter = new JcaX509CRLConverter();
        converter.setProvider("BC");
        CRLLoadSave.saveCRL(converter.getCRL(crlHolder));
        return converter.getCRL(crlHolder);
    }

}
