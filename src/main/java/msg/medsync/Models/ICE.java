package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "ICE")
public class ICE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iceId;
    @JoinColumn(name = "patientid", referencedColumnName = "patientid")
    private Long patientId;
    private String name;
    private String surname;
    private String relationship;
    private String email;
    private String phone;
    private String street;
    @Column(name ="housenumber")
    private String houseNumber;
    @Column(name ="postalcode")
    private String postalCode;
    private String city;

    public ICE(Long patientId, String name, String surname, String relationship, String email, String phone, String street, String houseNumber, String postalCode, String city) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}