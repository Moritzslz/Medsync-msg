package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientDoctorRepository patientDoctorRepository;

    public DoctorController(DoctorRepository doctorRepository, PatientRepository patientRepository, PatientDoctorRepository patientDoctorRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientDoctorRepository = patientDoctorRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) {
        // TODO validations
        doctorRepository.save(doctor);
        return ResponseEntity.ok().body("Doctor saved");
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<Iterable<PatientDoctor>> getAllPatients(@PathVariable long id) {
        Iterable<PatientDoctor> patientDoctorIterable = patientDoctorRepository.findAllByDoctorId(id);
        if (!patientDoctorIterable.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorIterable);
        }
    }

    @PostMapping("{id}/add/patient/{patiendId}")
    public ResponseEntity<PatientDoctor> addPatient(@PathVariable Long id, @PathVariable Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Patient currentPatient = patient.get();
            PatientDoctor patientDoctor = new PatientDoctor();
            patientDoctor.setPatientId(currentPatient.getPatientId());
            patientDoctor.setDoctorId(id);
            patientDoctor.setPatientName(currentPatient.getName());
            patientDoctor.setPatientSurname(currentPatient.getSurname());
            patientDoctor.setPatientKVR(currentPatient.getKVR());
            patientDoctorRepository.save(patientDoctor);
            return ResponseEntity.ok().body(patientDoctor);
        }
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

}
