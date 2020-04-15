package de.jonashackt.springbootvuejs.certificates.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.cert.X509Certificate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertificateCreatorContext {

    private ICertificateCreator certificateCreator;

    public X509Certificate createCertificate(String issuerEmailDomain, String subjectEmailDomain){
        return this.certificateCreator.createCertificate(issuerEmailDomain,subjectEmailDomain);
    }

}
