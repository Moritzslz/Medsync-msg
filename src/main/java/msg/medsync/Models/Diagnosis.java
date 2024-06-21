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

    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Patient patient;
    private Doctor issuedBy;
    private String illness;
    private String description;
    private String severity;
    private String dateDiagnosed;
    private ArrayList<Report> reports;

    public Diagnosis(Long id, Patient patient, Doctor issuedBy, String illness, String description, String severity, String dateDiagnosed, ArrayList<Report> reports) {
        this.id = id;
        this.patient = patient;
        this.issuedBy = issuedBy;
        this.illness = illness;
        this.description = description;
        this.severity = severity;
        this.dateDiagnosed = dateDiagnosed;
        this.reports = reports;
    }
}
