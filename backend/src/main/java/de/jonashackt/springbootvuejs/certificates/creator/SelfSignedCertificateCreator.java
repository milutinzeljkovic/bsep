package de.jonashackt.springbootvuejs.certificates.creator;

import de.jonashackt.springbootvuejs.certificates.helpers.CertificateCreationHelper;
import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import de.jonashackt.springbootvuejs.service.CertificateDetailService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
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
    public X509Certificate createCertificate(CertificateDetail issuerDetail, CertificateDetail subjectDetail) throws OperatorCreationException, CertificateException, ParseException {
        //podaci izdavaca setifikata su u ovom slucaju i podaci subjekta jer je self signed sertifikat
        //builder za potpis
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");
        KeyPair keyPair = CertificateCreationHelper.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        X500Name subjectX500Name = CertificateCreationHelper.generateX500Name(issuerDetail);
        ContentSigner contentSigner = builder.build(keyPair.getPrivate());
        long certificateSerialNumber = System.currentTimeMillis();

        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(issuerDetail.getEndAt());

        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(subjectX500Name,
                new BigInteger(String.valueOf(certificateSerialNumber)),
                issuerDetail.getStartAt(),
                issuerDetail.getEndAt(),
                subjectX500Name,
                publicKey);

        X509CertificateHolder certHolder = certGen.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider("BC");
        X509Certificate certificate = certConverter.getCertificate(certHolder);

        System.out.println("==============================================================================");
        System.out.println(certificate);

        return certificate;
    }
}
