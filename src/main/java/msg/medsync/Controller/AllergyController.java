package msg.medsync.Controller;

import msg.medsync.Models.Allergy;
import msg.medsync.Repositories.AllergyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/allergy")
public class AllergyController {

    private final AllergyRepository allergyRepository;

    public AllergyController(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    @GetMapping("/{allergen}")
    public ResponseEntity<Iterable<Allergy>> getAllAllergiesByAllergen(@PathVariable String allergen) {
        Iterable<Allergy> allergies = allergyRepository.findAllByAllergen(allergen);
        if (!allergies.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(allergies);
        }
    }
}
