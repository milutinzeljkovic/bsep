package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.certificates.helpers.CertificateCreationHelper;
import de.jonashackt.springbootvuejs.certificates.storage.Reader;
import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.model.CertificateWrapper;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import de.jonashackt.springbootvuejs.service.CertificateDetailService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


public class SelfSignedCertificateCreator implements ICertificateCreator {


    @Override
    public CertificateWrapper createCertificate(CertificateDetail issuerDetail, CertificateDetail subjectDetail) throws OperatorCreationException, CertificateException, ParseException {
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");
        KeyPair keyPair = CertificateCreationHelper.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        X500Name subjectX500Name = CertificateCreationHelper.generateX500Name(subjectDetail);
        Reader reader = new Reader();
        PrivateKey privateKeyIssuer = reader.readPrivateKey("keystore/root.jks","password",issuerDetail.getEmail(),"milutin");
        X509Certificate issuer = (X509Certificate) reader.readX509Certificate("keystore/oot.jks","password",issuerDetail.getEmail());
        X500Name issuerX500Name = new JcaX509CertificateHolder(issuer).getSubject();

        ContentSigner contentSigner = builder.build(privateKeyIssuer);

        long certificateSerialNumber = System.currentTimeMillis();

        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerX500Name,
                new BigInteger(String.valueOf(certificateSerialNumber)),
                subjectDetail.getStartAt(),
                subjectDetail.getEndAt(),
                subjectX500Name,
                publicKey);

        X509CertificateHolder certHolder = certGen.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider("BC");
        X509Certificate certificate = certConverter.getCertificate(certHolder);

        CertificateWrapper certificateWrapper = new CertificateWrapper();
        certificateWrapper.setCertificate(certificate);
        certificateWrapper.setPrivateKey(privateKey);
        return certificateWrapper;
    }
}
