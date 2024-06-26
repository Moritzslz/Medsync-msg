/*
package msg.medsync.Controller;

import msg.medsync.Models.*;
import msg.medsync.Models.Enums.Allergen;
import msg.medsync.Models.Enums.HealthInsuranceProvider;
import msg.medsync.Models.Enums.Severity;
import msg.medsync.Repositories.*;
import msg.medsync.Services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping( "/api/v1/patient")
public class PatientController {

    private final PatientRepository patientRepository;
    private final AllergyRepository allergyRepository;
    private final ICERepository iceRepository;
    private final VaccinationRepository vaccinationRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DrugController drugRepository;
    private final ReportRepository reportRepository;
    private final PatientDoctorRepository patientDoctorRepository;
    private final DoctorRepository doctorRepository;
    private final PatientService patientService;

    public PatientController(PatientRepository patientRepository, AllergyRepository allergyRepository,
                             ICERepository iceRepository, VaccinationRepository vaccinationRepository,
                             DiagnosisRepository diagnosisRepository, DrugController drugRepository,
                             ReportRepository reportRepository, PatientDoctorRepository patientDoctorRepository,
                             DoctorRepository doctorRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.allergyRepository = allergyRepository;
        this.iceRepository = iceRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.drugRepository = drugRepository;
        this.reportRepository = reportRepository;
        this.patientDoctorRepository = patientDoctorRepository;
        this.doctorRepository = doctorRepository;
        this.patientService = patientService;
    }

     */
/*
    ============================================================================
    PATIENT
    ============================================================================
    *//*


    @PostMapping("/register")
    public ResponseEntity<Object> createPatient(@RequestBody Patient patient) {

        ResponseEntity<String> validated = patientService.validatePatient(patient);
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validated.getStatusCode()).body(validated.getBody());
        }

        HealthInsuranceProvider hip = patientService.getHealthInsuranceProvider(patient);
        patient.setHip(hip.name());

        ICE ice = patient.getIce();
        if (ice != null) {
            ice.setPatient(patient);
            iceRepository.save(ice);
        } else {
            ICE nIce = patientService.createNewICE(patient);
            patient.setIce(nIce);
        }

        Patient savedPatient = patientRepository.save(patient);

        patientService.mapToAndSavePatientDoctor(patient);

        return ResponseEntity.ok().body(savedPatient);
    }

    @PostMapping("{id}/add/doctor/{doctorId}")
    public ResponseEntity<PatientDoctor> addDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (patient.isEmpty() || doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            PatientDoctor patientDoctor = patientService.mapToAndSavePatientDoctor(patient.get());
            return ResponseEntity.ok().body(patientDoctor);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        if (patients.isEmpty()) {
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

    @GetMapping("name/{name}/{surname}")
    public ResponseEntity<List<Patient>> getPatientByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        List<Patient> patients = (List<Patient>) patientRepository.findAllByNameAndSurname(name, surname);
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patients);
        }
    }

    @GetMapping()
    public ResponseEntity<Patient> getPatientByEmail(@RequestParam String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("kvr/{kvr}/{hip}")
    public ResponseEntity<Patient> getPatientByKVTAndHIP(@PathVariable String kvr, @PathVariable String hip) {
        HealthInsuranceProvider HIP = patientService.getHealthInsuranceProvider(hip);
        Optional<Patient> patient = patientRepository.findByKvrAndHip(kvr, HIP.name());
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<PatientDoctor>> getAllDoctors(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PatientDoctor> patientDoctorList = (List<PatientDoctor>) patientDoctorRepository.findAllByPatient(patient.get());
        if (patientDoctorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable long id) {
        if(!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            patientRepository.deleteById(id);
            return ResponseEntity.ok().body("Patient deleted");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient existingPatient = optionalPatient.get();
        existingPatient.setIce(patient.getIce());
        existingPatient.setFamilyDoctor(patient.getFamilyDoctor());
        existingPatient.setKvr(patient.getKvr());
        existingPatient.setHip(patient.getHip());
        existingPatient.setName(patient.getName());
        existingPatient.setSurname(patient.getSurname());
        existingPatient.setBirthday(patient.getBirthday());
        existingPatient.setWeightKg(patient.getWeightKg());
        existingPatient.setHeightCm(patient.getHeightCm());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setStreet(patient.getStreet());
        existingPatient.setHouseNumber(patient.getHouseNumber());
        existingPatient.setPostalCode(patient.getPostalCode());
        existingPatient.setCity(patient.getCity());

        patientRepository.save(existingPatient);
        return ResponseEntity.ok().body(existingPatient);
    }

    */
/*
    ============================================================================
    EMERGENCY CONTACT
    ============================================================================
    *//*


    @PostMapping("/{id}/ice")
    public ResponseEntity<ICE> addIce(@RequestBody ICE ice, @PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ice.setPatient(patient.get());
        ICE savedIce = iceRepository.save(ice);
        return ResponseEntity.ok().body(savedIce);
    }

    @GetMapping("/{id}/ice")
    public ResponseEntity<ICE> getICEById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<ICE> ice = iceRepository.findByPatient(patient.get());
        if (ice.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(ice.get());
        }
    }

    @DeleteMapping("/{id}/ice")
    public ResponseEntity<String> deleteIce(@PathVariable long id) {
        if(!iceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            iceRepository.deleteById(id);
            return ResponseEntity.ok().body("Emergency contact deleted");
        }
    }

    @PutMapping("/{id}/ice")
    public ResponseEntity<ICE> updateICE(@RequestBody ICE ice, @PathVariable long id) {

        ResponseEntity validated = validateId(id, ice.getIceId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = optionalPatient.get();
        ICE existingICE = patient.getIce();
        if (existingICE == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingICE.setName(ice.getName());
        existingICE.setSurname(ice.getSurname());
        existingICE.setCity(ice.getCity());
        existingICE.setPhone(ice.getPhone());
        existingICE.setStreet(ice.getStreet());
        existingICE.setPostalCode(ice.getPostalCode());
        existingICE.setRelationship(ice.getRelationship());

        iceRepository.save(existingICE);
        return ResponseEntity.ok().body(existingICE);
    }

    */
/*
    ============================================================================
    Allergy
    ============================================================================
    *//*


    @PostMapping("/{id}/allergy")
    public ResponseEntity<Allergy> addAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        // Fetch the patient by id
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = patientOptional.get();

        // Check if the patient id matches
        if (!patient.getPatientId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Set the patient to the allergy
        allergy.setPatient(patient);

        // Validate and set enums
        Allergen allergen = getAllergen(allergy.getAllergen());
        Severity severity = getSeverity(allergy.getSeverity());
        allergy.setAllergen(allergen.name());
        allergy.setSeverity(severity.name());

        // Save the allergy
        allergyRepository.save(allergy);
        return ResponseEntity.ok().body(allergy);
    }

    @GetMapping("/{id}/allergy/all")
    public ResponseEntity<List<Allergy>> findAllAllergiesById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Allergy> allergies = (List<Allergy>) allergyRepository.findAllByPatient(patient.get());
        if (!allergies.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(allergies);
        }
    }

    @DeleteMapping("/{id}/allergy/all")
    public ResponseEntity<String> deleteAllAllergiesByPatientId(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Allergy> allergies = (List<Allergy>) allergyRepository.findAllByPatient(patient.get());
        if (allergies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            allergyRepository.deleteAll(allergies);
            return ResponseEntity.ok().body("All allergies deleted");
        }
    }

    @DeleteMapping("/{id}/allergy/{allergyId}")
    public ResponseEntity<String> deleteAllergyById(@PathVariable long id, @PathVariable long allergyId) {
        Optional<Allergy> allergy = allergyRepository.findById(allergyId);
        
        if (allergy.isEmpty() || !allergy.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            allergyRepository.deleteById(allergyId);
            return ResponseEntity.ok().body("Allergy deleted");
        }
    }    

    @PutMapping("{id}/allergy")
    public ResponseEntity<Allergy> updateAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        ResponseEntity validated = validateId(id, allergy.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Allergy> optionalAllergy = allergyRepository.findById(allergy.getId());
        if (optionalAllergy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Allergy existingAllergy = optionalAllergy.get();
        if (!existingAllergy.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Severity severity = getSeverity(allergy.getSeverity());
        existingAllergy.setSeverity(severity.name());
        existingAllergy.setAllergen(allergy.getAllergen());
        existingAllergy.setReaction(allergy.getReaction());
        existingAllergy.setNotes(allergy.getNotes());
        existingAllergy.setDateDiagnosed(allergy.getDateDiagnosed());

        allergyRepository.save(existingAllergy);
        return ResponseEntity.ok().body(existingAllergy);
    }

    */
/*
    ============================================================================
    Vaccination
    ============================================================================
    *//*


    @PostMapping("/{id}/vaccination")
    public ResponseEntity<Vaccination> addVaccination(@RequestBody Vaccination vaccination, @PathVariable long id) {
        // TODO validations
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Doctor> administeringDoctor = doctorRepository.findById(vaccination.getAdministeringDoctor().getDoctorId());
        if (administeringDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        vaccination.setAdministeringDoctor(administeringDoctor.get());
        vaccination.setPatient(patient.get());
        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return ResponseEntity.ok().body(savedVaccination);
    }

    @GetMapping("{id}/vaccination")
    public ResponseEntity<List<Vaccination>> getVaccinationById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Vaccination> vaccinations = (List<Vaccination>) vaccinationRepository.findAllByPatient(patient.get());
        if (!vaccinations.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(vaccinations);
        }
    }

    @DeleteMapping("/{id}/vaccination")
    public ResponseEntity<String> deleteAllVaccinationsByPatientId(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Vaccination> vaccinations = (List<Vaccination>) vaccinationRepository.findAllByPatient(patient.get());
        if (vaccinations.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            vaccinationRepository.deleteAll(vaccinations);
            return ResponseEntity.ok().body("All vaccinations deleted");
        }
    }
    

    @DeleteMapping("/{id}/vaccination/{vaccinationId}")
    public ResponseEntity<String> deleteVaccinationById(@PathVariable long id, @PathVariable long vaccinationId) {
        Optional<Vaccination> vaccination = vaccinationRepository.findById(vaccinationId);
        
        if (vaccination.isEmpty() || !vaccination.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            vaccinationRepository.deleteById(vaccinationId);
            return ResponseEntity.ok().body("Vaccination deleted");
        }
    }

    @PutMapping("/{id}/vaccination")
    public ResponseEntity<Vaccination> updateVaccination(@RequestBody Vaccination vaccinationDetails, @PathVariable long id) {
        ResponseEntity validated = validateId(id, vaccinationDetails.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Vaccination> optionalVaccination = vaccinationRepository.findById(vaccinationDetails.getId());
        if (optionalVaccination.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Vaccination existingVaccination = optionalVaccination.get();
        if (!existingVaccination.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingVaccination.setVaccineName(vaccinationDetails.getVaccineName());
        existingVaccination.setVaccinationDate(vaccinationDetails.getVaccinationDate());
        existingVaccination.setDosage(vaccinationDetails.getDosage());
        existingVaccination.setAdministeringDoctor(vaccinationDetails.getAdministeringDoctor());
        existingVaccination.setNotificationDate(vaccinationDetails.getNotificationDate());

        vaccinationRepository.save(existingVaccination);
        return ResponseEntity.ok().body(existingVaccination);
    }


    */
/*
    ============================================================================
    Diagnosis
    ============================================================================
    *//*


    @PostMapping("/{id}/diagnosis")
    public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        // TODO validations
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        diagnosis.setPatient(patient.get());
        Optional<Doctor> doctor = doctorRepository.findById(diagnosis.getIssuedBy().getDoctorId());
        if (doctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        diagnosis.setIssuedBy(doctor.get());
        Severity severity = getSeverity(diagnosis.getSeverity());
        diagnosis.setSeverity(severity.name());
        Diagnosis diagnosisSaved = diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok().body(diagnosisSaved);
    }

    @GetMapping("/{id}/diagnosis")
    public ResponseEntity<List<Diagnosis>> getAllDiagnosisById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Diagnosis> diagnoses = (List<Diagnosis>) diagnosisRepository.findAllByPatient(patient.get());
        if (!diagnoses.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(diagnoses);
        }
    }

    @DeleteMapping("/{id}/diagnosis")
    public ResponseEntity<String> deleteAllDiagnosesByPatientId(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Diagnosis> diagnoses = (List<Diagnosis>) diagnosisRepository.findAllByPatient(patient.get());
        if (diagnoses.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteAll(diagnoses);
            return ResponseEntity.ok().body("All diagnoses deleted");
        }
    }

    @DeleteMapping("/{id}/diagnosis/{diagnosisId}")
    public ResponseEntity<String> deleteDiagnosisById(@PathVariable long id, @PathVariable long diagnosisId) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(diagnosisId);
        
        if (diagnosis.isEmpty() || !diagnosis.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteById(diagnosisId);
            return ResponseEntity.ok().body("Diagnosis deleted");
        }
    }

    @PutMapping("/{id}/diagnosis")
    public ResponseEntity<Diagnosis> updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        ResponseEntity validated = validateId(id, diagnosis.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Diagnosis> optionalDiagnosis = diagnosisRepository.findById(diagnosis.getId());
        if (optionalDiagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnosis existingDiagnosis = optionalDiagnosis.get();
        if (!existingDiagnosis.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Severity severity = getSeverity(diagnosis.getSeverity());
        existingDiagnosis.setSeverity(severity.name());
        existingDiagnosis.setDescription(diagnosis.getDescription());
        existingDiagnosis.setIllness(diagnosis.getIllness());
        existingDiagnosis.setIssuedBy(diagnosis.getIssuedBy());
        existingDiagnosis.setDateDiagnosed(diagnosis.getDateDiagnosed());

        diagnosisRepository.save(existingDiagnosis);
        return ResponseEntity.ok().body(existingDiagnosis);
    }


    */
/*
    ============================================================================
    Report
    ============================================================================
    *//*


    @PostMapping("/{id}/report")
    public ResponseEntity<Report> addReport(@RequestBody Report report, @PathVariable long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Fetch the diagnosis from the repository
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(report.getDiagnosis().getId());
        if (diagnosis.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        report.setPatient(patient.get());
        report.setDiagnosis(diagnosis.get());

        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<List<Report>> findAllReportsById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Report> reports = (List<Report>) reportRepository.findAllByPatient(patient.get());
        if (!reports.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reports);
        }
    }

    @DeleteMapping("/{id}/report")
    public ResponseEntity<String> deleteAllReportsByPatientId(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Report> reports = (List<Report>) reportRepository.findAllByPatient(patient.get());
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
    public ResponseEntity<Report> updateReport(@RequestBody Report reportDetails, @PathVariable long id) {
        ResponseEntity validated = validateId(id, reportDetails.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Report> optionalReport = reportRepository.findById(reportDetails.getId());
        if (optionalReport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Report existingReport = optionalReport.get();
        if (!existingReport.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingReport.setReportType(reportDetails.getReportType());
        existingReport.setDate(reportDetails.getDate());
        existingReport.setFindings(reportDetails.getFindings());
        existingReport.setRecommendations(reportDetails.getRecommendations());


        reportRepository.save(existingReport);
        return ResponseEntity.ok().body(existingReport);
    }


    */
/*
    ============================================================================
    Drug
    ============================================================================
    *//*


    @PostMapping("/{id}/drug")
    public ResponseEntity<Drug> addDrug(@RequestBody Drug drug, @PathVariable long id) {
        // TODO validations
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Doctor> doctor = doctorRepository.findById(drug.getPrescribingDoctor().getDoctorId());
        if (doctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        drug.setPatient(patient.get());
        drug.setPrescribingDoctor(doctor.get());

        Drug savedDrug = drugRepository.save(drug);
        return ResponseEntity.ok().body(savedDrug);
    }

    @GetMapping("/{id}/drug")
    public ResponseEntity<List<Drug>> getAllDrugsByPatientId(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Drug> drugs = (List<Drug>) drugRepository.findAllByPatient(patient.get());
        if (!drugs.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(drugs);
        }
    }

    @DeleteMapping("/{id}/drug")
    public ResponseEntity<String> deleteDrug(@PathVariable long id) {
        if(!drugRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            drugRepository.deleteById(id);
            return ResponseEntity.ok().body("Drug deleted");
        }
    }

    @PutMapping("/{id}/drug")
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drugDetails, @PathVariable long id) {
        ResponseEntity validated = validateId(id, drugDetails.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Drug> optionalDrug = drugRepository.findById(drugDetails.getId());
        if (optionalDrug.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Drug existingDrug = optionalDrug.get();
        if (!existingDrug.getPatient().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingDrug.setName(drugDetails.getName());
        existingDrug.setDosage(drugDetails.getDosage());
        existingDrug.setFrequency(drugDetails.getFrequency());
        existingDrug.setStartDate(drugDetails.getStartDate());
        existingDrug.setEndDate(drugDetails.getEndDate());
        existingDrug.setPrescribingDoctor(drugDetails.getPrescribingDoctor());
        existingDrug.setSideEffects(drugDetails.getSideEffects());

        drugRepository.save(existingDrug);
        return ResponseEntity.ok().body(existingDrug);
    }
}
*/
