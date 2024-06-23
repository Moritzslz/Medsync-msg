package msg.medsync.Services;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Enums.HealthInsuranceProvider;
import msg.medsync.Models.ICE;
import msg.medsync.Models.Patient;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.ICERepository;
import msg.medsync.Repositories.PatientDoctorRepository;
import msg.medsync.Repositories.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDoctorRepository patientDoctorRepository;
    private final ICERepository iceRepository;

    public PatientService(PatientRepository patientRepository, PatientDoctorRepository patientDoctorRepository, ICERepository iceRepository) {
        this.patientRepository = patientRepository;
        this.patientDoctorRepository = patientDoctorRepository;
        this.iceRepository = iceRepository;
    }

    public ResponseEntity<Patient> findPatientById(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = patientOptional.get();
        return ResponseEntity.ok(patient);
    }

    public ResponseEntity<String> validatePatient(Patient patient) {
        if (patient.getName() == null || patient.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }
        if (patient.getSurname() == null || patient.getSurname().isEmpty()) {
            return ResponseEntity.badRequest().body("Surname is required");
        }
        if (patient.getEmail() == null || patient.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (patient.getKvr() == null || patient.getKvr().isEmpty()) {
            return ResponseEntity.badRequest().body("Health Insurance Number (KVR) is required");
        }
        if (patient.getHip() == null || patient.getHip().isEmpty()) {
            return ResponseEntity.badRequest().body("Health Insurance Provider (HIP) is required");
        }
        if (patient.getBirthday() == null) {
            return ResponseEntity.badRequest().body("Birthday is required");
        }
        if (patient.getFamilyDoctor() == null) {
            return ResponseEntity.badRequest().body("Family doctor is required");
        }
        if (patient.getIce() == null) {
            return ResponseEntity.badRequest().body("ICE (In Case of Emergency) contact is required");
        }
        return ResponseEntity.ok().build();
    }

    public PatientDoctor mapToAndSavePatientDoctor(Patient patient, Doctor doctor) {
        PatientDoctor patientDoctor = new PatientDoctor();
        patientDoctor.setPatient(patient);
        patientDoctor.setDoctor(doctor);
        patientDoctor.setPatientName(patient.getName());
        patientDoctor.setPatientSurname(patient.getSurname());
        patientDoctor.setPatientKvr(patient.getKvr());
        return patientDoctorRepository.save(patientDoctor);
    }

    public HealthInsuranceProvider getHealthInsuranceProvider(String  hip) {
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

    public ICE createNewICE(Patient patient) {
        ICE ice = new ICE();
        ice.setPatient(patient);

        ICE receivedICE = patient.getIce();
        if (receivedICE != null) {
            ice.setName(receivedICE.getName());
            ice.setSurname(receivedICE.getSurname());
            ice.setRelationship(receivedICE.getRelationship());
            ice.setEmail(receivedICE.getEmail());
            ice.setPhone(receivedICE.getPhone());
            ice.setStreet(receivedICE.getStreet());
            ice.setHouseNumber(receivedICE.getHouseNumber());
            ice.setPostalCode(receivedICE.getPostalCode());
            ice.setCity(receivedICE.getCity());
        }

        return iceRepository.save(ice);
    }
}
