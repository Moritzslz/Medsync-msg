package msg.medsync.Services;

import msg.medsync.Models.Diagnosis;
import msg.medsync.Models.Enums.Severity;
import msg.medsync.Repositories.DiagnosisRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisService(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
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

    public ResponseEntity<Diagnosis> findDiagnosisById(long id) {
        Optional<Diagnosis> diagnosisOptional = diagnosisRepository.findById(id);
        if (diagnosisOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnosis diagnosis = diagnosisOptional.get();
        return ResponseEntity.ok(diagnosis);
    }
}
