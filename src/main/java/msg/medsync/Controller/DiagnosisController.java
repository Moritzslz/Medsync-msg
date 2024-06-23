package msg.medsync.Controller;

import msg.medsync.Models.Diagnosis;
import msg.medsync.Models.Doctor;
import msg.medsync.Models.Enums.Severity;
import msg.medsync.Models.Patient;
import msg.medsync.Repositories.AllergyRepository;
import msg.medsync.Repositories.DiagnosisRepository;
import msg.medsync.Services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/diagnosis")
public class DiagnosisController {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;
    private final UtilService utilService;
    private final DoctorService doctorService;

    public DiagnosisController(DiagnosisRepository diagnosisRepository, PatientService patientService, DiagnosisService diagnosisService, UtilService utilService, DoctorService doctorService) {
        this.diagnosisRepository = diagnosisRepository;
        this.patientService = patientService;
        this.diagnosisService = diagnosisService;
        this.utilService = utilService;
        this.doctorService = doctorService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        ResponseEntity<Doctor> doctorResponseEntity = doctorService.findDoctorById(id);
        if (!doctorResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return doctorResponseEntity;
        }
        Doctor doctor = doctorResponseEntity.getBody();

        diagnosis.setPatient(patient);
        diagnosis.setIssuedBy(doctor);

        Severity severity = diagnosisService.getSeverity(diagnosis.getSeverity());
        diagnosis.setSeverity(severity.name());

        return ResponseEntity.ok().body(diagnosisRepository.save(diagnosis));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllDiagnosisById(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Diagnosis> diagnoses = (List<Diagnosis>) diagnosisRepository.findAllByPatient(patient);

        if (!diagnoses.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(diagnoses);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAllDiagnosesByPatientId(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Diagnosis> diagnoses = (List<Diagnosis>) diagnosisRepository.findAllByPatient(patient);

        if (diagnoses.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteAll(diagnoses);
            return ResponseEntity.ok().body("All diagnoses deleted");
        }
    }

    @DeleteMapping("/{id}/{diagnosisId}")
    public ResponseEntity<String> deleteDiagnosisById(@PathVariable long id, @PathVariable long diagnosisId) {

        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(diagnosisId);

        if (diagnosis.isEmpty() || !diagnosis.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteById(diagnosisId);
            return ResponseEntity.ok().body("Diagnosis deleted");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {

        ResponseEntity<?> validated = utilService.validateId(id, diagnosis.getPatient().getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findById(diagnosis.getId());
        if (optionalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnosis existingDiagnosis = optionalDiagnosis.get();

        Severity severity = diagnosisService.getSeverity(diagnosis.getSeverity());
        existingDiagnosis.setPatient(patient);
        existingDiagnosis.setSeverity(severity.name());
        existingDiagnosis.setDescription(diagnosis.getDescription());
        existingDiagnosis.setIllness(diagnosis.getIllness());
        existingDiagnosis.setIssuedBy(diagnosis.getIssuedBy());
        existingDiagnosis.setDateDiagnosed(diagnosis.getDateDiagnosed());

        return ResponseEntity.ok().body(diagnosisRepository.save(existingDiagnosis));
    }
}
