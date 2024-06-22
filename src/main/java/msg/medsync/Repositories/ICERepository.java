package msg.medsync.Repositories;

import msg.medsync.Models.ICE;
import msg.medsync.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICERepository extends CrudRepository<ICE, Long> {
    Optional<ICE> findByPatient(Patient patient);
    Iterable<ICE> findAllByNameAndSurname(String name, String surname);
    Iterable<ICE> findAllBySurname(String surname);
}
