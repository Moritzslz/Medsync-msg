package msg.medsync.Repositories;

import msg.medsync.Models.Vaccination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface AllergyRepository extends CrudRepository<Vaccination, Long> {
    Optional<ArrayList<Vaccination>> findByPatientId(Long patientId);
}
