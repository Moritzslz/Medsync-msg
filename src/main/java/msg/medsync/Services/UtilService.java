package msg.medsync.Services;

import msg.medsync.Models.ReportType;
import msg.medsync.Models.Severity;

public class UtilService {

    public static Severity getSeverity(String severity) {
        return switch (severity.toLowerCase().trim()) {
            case "mild" -> Severity.MILD;
            case "moderate" -> Severity.MODERATE;
            case "severe" -> Severity.SEVERE;
            default -> throw new IllegalArgumentException("Unknown severity level: " + severity);
        };
    }

    public static ReportType getReportType(String type) {
        return switch (type.toLowerCase().trim()) {
            case "lab_results" -> ReportType.LAB_RESULTS;
            case "radiology_report" -> ReportType.RADIOLOGY_REPORT;
            case "consultation_note" -> ReportType.CONSULTATION_NOTE;
            case "surgical_report" -> ReportType.SURGICAL_REPORT;
            case "discharge_summary" -> ReportType.DISCHARGE_SUMMARY;
            case "progress_note" -> ReportType.PROGRESS_NOTE;
            case "pathology_report" -> ReportType.PATHOLOGY_REPORT;
            default -> throw new IllegalArgumentException("Unknown report type: " + type);
        };
    }
}
