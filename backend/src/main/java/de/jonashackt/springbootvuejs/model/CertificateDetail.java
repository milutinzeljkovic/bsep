package de.jonashackt.springbootvuejs.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

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

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date entAt;

    private String organisation;

    private String organisationUnit;

    private String extension;

    private boolean isCA;

    private String issuerEmail;

    public String getCommonName(){
        return this.givenName + " " + this.surname;
    }

    private boolean isActive;
}
