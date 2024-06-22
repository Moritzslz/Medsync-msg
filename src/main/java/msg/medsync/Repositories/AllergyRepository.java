package msg.medsync.Repositories;

import msg.medsync.Models.Allergy;
import msg.medsync.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {
    Iterable<Allergy> findAllByPatient(Patient patient);
    Iterable<Allergy> findAllByAllergen(String allergen);
}
