package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientDoctorRepository patientDoctorRepository;

    public DoctorController(DoctorRepository doctorRepository, PatientRepository patientRepository, PatientDoctorRepository patientDoctorRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientDoctorRepository = patientDoctorRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) {
        // TODO validations
        doctorRepository.save(doctor);
        return ResponseEntity.ok().body("Doctor saved");
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<Iterable<PatientDoctor>> getAllPatients(@PathVariable long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Iterable<PatientDoctor> patientDoctorIterable = patientDoctorRepository.findAllByDoctor(doctor.get());
        if (!patientDoctorIterable.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorIterable);
        }
    }

    @PostMapping("{id}/add/patient/{patiendId}")
    public ResponseEntity<PatientDoctor> addPatient(@PathVariable Long id, @PathVariable Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (patient.isEmpty() || doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Patient currentPatient = patient.get();
            Doctor currentDoctor = doctor.get();
            PatientDoctor patientDoctor = new PatientDoctor();
            patientDoctor.setPatient(currentPatient);
            patientDoctor.setDoctor(currentDoctor);
            patientDoctor.setPatientName(currentPatient.getName());
            patientDoctor.setPatientSurname(currentPatient.getSurname());
            patientDoctor.setPatientKVR(currentPatient.getKVR());
            patientDoctorRepository.save(patientDoctor);
            return ResponseEntity.ok().body(patientDoctor);
        }
    }


    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Doctor existingDoctor = optionalDoctor.get();
        existingDoctor.setName(doctorDetails.getName());
        existingDoctor.setSurname(doctorDetails.getSurname());
        existingDoctor.setSpeciality(doctorDetails.getSpeciality());
        existingDoctor.setEmail(doctorDetails.getEmail());
        existingDoctor.setPhone(doctorDetails.getPhone());
        existingDoctor.setStreet(doctorDetails.getStreet());
        existingDoctor.setHouseNumber(doctorDetails.getHouseNumber());
        existingDoctor.setPostalCode(doctorDetails.getPostalCode());
        existingDoctor.setCity(doctorDetails.getCity());

        doctorRepository.save(existingDoctor);
        return ResponseEntity.ok().body(existingDoctor);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable long id) {
        if(!doctorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            doctorRepository.deleteById(id);
            return ResponseEntity.ok().body("Doctor deleted");
        }
    }

    @DeleteMapping("{id}/patient/{patientId}")
    public ResponseEntity<String> deletePatientDoctor(@PathVariable Long id, @PathVariable Long patientId) {
        List<PatientDoctor> patients = patientDoctorRepository.findByPatientId(patientId);
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<PatientDoctor> patientDoctors = patients.stream()
                .filter(pd -> pd.getDoctorId().equals(id))
                .findFirst();
            
            if (patientDoctors.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                patientDoctorRepository.delete(patientDoctors.get());
                return ResponseEntity.ok().body("Patient deleted from doctor");
            }
        }
    }
}
