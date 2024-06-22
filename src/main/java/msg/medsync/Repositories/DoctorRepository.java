package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Iterable<Doctor> findAllByNameAndSurname(String name, String surname);
    Iterable<Doctor> findAllBySurname(String surname);
    Iterable<Doctor> findAllBySpecialty(String speciality);
    Iterable<Doctor> findAllByPostalCode(String postalCode);
    Optional<Doctor> findByEmail(String email);
}
