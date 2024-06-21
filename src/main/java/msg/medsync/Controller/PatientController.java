package msg.medsync.Controller;

import lombok.NoArgsConstructor;
import msg.medsync.Models.Patient;
import msg.medsync.Repositories.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/patients")
public class PatientController {
    
    @Autowired
    public final PatientRepository patientRepository;

    @PostMapping("/create")
    public HttpStatus createPatient(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    GetById(@PathVariable Long id) {
        return patientRepository.findById(id).get();
    }
    
}
