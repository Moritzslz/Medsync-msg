package msg.medsync.Controller;

import lombok.NoArgsConstructor;
import msg.medsync.Models.Doctor;
import msg.medsync.Repositories.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/doctors/")
public class DoctorController {

    @Autowired
    public final DoctorRepository doctorRepository;

    @PostMapping("/create")
    public HttpStatus createDoctor(@RequestBody Doctor doctor) {
        doctorRepository.save(doctor);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    GetById(@PathVariable Long id) {
        return doctorRepository.findById(id).get();
    }
}
