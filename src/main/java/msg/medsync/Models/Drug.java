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
@Table(name = "drug")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    @OneToOne
    @JoinColumn(name = "prescribingDoctor", referencedColumnName = "doctorId")
    private Doctor prescribingDoctor;
    private String name;
    private String dosage;
    private String frequency;
    private Date startDate;
    private Date endDate;
    private String sideEffects;

    public Drug(Long patientId, Doctor prescribingDoctor, String name, String dosage, String frequency, Date startDate, Date endDate, String sideEffects) {
        this.patientId = patientId;
        this.prescribingDoctor = prescribingDoctor;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sideEffects = sideEffects;
    }
}
