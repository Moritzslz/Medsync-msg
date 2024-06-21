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
@Table(name = "drugs")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    private String name;
    private String dosage;
    private String frequency;
    private Date startDate;
    private Date endDate;
    @OneToOne
    @JoinColumn(name = "prescribingDoctor", referencedColumnName = "id")
    private Doctor prescribingDoctor;
    private String sideEffects;

    public Drug(Long id, Long patientId, String name, String dosage, String frequency, Date startDate, Date endDate, Doctor prescribingDoctor, String sideEffects) {
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prescribingDoctor = prescribingDoctor;
        this.sideEffects = sideEffects;
    }
}
