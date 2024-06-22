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
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctorid")
    private Long doctorId;
    private String name;
    private String surname;
    private String speciality;
    private String email;
    private String phone;
    private String street;
    @Column(name = "housenumber")
    private String houseNumber;
    @Column(name = "postalcode")
    private String postalCode;
    private String city;

    public Doctor(String name, String surname, String speciality, String email, String phone, String street, String houseNumber, String postalCode, String city) {
        this.name = name;
        this.surname = surname;
        this.speciality = speciality;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
