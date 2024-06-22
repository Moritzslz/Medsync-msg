package msg.medsync.Repositories;

import msg.medsync.Models.Report;
import msg.medsync.Models.ReportType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
    Iterable<Report> findAllByPatientId(Long patientId);
    Iterable<Report> findAllByReportType(ReportType reportType);
    Iterable<Report> findAllByPatientIdAndReportType(Long patientId, ReportType reportType);
    Iterable<Report> findAllByDateOrderByDateDesc(Date date);
}
