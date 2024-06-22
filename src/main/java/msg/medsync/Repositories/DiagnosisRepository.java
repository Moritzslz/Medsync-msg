package msg.medsync.Repositories;

import msg.medsync.Models.Diagnosis;
import msg.medsync.Models.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {
    Iterable<Diagnosis> findAllByPatientId(Long patientId);
    Iterable<Diagnosis> findAllByIssuedBy(Doctor doctor);
    Iterable<Diagnosis> findAllByDateDiagnosedOrderByDateDiagnosedDesc(Date date);
    Iterable<Diagnosis> findAllByIllness(String illness);
    Iterable<Diagnosis> findAllBySeverity(String severity);
}
