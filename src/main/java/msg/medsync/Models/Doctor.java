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

    public Doctor(String name, String surname, String specialty, String email, String phone, Address address) {
        this.name = name;
        this.surname = surname;
        this.specialty = specialty;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
