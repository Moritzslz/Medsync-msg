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

    public ICE(String name, String surname, String relationship, String email, String phone, Address address) {
        this.name = name;
        this.surname = surname;
        this.relationship = relationship;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}