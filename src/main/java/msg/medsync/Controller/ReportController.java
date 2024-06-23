package msg.medsync.Controller;

import msg.medsync.Models.Diagnosis;
import msg.medsync.Models.Enums.ReportType;
import msg.medsync.Models.Patient;
import msg.medsync.Models.Report;
import msg.medsync.Repositories.ReportRepository;
import msg.medsync.Services.DiagnosisService;
import msg.medsync.Services.PatientService;
import msg.medsync.Services.ReportService;
import msg.medsync.Services.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportRepository reportRepository;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final UtilService utilService;

    public ReportController(ReportService reportService, ReportRepository reportRepository, PatientService patientService, DiagnosisService diagnosisService, UtilService utilService) {
        this.reportService = reportService;
        this.reportRepository = reportRepository;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
        this.utilService = utilService;
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<?> addReport(@RequestBody Report report, @PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        ResponseEntity<Diagnosis> diagnosisResponseEntity = diagnosisService.findDiagnosisById(report.getDiagnosis().getId());
        if (!diagnosisResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return diagnosisResponseEntity;
        }
        Diagnosis diagnosis = diagnosisResponseEntity.getBody();

        report.setPatient(patient);
        report.setDiagnosis(diagnosis);

        return ResponseEntity.ok( reportRepository.save(report));
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<?> findAllReportsById(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Report> reports = (List<Report>) reportRepository.findAllByPatient(patient);
        if (!reports.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reports);
        }
    }

    @DeleteMapping("/{id}/report")
    public ResponseEntity<?> deleteAllReportsByPatientId(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Report> reports = (List<Report>) reportRepository.findAllByPatient(patient);
        if (!reports.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            reportRepository.deleteAll(reports);
            return ResponseEntity.ok().body("All reports deleted");
        }
    }

    @DeleteMapping("/{id}/report/{reportId}")
    public ResponseEntity<String> deleteReportById(@PathVariable long id, @PathVariable long reportId) {

        Optional<Report> report = reportRepository.findById(reportId);

        if (report.isEmpty() || !report.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            reportRepository.deleteById(reportId);
            return ResponseEntity.ok().body("Report deleted");
        }
    }


    @PutMapping("/{id}/report")
    public ResponseEntity<?> updateReport(@RequestBody Report reportDetails, @PathVariable long id) {

        ResponseEntity<?> validated = utilService.validateId(id, reportDetails.getPatient().getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        ResponseEntity<Report> reportResponseEntity = reportService.findReportById(reportDetails.getId());
        if (!reportResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return reportResponseEntity;
        }
        Report existingReport = reportResponseEntity.getBody();

        ReportType reportType = reportService.getReportType(reportDetails.getReportType());
        existingReport.setReportType(reportType.name());
        existingReport.setDate(reportDetails.getDate());
        existingReport.setFindings(reportDetails.getFindings());
        existingReport.setRecommendations(reportDetails.getRecommendations());

        return ResponseEntity.ok().body(reportRepository.save(existingReport));
    }
}
