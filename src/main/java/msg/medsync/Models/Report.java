package msg.medsync.Models;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Report entity representing a medical report in the application.
 *
 * @Summary Report Entity
 * @Description Represents a medical report associated with a patient in the application.
 * @Entity Report
 * @Table name="reports"
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "patientId", referencedColumnName = "id")
    private Patient patient;

    private String diagnosis;
    private String findings;
    private String recommendations;
    private Date date;
    private ReportType reportType;

    /**
     * Constructs a new Report with the specified details.
     *
     * @param patient the patient associated with the report
     * @param diagnosis the diagnosis described in the report
     * @param findings the findings described in the report
     * @param recommendations the recommendations provided in the report
     * @param date the date when the report was created
     * @param reportType the type of the report
     */
    public Report(Patient patient, String diagnosis, String findings, String recommendations, String date, ReportType reportType) {
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.findings = findings;
        this.recommendations = recommendations;
        this.date = date;
        this.reportType = reportType;
    }
}
