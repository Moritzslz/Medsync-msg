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
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "patientid", referencedColumnName = "patientid")
    private Long patientId;
    @JoinColumn(name = "diagnosisid", referencedColumnName = "id")
    private String diagnosisId;
    private String findings;
    private String recommendations;
    private Date date;
    @Column(name ="reporttype")
    private String reportType;

    public Report(Long patientId, String diagnosisId, String findings, String recommendations, Date date, String reportType) {
        this.patientId = patientId;
        this.diagnosisId = diagnosisId;
        this.findings = findings;
        this.recommendations = recommendations;
        this.date = date;
        this.reportType = reportType;
    }
}
