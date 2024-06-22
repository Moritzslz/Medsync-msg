package msg.medsync.Services;

import msg.medsync.Models.HealthInsuranceProvider;
import msg.medsync.Models.ReportType;
import msg.medsync.Models.Severity;
import org.springframework.http.ResponseEntity;

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

    public static HealthInsuranceProvider getHealthInsuranceProvider(String provider) {
        return switch (provider.toLowerCase().trim()) {
            case "aok" -> HealthInsuranceProvider.AOK;
            case "tk" -> HealthInsuranceProvider.TK;
            case "bkk" -> HealthInsuranceProvider.BKK;
            case "dak" -> HealthInsuranceProvider.DAK;
            case "barmer" -> HealthInsuranceProvider.BARMER;
            case "hek" -> HealthInsuranceProvider.HEK;
            case "kkh" -> HealthInsuranceProvider.KKH;
            case "sbk" -> HealthInsuranceProvider.SBK;
            case "ikk" -> HealthInsuranceProvider.IKK;
            case "knappschaft" -> HealthInsuranceProvider.KNAPPSCHAFT;
            case "hkk" -> HealthInsuranceProvider.HKK;
            case "viactiv" -> HealthInsuranceProvider.VIACTIV;
            case "securvita" -> HealthInsuranceProvider.SECURVITA;
            case "pronova bkk" -> HealthInsuranceProvider.PRONOVA_BKK;
            case "mercer" -> HealthInsuranceProvider.MERCER;
            case "allianz" -> HealthInsuranceProvider.ALLIANZ;
            default -> throw new IllegalArgumentException("Unknown health insurance provider: " + provider);
        };
    }

    public static ResponseEntity validateId(long Id, long BodyId) {
        if(Id != BodyId) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
