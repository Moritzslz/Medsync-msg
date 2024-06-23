package msg.medsync.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "doctor")
@JsonIgnoreProperties({"patients", "vaccinations", "drugs", "diagnoses"})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String name;
    private String surname;
    private String speciality;
    private String email;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    @ManyToMany()
    @JoinTable(name = "patient_doctor",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;

    @OneToMany(mappedBy = "prescribingDoctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Drug> drugs;

    @OneToMany(mappedBy = "administeringDoctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccination> vaccinations;

    @OneToMany(mappedBy = "issuedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagnosis> diagnoses;
}
