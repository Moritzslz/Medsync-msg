package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "vaccinations")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;

    private String vaccineName;
    private Date vaccinationDate;
    private Date notificationDate;
    private String dose;
    private String administeringDoctor;
    private String sideEffects;

    public Vaccination(Long id, Long patientIf, String vaccineName, Date vaccinationDate, String dose, String administeringDoctor, String sideEffects) {
        this.id = id;
        this.patientId = patientId;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.dose = dose;
        this.administeringDoctor = administeringDoctor;
        this.sideEffects = sideEffects;
    }
}
