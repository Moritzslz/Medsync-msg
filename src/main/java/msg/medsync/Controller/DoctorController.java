package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Patient;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.*;
import msg.medsync.Services.DoctorService;
import msg.medsync.Services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientDoctorRepository patientDoctorRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public DoctorController(DoctorRepository doctorRepository, PatientRepository patientRepository, PatientDoctorRepository patientDoctorRepository, DoctorService doctorService, PatientService patientService) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientDoctorRepository = patientDoctorRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor) {
        doctorRepository.save(doctor);
        return ResponseEntity.ok().body("Doctor saved");
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<?> getAllPatients(@PathVariable long id) {

        ResponseEntity<Doctor> doctorResponseEntity = doctorService.findDoctorById(id);
        if (!doctorResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return doctorResponseEntity;
        }
        Doctor doctor = doctorResponseEntity.getBody();

        List<PatientDoctor> patientDoctorList = (List<PatientDoctor>) patientDoctorRepository.findAllByDoctor(doctor);

        if (!patientDoctorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorList);
        }
    }

    @PostMapping("{id}/add/patient/{patientId}")
    public ResponseEntity<?> addPatient(@PathVariable Long id, @PathVariable Long patientId) {

        Optional<Doctor> doctor = doctorRepository.findById(id);
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isEmpty() || doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            PatientDoctor patientDoctor = patientService.mapToAndSavePatientDoctor(patient.get(), doctor.get());
            return ResponseEntity.ok().body(patientDoctor);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {

        ResponseEntity<Doctor> doctorResponseEntity = doctorService.findDoctorById(id);
        if (!doctorResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return doctorResponseEntity;
        }
        Doctor existingDoctor = doctorResponseEntity.getBody();

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

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = (List<Doctor>) doctorRepository.findAll();
        if (doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(doctors);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(doctor.get());
        }
    }

    @GetMapping("/name/{name}/{surname}")
    public ResponseEntity<List<Doctor>> getDoctorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        List<Doctor> doctors = (List<Doctor>) doctorRepository.findAllByNameAndSurname(name, surname);
        if (doctors.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(doctors);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
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
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PatientDoctor> patients = (List<PatientDoctor>) patientDoctorRepository.findAllByPatient(patient.get());
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<PatientDoctor> patientDoctors = patients.stream()
                .filter(pd -> pd.getDoctor().getDoctorId().equals(id))
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
