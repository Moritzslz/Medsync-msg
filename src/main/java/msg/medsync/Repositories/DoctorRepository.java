package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByNameAndSurname(String name, String surname);
    Optional<ArrayList<Doctor>> findByPostalCode(String postalCode);
}
