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
    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;
    @JoinColumn(name = "diagnosis", referencedColumnName = "id")
    private String diagnosis;
    private String findings;
    private String recommendations;
    private Date date;
    private ReportType reportType;

    public Report(Long id, Long patientId, String diagnosis, String findings, String recommendations, Date date, ReportType reportType) {
        this.id = id;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.findings = findings;
        this.recommendations = recommendations;
        this.date = date;
        this.reportType = reportType;
    }
}
