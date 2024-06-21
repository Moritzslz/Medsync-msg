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

    public Report(Patient patient, String diagnosis, String findings, String recommendations, String date, ReportType reportType) {
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.findings = findings;
        this.recommendations = recommendations;
        this.date = date;
        this.reportType = reportType;
    }
}
