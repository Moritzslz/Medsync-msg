package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "diagnosis")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    @JoinColumn(name = "issuedBy", referencedColumnName = "id")
    private Long issuedBy;
    private String illness;
    private String description;
    private String severity;
    private String dateDiagnosed;

    public Diagnosis(Long id, Long patientId, Long issuedBy, String illness, String description, String severity, String dateDiagnosed) {
        this.id = id;
        this.patientId = patientId;
        this.issuedBy = issuedBy;
        this.illness = illness;
        this.description = description;
        this.severity = severity;
        this.dateDiagnosed = dateDiagnosed;
    }
}
