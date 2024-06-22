package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Drug;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DrugRepository extends CrudRepository<Drug, Long> {
    Iterable<Drug> findAllByPatientId(Long patientId);
    Iterable<Drug> findAllByPrescribingDoctor(Doctor doctor);
    Iterable<Drug> findAllByName(String name);
    Iterable<Drug> findAllByStartDateOrderByStartDateDesc(Date startDate);
    Iterable<Drug> findAllByEndDateOrderByEndDateDesc(Date endDate);
}
