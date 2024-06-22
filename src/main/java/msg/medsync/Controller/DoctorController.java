package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final PatientDoctorRepository patientDoctorRepository;

    public DoctorController(DoctorRepository doctorRepository, PatientDoctorRepository patientDoctorRepository) {
        this.doctorRepository = doctorRepository;
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

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

}
