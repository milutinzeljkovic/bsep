package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.certificates.creator.CertificateCreatorContext;
import de.jonashackt.springbootvuejs.certificates.creator.SelfSignedCertificateCreator;
import de.jonashackt.springbootvuejs.certificates.storage.Reader;
import de.jonashackt.springbootvuejs.certificates.storage.Writer;
import de.jonashackt.springbootvuejs.model.CertificateDetail;
import de.jonashackt.springbootvuejs.model.CertificateWrapper;
import de.jonashackt.springbootvuejs.repository.CertificateDetailRepository;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.UUID;

@Service
public class CertificateService implements ICertificateService {

    @Autowired
    CertificateDetailRepository certificateDetailRepository;

    @Override
    public CertificateDetail issue(String email_issuer, String  email_subject) throws CertificateException, OperatorCreationException, ParseException {
        CertificateDetail issuer = certificateDetailRepository.findOneByEmail(email_issuer);
        CertificateDetail subject = certificateDetailRepository.findOneByEmail(email_subject);
        CertificateCreatorContext creator = new CertificateCreatorContext();
        creator.setCertificateCreator(new SelfSignedCertificateCreator());
        CertificateWrapper certificateWrapper = creator.createCertificate(issuer,subject);
        Writer writer = new Writer();
        writer.write(email_subject,certificateWrapper.getPrivateKey(),"password",certificateWrapper.getCertificate(),"root.jks", "milutin");


        Reader reader  = new Reader();
//        System.out.println(reader.readAliases("root.jks","password"));
//
//        Certificate cw = reader.readX509Certificate("root.jks","password","mile@gmail.com");
//        System.out.println("======================procirtani=========================");
//        System.out.println(cw);
//
//        System.out.println("======================svi=========================");
//        System.out.println(reader.readAllCertificate("root.jks","password"));

        System.out.println(reader.readPrivateKey("root.jks","password","milutin123@gmail.com","milutin"));
        return subject;
    }
}
