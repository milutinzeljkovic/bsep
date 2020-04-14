package de.jonashackt.springbootvuejs.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDetail extends AbstractBaseEntity{

    private String givenName;

    private String surname;

    @Column(unique = true)
    private String email;

    private String country;

    private Date startAt;

    private Date entAt;

    private String organisation;

    private String organisationUnit;

    private String extension;

    private boolean isCA;

    private String issuerEmail;

    public String getCommonName(){
        return this.givenName + " " + this.surname;
    }
}
