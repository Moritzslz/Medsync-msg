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
        Patient patientSaved = patientRepository.save(patient);
        PatientDoctor patientDoctor = new PatientDoctor();
        patientDoctor.setPatientId(patientSaved.getPatientId());
        patientDoctor.setDoctorId(patientSaved.getFamilyDoctor().getDoctorId());
        patientDoctor.setPatientName(patientSaved.getName());
        patientDoctor.setPatientSurname(patientSaved.getSurname());
        patientDoctor.setPatientKVR(patientSaved.getKVR());
        patientDoctorRepository.save(patientDoctor);
        return ResponseEntity.ok().body(patient);
    }

    @PostMapping("{id}/add/doctor/{doctorId}")
    public ResponseEntity<PatientDoctor> addDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Patient currentPatient = patient.get();
            PatientDoctor patientDoctor = new PatientDoctor();
            patientDoctor.setPatientId(currentPatient.getPatientId());
            patientDoctor.setDoctorId(doctorId);
            patientDoctor.setPatientName(currentPatient.getName());
            patientDoctor.setPatientSurname(currentPatient.getSurname());
            patientDoctor.setPatientKVR(currentPatient.getKVR());
            patientDoctorRepository.save(patientDoctor);
            return ResponseEntity.ok().body(patientDoctor);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient existingPatient = optionalPatient.get();
        existingPatient.setICE(patient.getICE());
        existingPatient.setFamilyDoctor(patient.getFamilyDoctor());
        existingPatient.setKVR(patient.getKVR());
        existingPatient.setHealthInsuranceProvider(patient.getHealthInsuranceProvider());
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

    @PutMapping("/{id}/ice")
    public ResponseEntity<ICE> updateICE(@RequestBody ICE ice, @PathVariable long id) {

        ResponseEntity validated = validateId(id, ice.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = optionalPatient.get();
        ICE existingICE = patient.getICE();
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
    public ResponseEntity<Allergy> addAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, allergy.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Allergen allergen =  getAllergene(allergy.getAllergen());
        Severity severity = getSeverity(allergy.getSeverity());
        allergy.setSeverity(allergen.name());
        allergy.setSeverity(severity.name());
        allergyRepository.save(allergy);
        return ResponseEntity.ok().body(allergy);
    }
    @PutMapping("{id}/allergy")
    public ResponseEntity<Allergy> updateAllergy(@RequestBody Allergy allergy, @PathVariable long id) {
        ResponseEntity validated = validateId(id, allergy.getPatientId());
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
        if (!existingAllergy.getPatientId().equals(id)) {
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

    @GetMapping("{id}/vaccination")
    public ResponseEntity<Iterable<Vaccination>> getVaccinationById(@PathVariable long id) {
        Iterable<Vaccination> vaccinations = vaccinationRepository.findAllByPatientId(id);
        if (!vaccinations.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(vaccinations);
        }
    }

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
    public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, diagnosis.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Severity severity = getSeverity(diagnosis.getSeverity());
        diagnosis.setSeverity(severity.name());
        diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok().body(diagnosis);
    }

    @GetMapping("/{id}/diagnosis")
    public ResponseEntity<Iterable<Diagnosis>> getAllDiagnosisById(@PathVariable long id) {
        Iterable<Diagnosis> diagnoses = diagnosisRepository.findAllByPatientId(id);
        if (!diagnoses.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(diagnoses);
        }
    }

    @DeleteMapping("/{id}/diagnosis")
    public ResponseEntity<String> deleteDiagnosis(@PathVariable long id) {
        if(!diagnosisRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            diagnosisRepository.deleteById(id);
            return ResponseEntity.ok().body("Diagnosis deleted");
        }
    }

    @PutMapping("/{id}/diagnosis")
    public ResponseEntity<Diagnosis> updateDiagnosis(@RequestBody Diagnosis diagnosis, @PathVariable long id) {
        ResponseEntity validated = validateId(id, diagnosis.getPatientId());
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
        if (!existingDiagnosis.getPatientId().equals(id)) {
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


    /*
    ============================================================================
    Report
    ============================================================================
    */

    @PostMapping("/{id}/report")
    public ResponseEntity<Report> addReport(@RequestBody Report report, @PathVariable long id) {
        // TODO validations
        ResponseEntity validated = validateId(id, report.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ReportType reportType = getReportType(report.getReportType());
        report.setReportType(reportType.name());
        reportRepository.save(report);
        return ResponseEntity.ok().body(report);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<Iterable<Report>> findAllReportsById(@PathVariable long id) {
        Iterable<Report> reports = reportRepository.findAllByPatientId(id);
        if (!reports.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(reports);
        }
    }

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

    @GetMapping("/{id}/drug")
    public ResponseEntity<Iterable<Drug>> getAllDrugsByPatientId(@PathVariable long id) {
        Iterable<Drug> drugs = drugRepository.findAllByPatientId(id);
        if (!drugs.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(drugs);
        }
    }

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
