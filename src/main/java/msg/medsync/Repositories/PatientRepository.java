package msg.medsync.Repositories;

import msg.medsync.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Iterable<Patient> findAllByNameAndSurname(String name, String surname);
    Iterable<Patient> findAllBySurname(String surname);
    Iterable<Patient> findAllByKvr(String KVR);
    Optional<Patient> findByKvrAndHip(String KVR, String HIP);
}
