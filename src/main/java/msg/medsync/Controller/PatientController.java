package msg.medsync.Controller;

import msg.medsync.Models.*;
import msg.medsync.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static msg.medsync.Services.UtilService.getReportType;
import static msg.medsync.Services.UtilService.getSeverity;

@RestController
@RequestMapping( "/api/v1/patient")
public class PatientController {
    
    @Autowired
    public final PatientRepository patientRepository;

    private final PatientRepository patientRepository;
    private final AllergyRepository allergyRepository;
    private final ICERepository iceRepository;
    private final VaccinationRepository vaccinationRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DrugRepository drugRepository;
    private final ReportRepository reportRepository;

    public PatientController(PatientRepository patientRepository, AllergyRepository allergyRepository,
                             ICERepository iceRepository, VaccinationRepository vaccinationRepository,
                             DiagnosisRepository diagnosisRepository, DrugRepository drugRepository,
                             ReportRepository reportRepository) {
        this.patientRepository = patientRepository;
        this.allergyRepository = allergyRepository;
        this.iceRepository = iceRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.drugRepository = drugRepository;
        this.reportRepository = reportRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createPatient(@RequestBody Patient patient) {
        // TODO validations
        patientRepository.save(patient);
        return ResponseEntity.ok().body("Patient saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/ice")
    public ResponseEntity<String> createICE(@RequestBody ICE ice) {
        // TODO validations
        iceRepository.save(ice);
        return ResponseEntity.ok().body("Emergency contact saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/allergy")
    public ResponseEntity<String> addAllergy(@RequestBody Allergy allergy, String severity) {
        // TODO validations
        Severity severityEnum = getSeverity(severity);
        allergy.setSeverity(severityEnum);
        allergyRepository.save(allergy);
        return ResponseEntity.ok().body("Allergy saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/vaccination")
    public ResponseEntity<String> addVaccination(@RequestBody Vaccination vaccination) {
        // TODO validations
        vaccinationRepository.save(vaccination);
        return ResponseEntity.ok().body("Vaccination saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/diagnosis")
    public ResponseEntity<String> addDiagnosis(@RequestBody Diagnosis diagnosis, String severity) {
        // TODO validations
        Severity severityEnum = getSeverity(severity);
        diagnosis.setSeverity(severityEnum);
        diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok().body("Diagnosis saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/report")
    public ResponseEntity<String> addReport(@RequestBody Report report, String type) {
        // TODO validations
        ReportType reportType = getReportType(type);
        report.setReportType(reportType);
        reportRepository.save(report);
        return ResponseEntity.ok().body("Report saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

    @PostMapping("/add/Drug")
    public ResponseEntity<String> addDiagnosis(@RequestBody Drug drug) {
        // TODO validations
        drugRepository.save(drug);
        return ResponseEntity.ok().body("Drug saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping
}
