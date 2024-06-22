package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import msg.medsync.Repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createPatient(@RequestBody Doctor doctor) {
        // TODO validations
        doctorRepository.save(doctor);
        return ResponseEntity.ok().body("Doctor saved");
    }

    // TODO @GetMapping
    // TODO @DeleteMapping
    // TODO @PutMapping

}
