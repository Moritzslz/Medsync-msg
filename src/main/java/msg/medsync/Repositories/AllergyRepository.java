package msg.medsync.Repositories;

import msg.medsync.Models.Allergy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {
    Iterable<Allergy> findAllByPatientId(Long id);
    Iterable<Allergy> findAllByAllergen(String allergen);
}
