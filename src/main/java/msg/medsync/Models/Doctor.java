package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Doctor entity representing a doctor in the application.
 *
 * @Summary Doctor Entity
 * @Description Represents a doctor in the application, extending the Person class.
 * @Entity Doctor
 * @Table name="doctors"
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "doctors")
public class Doctor extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String specialty;
    private String email;
    private String phone;
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Address address;

    /**
     * Constructs a new Doctor with the specified details.
     *
     * @param name the first name of the doctor
     * @param surname the surname of the doctor
     * @param specialty the medical specialty of the doctor
     * @param email the email address of the doctor
     * @param phone the phone number of the doctor
     * @param address the address of the doctor
     */
    public Doctor(String name, String surname, String specialty, String email, String phone, Address address) {
        this.name = name;
        this.surname = surname;
        this.specialty = specialty;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
