package msg.medsync.Controller;

import msg.medsync.Models.*;
import msg.medsync.Repositories.ReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FacadePattern {

    private AllergyController allergyController;
    private DiagnosisController diagnosisController;
    private DoctorController doctorController;
    private PatientController patientController;
    private ReportRepository reportRepository;
    private VaccinationController vaccinationController;

    public FacadePattern(AllergyController allergyController, DiagnosisController diagnosisController, DoctorController doctorController, PatientController patientController, ReportRepository reportRepository, VaccinationController vaccinationController) {
        this.allergyController = allergyController;
        this.diagnosisController = diagnosisController;
        this.doctorController = doctorController;
        this.patientController = patientController;
        this.reportRepository = reportRepository;
        this.vaccinationController = vaccinationController;
    }

    @PostMapping("/patient/register")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        return patientController.createPatient(patient);
    }

    @PostMapping("/patient/{id}/add/doctor/{doctorId}")
    public ResponseEntity<?> addDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        return patientController.addDoctor(id, doctorId);
    }

    @GetMapping("/patient/all")
    public ResponseEntity<?> getAllPatients() {
        return patientController.getAllPatients();
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable long id) {
        return patientController.getPatientById(id);
    }

    @GetMapping("/patient/name/{name}/{surname}")
    public ResponseEntity<?> getPatientByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return patientController.getPatientByNameAndSurname(name, surname);
    }

    @GetMapping("/patient")
    public ResponseEntity<?> getPatientByEmail(@RequestParam String email) {
        return patientController.getPatientByEmail(email);
    }

    @GetMapping("/patient/kvr/{kvr}/{hip}")
    public ResponseEntity<?> getPatientByKVTAndHIP(@PathVariable String kvr, @PathVariable String hip) {
        return patientController.getPatientByKVTAndHIP(kvr, hip);
    }

    @GetMapping("/patient/{id}/doctors")
    public ResponseEntity<?> getAllDoctors(@PathVariable long id) {
        return patientController.getAllDoctors(id);
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable long id) {
        return patientController.deletePatient(id);
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientController.updatePatient(id, patient);
    }

    @PostMapping("/patient/{id}/ice")
    public ResponseEntity<?> addIce(@RequestBody ICE ice, @PathVariable long id) {
        return patientController.addIce(ice, id);
    }

    @GetMapping("/patient/{id}/ice")
    public ResponseEntity<?> getICEById(@PathVariable long id) {
        return patientController.getICEById(id);
    }

    @DeleteMapping("/patient/{id}/ice")
    public ResponseEntity<?> deleteIce(@PathVariable long id) {
        return patientController.deleteIce(id);
    }

    @PutMapping("/patient/{id}/ice")
    public ResponseEntity<?> updateICE(@RequestBody ICE ice, @PathVariable long id) {
        return patientController.updateICE(ice, id);
    }

    @PostMapping("/patient/{id}/allergy")
    public ResponseEntity<?> addAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        return allergyController.addAllergy(allergy, id);
    }

    @GetMapping("/patient/{id}/allergy/all")
    public ResponseEntity<?> getAllAllergiesById(@PathVariable long id) {
        return allergyController.getAllAllergiesById(id);
    }

    @GetMapping("/patient/{allergen}")
    public ResponseEntity<List<Allergy>> getAllAllergiesByAllergen(@PathVariable String allergen) {
        return allergyController.getAllAllergiesByAllergen(allergen);
    }

    @PutMapping("/patient/{id}/allergy")
    public ResponseEntity<?> updateAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        return allergyController.updateAllergy(allergy, id);
    }

    @DeleteMapping("/patient/{id}/allergy/all")
    public ResponseEntity<?> deleteAllAllergiesByPatientId(@PathVariable long id) {
        return allergyController.deleteAllAllergiesByPatientId(id);
    }

    @DeleteMapping("/patient/{id}/allergy/{allergyId}")
    public ResponseEntity<?> deleteAllergyById(@PathVariable long id, @PathVariable long allergyId) {
        return allergyController.deleteAllergyById(id, allergyId);
    }

    @PostMapping("/patient/{id}/vaccination")
    public ResponseEntity<?> addVaccination(@RequestBody Vaccination vaccination, @PathVariable long id) {
        return vaccinationController.addVaccination(vaccination, id);
    }

    @GetMapping("/patient/{id}/vaccination")
    public ResponseEntity<?> getVaccinationById(@PathVariable long id) {
        return vaccinationController.getVaccinationById(id);
    }

    @DeleteMapping("/patient/{id}/vaccination")
    public ResponseEntity<?> deleteAllVaccinationsByPatientId(@PathVariable long id) {
        return vaccinationController.deleteAllVaccinationsByPatientId(id);
    }

    @DeleteMapping("/patient/{id}/vaccination/{vaccinationId}")
    public ResponseEntity<?> deleteVaccinationById(@PathVariable long id, @PathVariable long vaccinationId) {
        return vaccinationController.deleteVaccinationById(id, vaccinationId);
    }

    @PutMapping("/patient/{id}/vaccination")
    public ResponseEntity<?> updateVaccination(@RequestBody Vaccination vaccinationDetails, @PathVariable long id) {
        return vaccinationController.updateVaccination(vaccinationDetails, id)
    }

    @PostMapping("/patient/{id}/diagnosis")
    public ResponseEntity<?> addDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        return diagnosisController.addDiagnosis(diagnosis, id);
    }

    @GetMapping("/patient/{id}/diagnosis")
    public ResponseEntity<?> getAllDiagnosisById(@PathVariable long id) {
        return diagnosisController.getAllDiagnosisById(id);
    }

    @DeleteMapping("/{id}/diagnosis")
    public ResponseEntity<?> deleteAllDiagnosesByPatientId(@PathVariable long id) {
        return diagnosisController.deleteAllDiagnosesByPatientId(id);
    }
}
