package msg.medsync.Repositories;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Vaccination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface VaccinationRepository extends CrudRepository<Vaccination, Long> {
    Iterable<Vaccination> findAllByPatientId(Long patientId);
    Iterable<Vaccination> findAllByAdministeringDoctor(Doctor doctor);
    Iterable<Vaccination> findAllByVaccineName(String vaccineName);
    Iterable<Vaccination> findAllByVaccinationDateOrderByVaccinationDateDesc(Date date);
    Iterable<Vaccination> findAllByNotificationDateAfter(Date date);
    Iterable<Vaccination> findAllByNotificationDateBefore(Date date);
    Iterable<Vaccination> findAllByVaccinationDateAfter(Date date);
    Iterable<Vaccination> findAllByVaccinationDateBefore(Date date);
}
