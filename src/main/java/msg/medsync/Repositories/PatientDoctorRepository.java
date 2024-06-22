package msg.medsync.Repositories;

import msg.medsync.Models.Allergy;
import msg.medsync.Models.PatientDoctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDoctorRepository extends CrudRepository<PatientDoctor, Long> {
    Iterable<PatientDoctor> findAllByPatientId(long id);
    Iterable<PatientDoctor> findAllByDoctorId(long id);
}
