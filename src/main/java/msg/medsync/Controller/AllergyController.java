package msg.medsync.Controller;

import msg.medsync.Models.Allergy;
import msg.medsync.Models.Enums.Allergen;
import msg.medsync.Models.Enums.Severity;
import msg.medsync.Models.Patient;
import msg.medsync.Repositories.AllergyRepository;
import msg.medsync.Services.AllergyService;
import msg.medsync.Services.PatientService;
import msg.medsync.Services.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/allergy")
public class AllergyController {

    private final AllergyRepository allergyRepository;
    private final PatientService patientService;
    private final AllergyService allergyService;
    private final UtilService utilService;

    public AllergyController(AllergyRepository allergyRepository, PatientService patientService, AllergyService allergyService, UtilService utilService) {
        this.allergyRepository = allergyRepository;
        this.patientService = patientService;
        this.allergyService = allergyService;
        this.utilService = utilService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addAllergy(@RequestBody Allergy allergy, @PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        if (!allergy.getPatient().getPatientId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        allergy.setPatient(patient);
        Allergen allergen = allergyService.getAllergen(allergy.getAllergen());
        Severity severity = allergyService.getSeverity(allergy.getSeverity());
        allergy.setAllergen(allergen.name());
        allergy.setSeverity(severity.name());

        return ResponseEntity.ok().body(allergyRepository.save(allergy));
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<?> getAllAllergiesById(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }

        Patient patient = patientResponseEntity.getBody();

        List<Allergy> allergies = (List<Allergy>) allergyRepository.findAllByPatient(patient);

        if (allergies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(allergies);
        }
    }

    @GetMapping("/{allergen}")
    public ResponseEntity<List<Allergy>> getAllAllergiesByAllergen(@PathVariable String allergen) {

        List<Allergy> allergies = (List<Allergy>) allergyRepository.findAllByAllergen(allergen);

        if (allergies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allergies);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAllergy(@RequestBody Allergy allergy, @PathVariable long id) {

        ResponseEntity<?> validated = utilService.validateId(id, allergy.getId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        Optional<Allergy> optionalAllergy = allergyRepository.findById(allergy.getId());
        if (optionalAllergy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Allergy existingAllergy = optionalAllergy.get();
        if (!existingAllergy.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Allergen allergen = allergyService.getAllergen(allergy.getAllergen());
        Severity severity = allergyService.getSeverity(allergy.getSeverity());

        existingAllergy.setPatient(patient);
        existingAllergy.setSeverity(severity.name());
        existingAllergy.setAllergen(allergen.name());
        existingAllergy.setReaction(allergy.getReaction());
        existingAllergy.setNotes(allergy.getNotes());
        existingAllergy.setDateDiagnosed(allergy.getDateDiagnosed());

        return ResponseEntity.ok().body(allergyRepository.save(existingAllergy));
    }

    @DeleteMapping("/{id}/all")
    public ResponseEntity<?> deleteAllAllergiesByPatientId(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Allergy> allergies = (List<Allergy>) allergyRepository.findAllByPatient(patient);
        if (allergies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            allergyRepository.deleteAll(allergies);
            return ResponseEntity.ok().body("All allergies deleted");
        }
    }

    @DeleteMapping("/{id}/{allergyId}")
    public ResponseEntity<String> deleteAllergyById(@PathVariable long id, @PathVariable long allergyId) {
        Optional<Allergy> allergy = allergyRepository.findById(allergyId);

        if (allergy.isEmpty() || !allergy.get().getId().equals(id)) {
            return ResponseEntity.notFound().build();
        } else {
            allergyRepository.deleteById(allergyId);
            return ResponseEntity.ok().body("Allergy deleted");
        }
    }
}
