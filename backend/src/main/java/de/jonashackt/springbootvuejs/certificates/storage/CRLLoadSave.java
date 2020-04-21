package de.jonashackt.springbootvuejs.certificates.storage;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class CRLLoadSave {

    public static void saveCRL(X509CRL crl) throws IOException {
        String path = "crl"+ File.separator + "crllist.crl";
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter pw = new JcaPEMWriter(sw)){
            pw.writeObject(crl);
        }
        FileWriter fw = new FileWriter(path);
        fw.write(sw.toString());
        fw.close();
    }
}
