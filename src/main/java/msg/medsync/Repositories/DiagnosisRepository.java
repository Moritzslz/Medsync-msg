package msg.medsync.Repositories;

import msg.medsync.Models.Allergy;
import msg.medsync.Models.Diagnosis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {
    Optional<ArrayList<Diagnosis>> findByPatientId(Long patientId);
}
