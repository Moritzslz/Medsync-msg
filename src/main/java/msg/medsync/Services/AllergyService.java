package msg.medsync.Services;

import msg.medsync.Models.Enums.Allergen;
import msg.medsync.Models.Enums.Severity;
import msg.medsync.Repositories.AllergyRepository;
import org.springframework.stereotype.Service;

@Service
public class AllergyService {

    private final AllergyRepository allergyRepository;

    public AllergyService(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

    public Allergen getAllergen(String allergen) {
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

    public Severity getSeverity(String severity) {
        return switch (severity.toLowerCase().trim()) {
            case "mild" -> Severity.MILD;
            case "moderate" -> Severity.MODERATE;
            case "severe" -> Severity.SEVERE;
            case "critical" -> Severity.CRITICAL;
            default -> throw new IllegalArgumentException("Unknown severity level: " + severity);
        };
    }
}
