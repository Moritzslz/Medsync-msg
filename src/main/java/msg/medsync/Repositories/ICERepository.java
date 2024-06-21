package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.ICE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ICERepository extends CrudRepository<ICE, Long> {
    Optional<ICE> findByPatientId(Long patientId);
}
