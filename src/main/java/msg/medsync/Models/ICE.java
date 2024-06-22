package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ICE")
public class ICE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iceId;

    @OneToOne(mappedBy = "ICE")
    private Patient patient;

    private String name;
    private String surname;
    private String relationship;
    private String email;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
}
