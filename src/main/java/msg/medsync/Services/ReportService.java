package msg.medsync.Services;

import msg.medsync.Models.Enums.ReportType;
import msg.medsync.Models.Report;
import msg.medsync.Repositories.ReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportType getReportType(String type) {
        return switch (type.toLowerCase().trim()) {
            case "lab results" -> ReportType.LAB_RESULTS;
            case "radiology report" -> ReportType.RADIOLOGY_REPORT;
            case "consultation note" -> ReportType.CONSULTATION_NOTE;
            case "surgical report" -> ReportType.SURGICAL_REPORT;
            case "discharge summary" -> ReportType.DISCHARGE_SUMMARY;
            case "progress note" -> ReportType.PROGRESS_NOTE;
            case "pathology report" -> ReportType.PATHOLOGY_REPORT;
            default -> throw new IllegalArgumentException("Unknown report type: " + type);
        };
    }

    public ResponseEntity<Report> findReportById(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Report report = optionalReport.get();
        return ResponseEntity.ok(report);
    }
}
