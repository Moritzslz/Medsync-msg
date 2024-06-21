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
@Table(name = "patients")
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String KVR;
    private String healthInsuranceProvider;
    private String name;
    private String surname;
    private String birthday;
    private String weight;
    private String height;
    private String email;
    private String phone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Address address;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private ICE ice;
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Doctor familyDoctor;

    public Patient(String KVR, String healthInsuranceProvider, String name, String surname, String birthday, String weight, String height, String email, String phone, Address address, ICE ice, Doctor familyDoctor) {
        this.KVR = KVR;
        this.healthInsuranceProvider = healthInsuranceProvider;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.ice = ice;
        this.familyDoctor = familyDoctor;
    }
}
