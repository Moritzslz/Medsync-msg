package msg.medsync.Repositories;

import msg.medsync.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Optional<Patient> findByPatientId(Long patientId);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByNameAndSurname(String name, String surname);
    Optional<Patient> findByKVRAndHealthInsuranceProvider(String KVR, String healthInsuranceProvider);
}
