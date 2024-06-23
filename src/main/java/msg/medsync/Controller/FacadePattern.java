package msg.medsync.Controller;

import msg.medsync.Models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
public class FacadePattern {

    private final ReportController reportController;
    private AllergyController allergyController;
    private DiagnosisController diagnosisController;
    private PatientController patientController;
    private VaccinationController vaccinationController;
    private DrugController drugController;
    

    public FacadePattern(AllergyController allergyController, DiagnosisController diagnosisController, DoctorController doctorController, PatientController patientController, DrugController drugController, VaccinationController vaccinationController, ReportController reportController) {
        this.allergyController = allergyController;
        this.diagnosisController = diagnosisController;
        this.patientController = patientController;
        this.vaccinationController = vaccinationController;
        this.reportController = reportController;
        this.drugController = drugController;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        return patientController.createPatient(patient);
    }

    @PostMapping("/{id}/add/doctor/{doctorId}")
    public ResponseEntity<?> addDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        return patientController.addDoctor(id, doctorId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() {
        return patientController.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable long id) {
        return patientController.getPatientById(id);
    }

    @GetMapping("/name/{name}/{surname}")
    public ResponseEntity<?> getPatientByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return patientController.getPatientByNameAndSurname(name, surname);
    }

    @GetMapping("")
    public ResponseEntity<?> getPatientByEmail(@RequestParam String email) {
        return patientController.getPatientByEmail(email);
    }

    @GetMapping("/kvr/{kvr}/{hip}")
    public ResponseEntity<?> getPatientByKVTAndHIP(@PathVariable String kvr, @PathVariable String hip) {
        return patientController.getPatientByKVTAndHIP(kvr, hip);
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<?> getAllDoctors(@PathVariable long id) {
        return patientController.getAllDoctors(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable long id) {
        return patientController.deletePatient(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientController.updatePatient(id, patient);
    }

    @PostMapping("/{id}/ice")
    public ResponseEntity<?> addIce(@RequestBody ICE ice, @PathVariable long id) {
        return patientController.addIce(ice, id);
    }

    @GetMapping("/{id}/ice")
    public ResponseEntity<?> getICEById(@PathVariable long id) {
        return patientController.getICEById(id);
    }

    @DeleteMapping("/{id}/ice")
    public ResponseEntity<?> deleteIce(@PathVariable long id) {
        return patientController.deleteIce(id);
    }

    @PutMapping("/{id}/ice")
    public ResponseEntity<?> updateICE(@RequestBody ICE ice, @PathVariable long id) {
        return patientController.updateICE(ice, id);
    }

    @PostMapping("/{id}/allergy")
    public ResponseEntity<?> addAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        return allergyController.addAllergy(allergy, id);
    }

    @GetMapping("/{id}/allergy/all")
    public ResponseEntity<?> getAllAllergiesById(@PathVariable long id) {
        return allergyController.getAllAllergiesById(id);
    }

    @GetMapping("/{allergen}")
    public ResponseEntity<List<Allergy>> getAllAllergiesByAllergen(@PathVariable String allergen) {
        return allergyController.getAllAllergiesByAllergen(allergen);
    }

    @PutMapping("/{id}/allergy")
    public ResponseEntity<?> updateAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        return allergyController.updateAllergy(allergy, id);
    }

    @DeleteMapping("/{id}/allergy/all")
    public ResponseEntity<?> deleteAllAllergiesByPatientId(@PathVariable long id) {
        return allergyController.deleteAllAllergiesByPatientId(id);
    }

    @DeleteMapping("/{id}/allergy/{allergyId}")
    public ResponseEntity<?> deleteAllergyById(@PathVariable long id, @PathVariable long allergyId) {
        return allergyController.deleteAllergyById(id, allergyId);
    }

    @PostMapping("/{id}/vaccination")
    public ResponseEntity<?> addVaccination(@RequestBody Vaccination vaccination, @PathVariable long id) {
        return vaccinationController.addVaccination(vaccination, id);
    }

    @GetMapping("/{id}/vaccination")
    public ResponseEntity<?> getVaccinationById(@PathVariable long id) {
        return vaccinationController.getVaccinationById(id);
    }

    @DeleteMapping("/{id}/vaccination")
    public ResponseEntity<?> deleteAllVaccinationsByPatientId(@PathVariable long id) {
        return vaccinationController.deleteAllVaccinationsByPatientId(id);
    }

    @DeleteMapping("/{id}/vaccination/{vaccinationId}")
    public ResponseEntity<?> deleteVaccinationById(@PathVariable long id, @PathVariable long vaccinationId) {
        return vaccinationController.deleteVaccinationById(id, vaccinationId);
    }

    @PutMapping("/{id}/vaccination")
    public ResponseEntity<?> updateVaccination(@RequestBody Vaccination vaccinationDetails, @PathVariable long id) {
        return vaccinationController.updateVaccination(vaccinationDetails, id);
    }

    @PostMapping("/{id}/diagnosis")
    public ResponseEntity<?> addDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        return diagnosisController.addDiagnosis(diagnosis, id);
    }

    @GetMapping("/{id}/diagnosis")
    public ResponseEntity<?> getAllDiagnosisById(@PathVariable long id) {
        return diagnosisController.getAllDiagnosisById(id);
    }

    @DeleteMapping("/{id}/diagnosis")
    public ResponseEntity<?> deleteAllDiagnosesByPatientId(@PathVariable long id) {
        return diagnosisController.deleteAllDiagnosesByPatientId(id);
    }

    @DeleteMapping("/{id}/diagnosis/{diagnosisId}")
    public ResponseEntity<?> deleteDiagnosisById(@PathVariable long id, @PathVariable long diagnosisId) {
        return diagnosisController.deleteDiagnosisById(id, diagnosisId);
    }
    
    @PutMapping("/{id}/diagnosis")
    public ResponseEntity<?> updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        return diagnosisController.updateDiagnosis(diagnosis, id);
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<?> addReport(@RequestBody Report report, @PathVariable long id) {
        return reportController.addReport(report, id);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<?> findAllReportsById(@PathVariable long id) {
        return reportController.findAllReportsById(id);
    }

    @DeleteMapping("/{id}/report")
    public ResponseEntity<?> deleteAllReportsByPatientId(@PathVariable long id) {
        return reportController.deleteAllReportsByPatientId(id);
    }

    @DeleteMapping("/{id}/report/{reportId}")
    public ResponseEntity<String> deleteReportById(@PathVariable long id, @PathVariable long reportId) {
        return reportController.deleteReportById(id, reportId);
    }

    @PutMapping("/{id}/report")
    public ResponseEntity<?> updateReport(@RequestBody Report reportDetails, @PathVariable long id) {
        return reportController.updateReport(reportDetails, id);
    }

    @PostMapping("//{id}/drug")
    public ResponseEntity<?> addDrug(@RequestBody Drug drug, @PathVariable long id) {
        return drugController.addDrug(drug, id);
    }

    @GetMapping("/{id}/drug")
    public ResponseEntity<?> getAllDrugsByPatientId(@PathVariable long id) {
        return drugController.getAllDrugsByPatientId(id);
    }

    @DeleteMapping("/{id}/drug")
    public ResponseEntity<?> deleteDrug(@PathVariable long id) {
        return drugController.deleteDrug(id);
    }

    @PutMapping("/{id}/drug")
    public ResponseEntity<?> updateDrug(@RequestBody Drug drugDetails, @PathVariable long id) {
        return drugController.updateDrug(drugDetails, id);
    }
    
}
