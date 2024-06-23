package msg.medsync.Services;

import msg.medsync.Models.Enums.Allergen;
import msg.medsync.Models.Enums.HealthInsuranceProvider;
import msg.medsync.Models.Enums.ReportType;
import msg.medsync.Models.Enums.Severity;
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

    public static HealthInsuranceProvider getHealthInsuranceProvider(String hip) {
        return switch (hip.toLowerCase().trim()) {
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
            default -> throw new IllegalArgumentException("Unknown health insurance provider: " + hip);
        };
    }

    public static Allergen getAllergen(String allergen) {
        return switch (allergen.toLowerCase().trim()) {
            case "pollen" -> Allergen.POLLEN;
            case "dust" -> Allergen.DUST;
            case "pet dander" -> Allergen.PET_DANDER;
            case "peanuts" -> Allergen.PEANUTS;
            case "shellfish" -> Allergen.SHELLFISH;
            case "milk" -> Allergen.MILK;
            case "egg" -> Allergen.EGG;
            case "penicillin" -> Allergen.PENICILLIN;
            case "latex" -> Allergen.LATEX;
            case "insect stings" -> Allergen.INSECT_STINGS;
            case "mold" -> Allergen.MOLD;
            case "soy" -> Allergen.SOY;
            case "wheat" -> Allergen.WHEAT;
            case "fish" -> Allergen.FISH;
            case "other" -> Allergen.OTHER;
            default -> throw new IllegalArgumentException("Unknown allergen: " + allergen);
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
