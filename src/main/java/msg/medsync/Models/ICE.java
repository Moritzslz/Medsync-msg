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
@Table(name = "ice")
public class ICE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String relationship;
    private String email;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    @JoinColumn(name = "patientid", referencedColumnName = "patientid")
    private Long patientId;

    public ICE(Long id, String name, String surname, String relationship, String email, String phone, String street, String houseNumber, String postalCode, String city, Long patientId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.patientId = patientId;
    }
}