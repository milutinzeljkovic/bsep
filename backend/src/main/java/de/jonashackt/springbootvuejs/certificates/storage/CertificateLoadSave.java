package de.jonashackt.springbootvuejs.certificates.storage;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.X509Certificate;

public class CertificateLoadSave {

    public static void saveCertificate(X509Certificate certificate) throws IOException {
        String fileName = certificate.getSubjectDN().getName().split(",")[0].split("=")[1].trim();
        fileName+=".cer";
        String path = "certificates"+ File.separator + fileName;
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter pw = new JcaPEMWriter(sw)){
            pw.writeObject(certificate);
        }
        FileWriter fw = new FileWriter(path);
        fw.write(sw.toString());
        fw.close();
    }

}
