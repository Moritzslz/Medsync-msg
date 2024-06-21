package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * ICE entity representing an emergency contact in the application.
 *
 * @Summary ICE Entity
 * @Description Represents an emergency contact (In Case of Emergency) associated with a patient in the application.
 * @Entity ICE
 * @Table name="ice"
 */
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Patient patient;

    private String name;
    private String surname;
    private String relationship;
    private String email;
    private String phone;
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Address address;

    /**
     * Constructs a new ICE entity with the specified details.
     *
     * @param name the first name of the emergency contact
     * @param surname the surname of the emergency contact
     * @param relationship the relationship of the emergency contact to the patient
     * @param email the email address of the emergency contact
     * @param phone the phone number of the emergency contact
     * @param address the address of the emergency contact
     */
    public ICE(String name, String surname, String relationship, String email, String phone, Address address) {
        this.name = name;
        this.surname = surname;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}