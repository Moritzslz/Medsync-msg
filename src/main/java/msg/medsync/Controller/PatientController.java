package msg.medsync.Controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/patients")
public class PatientController {

    public PatientController() {
    }

    @PostMapping("/create")
    public void createPatient() {
        // TODO
    }
}
