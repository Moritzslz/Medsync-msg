package msg.medsync.Repositories;

import msg.medsync.Models.Patient;
import msg.medsync.Models.Report;
import msg.medsync.Models.ReportType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
    Iterable<Report> findAllByPatient(Patient patient);
    Iterable<Report> findAllByReportType(String reportType);
    Iterable<Report> findAllByPatientAndReportType(Patient patient, String reportType);
    Iterable<Report> findAllByDateOrderByDateDesc(Date date);
}
