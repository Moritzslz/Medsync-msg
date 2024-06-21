package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Patient entity representing a patient in the application.
 *
 * @Summary Patient Entity
 * @Description Represents a patient in the application, extending the Person class.
 * @Entity Patient
 * @Table name="patients"
 */
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

    // Krankenversicherungsnummer of the patient
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


    /**
     * Constructs a new Patient with the specified details.
     *
     * @param KVR the KVR (Krankenversicherungsnummer) of the patient
     * @param healthInsuranceProvider the health insurance provider of the patient
     * @param name the first name of the patient
     * @param surname the surname of the patient
     * @param birthday the birthday of the patient
     * @param weight the weight of the patient
     * @param height the height of the patient
     * @param email the email address of the patient
     * @param phone the phone number of the patient
     * @param address the address of the patient
     * @param ice the emergency contact (ICE) of the patient
     * @param familyDoctor the family doctor of the patient
     */
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
