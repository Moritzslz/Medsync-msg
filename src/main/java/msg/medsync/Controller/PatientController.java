package msg.medsync.Controller;

import msg.medsync.Models.*;
import msg.medsync.Repositories.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static msg.medsync.Services.UtilService.getReportType;
import static msg.medsync.Services.UtilService.getSeverity;
import static msg.medsync.Services.UtilService.validateId;

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

     /*
    ============================================================================
    PATIENT
    ============================================================================
    */

    @PostMapping("/register")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        // TODO validations
        patientRepository.save(patient);
        return ResponseEntity.ok().body(patient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (!optionalPatient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient existingPatient = optionalPatient.get();
        existingPatient.setICE(patientDetails.getICE());
        existingPatient.setFamilyDoctor(patientDetails.getFamilyDoctor());
        existingPatient.setKVR(patientDetails.getKVR());
        existingPatient.setHealthInsuranceProvider(patientDetails.getHealthInsuranceProvider());
        existingPatient.setName(patientDetails.getName());
        existingPatient.setSurname(patientDetails.getSurname());
        existingPatient.setBirthday(patientDetails.getBirthday());
        existingPatient.setWeightKg(patientDetails.getWeightKg());
        existingPatient.setHeightCm(patientDetails.getHeightCm());
        existingPatient.setEmail(patientDetails.getEmail());
        existingPatient.setPhone(patientDetails.getPhone());
        existingPatient.setStreet(patientDetails.getStreet());
        existingPatient.setHouseNumber(patientDetails.getHouseNumber());
        existingPatient.setPostalCode(patientDetails.getPostalCode());
        existingPatient.setCity(patientDetails.getCity());


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

    @GetMapping("/{kvr}/{healthInsuranceProvider}")
    public ResponseEntity<Patient> getPatientByKVTAndHealthInsuranceProvider(@PathVariable String kvr, String healthInsuranceProvider) {
        Optional<Patient> patient = patientRepository.findByKVRAndHealthInsuranceProvider(kvr, healthInsuranceProvider);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    /*
    ============================================================================
    EMERGENCY CONTACT
    ============================================================================
    */

    @PutMapping("/{id}/ice")
    public ResponseEntity<ICE> updateICE(@RequestBody ICE iceDetails, @PathVariable long id) {

        ResponseEntity validated = validateId(id, iceDetails.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (!optionalPatient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = optionalPatient.get();
        ICE existingICE = patient.getICE();
        if (existingICE == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingICE.setName(iceDetails.getName());
        existingICE.setSurname(iceDetails.getSurname());
        existingICE.setCity(iceDetails.getCity());
        existingICE.setPhone(iceDetails.getPhone());
        existingICE.setStreet(iceDetails.getStreet());
        existingICE.setPostalCode(iceDetails.getPostalCode());
        existingICE.setRelationship(iceDetails.getRelationship());

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

    // TODO @DeleteMapping
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
    @PutMapping("{id}/allergy")
    public ResponseEntity<Allergy> updateAllergy(@RequestBody Allergy allergyDetails, @PathVariable long id, String severity) {
        ResponseEntity validated = validateId(id, allergyDetails.getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (!optionalPatient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Allergy> optionalAllergy = allergyRepository.findById(allergyDetails.getId());
        if (!optionalAllergy.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Allergy existingAllergy = optionalAllergy.get();
        if (!existingAllergy.getPatientId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Severity severityEnum = getSeverity(severity);
        existingAllergy.setSeverity(severityEnum);
        existingAllergy.setAllergen(allergyDetails.getAllergen());
        existingAllergy.setReaction(allergyDetails.getReaction());
        existingAllergy.setNotes(allergyDetails.getNotes());
        existingAllergy.setDateDiagnosed(allergyDetails.getDateDiagnosed());

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

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

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
    // TODO @DeleteMapping
    // TODO @PutMapping

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
    // TODO @DeleteMapping
    // TODO @PutMapping

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
    // TODO @DeleteMapping
    // TODO @PutMapping

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
    // TODO @DeleteMapping
    // TODO @PutMapping
}
