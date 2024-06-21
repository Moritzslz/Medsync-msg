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
@Table(name = "allergies")
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    private String allergen;
    private String reaction;
    private String severity;
    private Date dateDiagnosed;
    private String notes;

    public Allergy(Long id, Long patientId, String allergen, String reaction, String severity, Date dateDiagnosed, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.allergen = allergen;
        this.reaction = reaction;
        this.severity = severity;
        this.dateDiagnosed = dateDiagnosed;
        this.notes = notes;
    }
}
