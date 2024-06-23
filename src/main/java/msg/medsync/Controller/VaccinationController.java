package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import msg.medsync.Models.Vaccination;
import msg.medsync.Repositories.VaccinationRepository;
import msg.medsync.Services.DoctorService;
import msg.medsync.Services.PatientService;
import msg.medsync.Services.UtilService;
import msg.medsync.Services.VaccinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vaccination")
public class VaccinationController {

    private final VaccinationService vaccinationService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final VaccinationRepository vaccinationRepository;
    private final UtilService utilService;

    public VaccinationController(VaccinationService vaccinationService, PatientService patientService, DoctorService doctorService, VaccinationRepository vaccinationRepository, UtilService utilService) {
        this.vaccinationService = vaccinationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.vaccinationRepository = vaccinationRepository;
        this.utilService = utilService;
    }

    @PostMapping("/{id}/vaccination")
    public ResponseEntity<?> addVaccination(@RequestBody Vaccination vaccination, @PathVariable long id) {

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

        vaccination.setAdministeringDoctor(doctor);
        vaccination.setPatient(patient);

        return ResponseEntity.ok().body(vaccinationRepository.save(vaccination));
    }

    @GetMapping("{id}/vaccination")
    public ResponseEntity<?> getVaccinationById(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Vaccination> vaccinations = (List<Vaccination>) vaccinationRepository.findAllByPatient(patient);
        if (!vaccinations.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(vaccinations);
        }
    }

    @DeleteMapping("/{id}/vaccination")
    public ResponseEntity<?> deleteAllVaccinationsByPatientId(@PathVariable long id) {

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

        List<Vaccination> vaccinations = (List<Vaccination>) vaccinationRepository.findAllByPatient(patient);
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
    public ResponseEntity<?> updateVaccination(@RequestBody Vaccination vaccinationDetails, @PathVariable long id) {

        ResponseEntity<?> validated = utilService.validateId(id, vaccinationDetails.getPatient().getPatientId());
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return validated;
        }

        ResponseEntity<Patient> patientResponseEntity = patientService.findPatientById(id);
        if (!patientResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return patientResponseEntity;
        }
        Patient patient = patientResponseEntity.getBody();

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

        return ResponseEntity.ok().body( vaccinationRepository.save(existingVaccination));
    }
}
