package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DrugRepository extends CrudRepository<Drug, Long> {
    Optional<Drug> findByPatientId(Long patientId);
    Optional<Drug> findByPrescribingDoctor(Doctor doctor);
}
