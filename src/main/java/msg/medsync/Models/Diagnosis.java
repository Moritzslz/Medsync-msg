package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


/**
 * Diagnosis entity representing a medical diagnosis in the application.
 *
 * @Summary Diagnosis Entity
 * @Description Represents a medical diagnosis associated with a patient in the application.
 * @Entity Diagnosis
 * @Table name="diagnosis"
 */
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

    /**
     * Constructs a new Diagnosis with the specified details.
     *
     * @param patient the patient associated with the diagnosis
     * @param issuedBy the doctor who issued the diagnosis
     * @param illness the illness associated with the diagnosis
     * @param description a description of the diagnosis
     * @param severity the severity of the illness
     * @param dateDiagnosed the date when the diagnosis was made
     */
    public Diagnosis(Patient patient, Doctor issuedBy, String illness, String description, String severity, String dateDiagnosed) {
        this.patient = patient;
        this.issuedBy = issuedBy;
        this.illness = illness;
        this.description = description;
        this.severity = severity;
        this.dateDiagnosed = dateDiagnosed;
        this.reports = new ArrayList<>();
    }
}
