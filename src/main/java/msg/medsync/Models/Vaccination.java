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
@Table(name = "vaccination")
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    @OneToOne
    @JoinColumn(name = "administeringDoctor", referencedColumnName = "doctorId")
    private Doctor administeringDoctor;
    private String vaccineName;
    private Date vaccinationDate;
    private Date notificationDate;
    private String dose;

    public Vaccination(Long patientId, Doctor administeringDoctor, String vaccineName, Date vaccinationDate, Date notificationDate, String dose) {
        this.patientId = patientId;
        this.administeringDoctor = administeringDoctor;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.notificationDate = notificationDate;
        this.dose = dose;
    }
}
