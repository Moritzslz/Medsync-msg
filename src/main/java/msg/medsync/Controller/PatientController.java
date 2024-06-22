package msg.medsync.Controller;

import msg.medsync.Models.*;
import msg.medsync.Repositories.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static msg.medsync.Services.UtilService.*;

@RestController
@RequestMapping( "/api/v1/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final AllergyRepository allergyRepository;
    private final ICERepository iceRepository;
    private final VaccinationRepository vaccinationRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DrugRepository drugRepository;
    private final ReportRepository reportRepository;
    private final PatientDoctorRepository patientDoctorRepository;

    public PatientController(PatientRepository patientRepository, AllergyRepository allergyRepository,
                             ICERepository iceRepository, VaccinationRepository vaccinationRepository,
                             DiagnosisRepository diagnosisRepository, DrugRepository drugRepository,
                             ReportRepository reportRepository, PatientDoctorRepository patientDoctorRepository) {
        this.patientRepository = patientRepository;
        this.allergyRepository = allergyRepository;
        this.iceRepository = iceRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.drugRepository = drugRepository;
        this.reportRepository = reportRepository;
        this.patientDoctorRepository = patientDoctorRepository;
    }

     /*
    ============================================================================
    PATIENT
    ============================================================================
    */

    @PostMapping("/register")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        // TODO validations
        HealthInsuranceProvider hip = getHealthInsuranceProvider(patient.getHealthInsuranceProvider());
        patient.setHealthInsuranceProvider(hip.name());
        patientRepository.save(patient);
        return ResponseEntity.ok().body(patient);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Patient>> getAllPatients() {
        Iterable<Patient> patients = patientRepository.findAll();
        if (!patients.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patients);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("/{name}/{surname}")
    public ResponseEntity<Iterable<Patient>> getPatientByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        Iterable<Patient> patients = patientRepository.findAllByNameAndSurname(name, surname);
        if (!patients.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patients);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("/{kvr}/{hip}")
    public ResponseEntity<Patient> getPatientByKVTAndHIP(@PathVariable String kvr, String hip) {
        HealthInsuranceProvider HIP = getHealthInsuranceProvider(hip);
        Optional<Patient> patient = patientRepository.findByKVRAndHealthInsuranceProvider(kvr, HIP.name());
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<Iterable<PatientDoctor>> getAllDoctors(@PathVariable long id) {
        Iterable<PatientDoctor> patientDoctorIterable = patientDoctorRepository.findAllByPatientId(id);
        if (!patientDoctorIterable.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorIterable);
        }
    }

    /*
    ============================================================================
    EMERGENCY CONTACT
    ============================================================================
    */

    @PostMapping("/{id}/ice")
    public ResponseEntity<ICE> createICE(@RequestBody ICE ice, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, ice.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        iceRepository.save(ice);
        return ResponseEntity.ok().body(ice);
    }

    @GetMapping("/{id}/ice")
    public ResponseEntity<ICE> getICEById(@PathVariable long id) {
        Optional<ICE> ice = iceRepository.findByPatientId(id);
        if (ice.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(ice.get());
        }
    }

    @DeleteMapping("/{id}/ice/delete")
    public ResponseEntity<String> deleteIce(@PathVariable long id) {
        if(!iceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            iceRepository.deleteById(id);
            return ResponseEntity.ok().body("Emergency contact deleted");
        }
    }

    // TODO @PutMapping

    /*
    ============================================================================
    Allergy
    ============================================================================
    */

    @PostMapping("/{id}/allergy")
    public ResponseEntity<Allergy> addAllergy(@RequestBody Allergy allergy, String severity, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, allergy.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Severity severityEnum = getSeverity(severity);
        allergy.setSeverity(severityEnum);
        allergyRepository.save(allergy);
        return ResponseEntity.ok().body(allergy);
    }

    @GetMapping("/{id}/allergy/all")
    public ResponseEntity<Iterable<Allergy>> findAllAllergiesById(@PathVariable long id) {
        Iterable<Allergy> allergies = allergyRepository.findAllByPatientId(id);
        if (!allergies.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(allergies);
        }
    }

    @DeleteMapping("/{id}/allergy")
    public ResponseEntity<String> deleteAllergy(@PathVariable long id) {
        if(!allergyRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            allergyRepository.deleteById(id);
            return ResponseEntity.ok().body("Allergy deleted");
        }
    }

    // TODO @PutMapping

    /*
    ============================================================================
    Vaccination
    ============================================================================
    */

    @PostMapping("/{id}/vaccination")
    public ResponseEntity<Vaccination> addVaccination(@RequestBody Vaccination vaccination, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, vaccination.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        vaccinationRepository.save(vaccination);
        return ResponseEntity.ok().body(vaccination);
    }

    // TODO @GetMapping

    @DeleteMapping("/{id}/vaccination/")
    public ResponseEntity<String> deleteVaccination(@PathVariable long id) {
        if(!vaccinationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            vaccinationRepository.deleteById(id);
            return ResponseEntity.ok().body("Vaccination deleted");
        }
    }

    // TODO @PutMapping


    /*
    ============================================================================
    Diagnosis
    ============================================================================
    */

    @PostMapping("/{id}/diagnosis")
    public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody Diagnosis diagnosis, String severity,  @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, diagnosis.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Severity severityEnum = getSeverity(severity);
        diagnosis.setSeverity(severityEnum);
        diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok().body(diagnosis);
    }

    // TODO @GetMapping

    @DeleteMapping("/{id}/diagnosis/delete")
    public ResponseEntity<String> deleteDiagnosis(@PathVariable long id) {
        if(!diagnosisRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteById(id);
            return ResponseEntity.ok().body("Diagnosis deleted");
        }
    }


    // TODO @PutMapping


    /*
    ============================================================================
    Report
    ============================================================================
    */

    @PostMapping("/{id}/report")
    public ResponseEntity<Report> addReport(@RequestBody Report report, String type, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, report.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ReportType reportType = getReportType(type);
        report.setReportType(reportType);
        reportRepository.save(report);
        return ResponseEntity.ok().body(report);
    }

    // TODO @GetMapping

    @DeleteMapping("/{id}/report")
    public ResponseEntity<String> deleteReport(@PathVariable long id) {
        if(!reportRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            reportRepository.deleteById(id);
            return ResponseEntity.ok().body("Report deleted");
        }
    }

    // TODO @PutMapping

    /*
    ============================================================================
    Drug
    ============================================================================
    */

    @PostMapping("/{id}/drug")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, drug.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        drugRepository.save(drug);
        return ResponseEntity.ok().body(drug);
    }

    // TODO @GetMapping

    @DeleteMapping("/{id}/drug/delete")
    public ResponseEntity<String> deleteDrug(@PathVariable long id) {
        if(!drugRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            drugRepository.deleteById(id);
            return ResponseEntity.ok().body("Drug deleted");
        }
    }

    // TODO @PutMapping
}
