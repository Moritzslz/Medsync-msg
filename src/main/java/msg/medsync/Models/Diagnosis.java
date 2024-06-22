package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

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
    @OneToOne
    @JoinColumn(name = "issuedBy", referencedColumnName = "doctorId")
    private Doctor issuedBy;
    private String illness;
    private String description;
    private Severity severity;
    private Date dateDiagnosed;

    public Diagnosis(Long patientId, Doctor issuedBy, String illness, String description, Severity severity, Date dateDiagnosed) {
        this.patientId = patientId;
        this.issuedBy = issuedBy;
        this.illness = illness;
        this.description = description;
        this.severity = severity;
        this.dateDiagnosed = dateDiagnosed;
    }
}
