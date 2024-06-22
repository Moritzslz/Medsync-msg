package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientid")
    private Long patientId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICE", referencedColumnName = "iceid")
    private ICE ICE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familydoctor", referencedColumnName = "doctorid")
    private Doctor familyDoctor;
    @Column(name ="kvr")
    private String KVR;
    @Column(name ="healthinsuranceprovider")
    private String healthInsuranceProvider;
    private String name;
    private String surname;
    private Date birthday;
    @Column(name ="weightkg")
    private int weightKg;
    @Column(name ="heightcm")
    private int heightCm;
    private String email;
    private String phone;
    private String street;
    @Column(name ="housenumber")
    private String houseNumber;
    @Column(name ="postalcode")
    private String postalCode;
    private String city;

    @OneToMany(mappedBy = "patient")
    private List<PatientDoctor> patientDoctors;

    public Patient(msg.medsync.Models.ICE ICE, Doctor familyDoctor, String KVR, String healthInsuranceProvider, String name, String surname, Date birthday, int weightKg, int heightCm, String email, String phone, String street, String houseNumber, String postalCode, String city) {
        this.ICE = ICE;
        this.familyDoctor = familyDoctor;
        this.KVR = KVR;
        this.healthInsuranceProvider = healthInsuranceProvider;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
