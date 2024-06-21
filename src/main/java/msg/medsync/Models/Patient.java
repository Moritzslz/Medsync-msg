package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String KVR;
    private String healthInsuranceProvider;
    private String name;
    private String surname;
    private Date birthday;
    private double weight;
    private double height;
    private String email;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ice_id", referencedColumnName = "id")
    private ICE ice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familydoctor_id", referencedColumnName = "id")
    private Doctor familyDoctor;

    public Patient(Long patientId, String KVR, String healthInsuranceProvider, String name, String surname, Date birthday, double weight, double height, String email, String phone, String street, String houseNumber, String postalCode, String city, ICE ice, Doctor familyDoctor) {
        this.patientId = patientId;
        this.KVR = KVR;
        this.healthInsuranceProvider = healthInsuranceProvider;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.ice = ice;
        this.familyDoctor = familyDoctor;
    }
}
