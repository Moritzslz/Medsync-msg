package msg.medsync.Controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/doctors/")
public class DoctorController {

    public DoctorController() {
    }

    @PostMapping("/create")
    public void createPatient() {
        // TODO
    }
}
