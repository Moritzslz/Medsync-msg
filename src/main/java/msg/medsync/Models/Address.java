package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Address entity representing an address in the application.
 *
 * @Summary Address Entity
 * @Description Represents an address associated with a person in the application.
 * @Entity Address
 * @Table name="address"
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Person person;

    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    /**
     * Constructs a new Address with the specified details.
     *
     * @param person the person associated with the address
     * @param street the street of the address
     * @param houseNumber the house number of the address
     * @param postalCode the postal code of the address
     * @param city the city of the address
     */
    public Address(Person person, String street, String houseNumber, String postalCode, String city) {
        this.person = person;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
