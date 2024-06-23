package msg.medsync.Services;

import msg.medsync.Models.Doctor;
import msg.medsync.Repositories.DoctorRepository;
import msg.medsync.Repositories.PatientDoctorRepository;
import msg.medsync.Repositories.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientDoctorRepository oatientDoctorRepository;

    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository, PatientDoctorRepository oatientDoctorRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.oatientDoctorRepository = oatientDoctorRepository;
    }

    public ResponseEntity<Doctor> findDoctorById(Long id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Doctor doctor = doctorOptional.get();
        return ResponseEntity.ok(doctor);
    }
}
