package msg.medsync.Controller;

import msg.medsync.Models.Doctor;
import msg.medsync.Models.Enums.HealthInsuranceProvider;
import msg.medsync.Models.ICE;
import msg.medsync.Models.Patient;
import msg.medsync.Models.PatientDoctor;
import msg.medsync.Repositories.*;
import msg.medsync.Services.PatientService;
import msg.medsync.Services.UtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/api/v1/")
public class PatientController {

    private final PatientRepository patientRepository;
    private final ICERepository iceRepository;
    private final PatientDoctorRepository patientDoctorRepository;
    private final DoctorRepository doctorRepository;
    private final PatientService patientService;
    private final UtilService utilService;

    public PatientController(PatientRepository patientRepository,
                             ICERepository iceRepository, PatientDoctorRepository patientDoctorRepository,
                             DoctorRepository doctorRepository, PatientService patientService, UtilService utilService) {
        this.patientRepository = patientRepository;
        this.iceRepository = iceRepository;
        this.patientDoctorRepository = patientDoctorRepository;
        this.doctorRepository = doctorRepository;
        this.patientService = patientService;
        this.utilService = utilService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createPatient(@RequestBody Patient patient) {

        ResponseEntity<String> validated = patientService.validatePatient(patient);
        if (!validated.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(validated.getStatusCode()).body(validated.getBody());
        }

        HealthInsuranceProvider hip = patientService.getHealthInsuranceProvider(patient.getHip());
        patient.setHip(hip.name());

        ICE ice = patient.getIce();
        if (ice != null) {
            ice.setPatient(patient);
            iceRepository.save(ice);
        } else {
            ICE nIce = patientService.createNewICE(patient);
            patient.setIce(nIce);
        }

        Patient savedPatient = patientRepository.save(patient);

        patientService.mapToAndSavePatientDoctor(patient, patient.getFamilyDoctor());

        return ResponseEntity.ok().body(savedPatient);
    }

    @PostMapping("{id}/add/doctor/{doctorId}")
    public ResponseEntity<PatientDoctor> addDoctor(@PathVariable Long id, @PathVariable Long doctorId) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (patient.isEmpty() || doctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            PatientDoctor patientDoctor = patientService.mapToAndSavePatientDoctor(patient.get(), doctor.get());
            return ResponseEntity.ok().body(patientDoctor);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patients);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("name/{name}/{surname}")
    public ResponseEntity<List<Patient>> getPatientByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        List<Patient> patients = (List<Patient>) patientRepository.findAllByNameAndSurname(name, surname);
        if (patients.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patients);
        }
    }

    @GetMapping()
    public ResponseEntity<Patient> getPatientByEmail(@RequestParam String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("kvr/{kvr}/{hip}")
    public ResponseEntity<Patient> getPatientByKVTAndHIP(@PathVariable String kvr, @PathVariable String hip) {
        HealthInsuranceProvider HIP = patientService.getHealthInsuranceProvider(hip);
        Optional<Patient> patient = patientRepository.findByKvrAndHip(kvr, HIP.name());
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patient.get());
        }
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<PatientDoctor>> getAllDoctors(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PatientDoctor> patientDoctorList = (List<PatientDoctor>) patientDoctorRepository.findAllByPatient(patient.get());
        if (patientDoctorList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(patientDoctorList);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable long id) {
        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            patientRepository.deleteById(id);
            return ResponseEntity.ok().body("Patient deleted");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient existingPatient = optionalPatient.get();
        existingPatient.setIce(patient.getIce());
        existingPatient.setFamilyDoctor(patient.getFamilyDoctor());
        existingPatient.setKvr(patient.getKvr());
        existingPatient.setHip(patient.getHip());
        existingPatient.setName(patient.getName());
        existingPatient.setSurname(patient.getSurname());
        existingPatient.setBirthday(patient.getBirthday());
        existingPatient.setWeightKg(patient.getWeightKg());
        existingPatient.setHeightCm(patient.getHeightCm());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setStreet(patient.getStreet());
        existingPatient.setHouseNumber(patient.getHouseNumber());
        existingPatient.setPostalCode(patient.getPostalCode());
        existingPatient.setCity(patient.getCity());

        patientRepository.save(existingPatient);
        return ResponseEntity.ok().body(existingPatient);
    }

    @PostMapping("/{id}/ice")
    public ResponseEntity<ICE> addIce(@RequestBody ICE ice, @PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ice.setPatient(patient.get());
        ICE savedIce = iceRepository.save(ice);
        return ResponseEntity.ok().body(savedIce);
    }

    @GetMapping("/{id}/ice")
    public ResponseEntity<ICE> getICEById(@PathVariable long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<ICE> ice = iceRepository.findByPatient(patient.get());
        if (ice.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(ice.get());
        }
    }

    @DeleteMapping("/{id}/ice")
    public ResponseEntity<String> deleteIce(@PathVariable long id) {
        if(!iceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        } else {
            iceRepository.deleteById(id);
            return ResponseEntity.ok().body("Emergency contact deleted");
        }
    }

    @PutMapping("/{id}/ice")
    public ResponseEntity<ICE> updateICE(@RequestBody ICE ice, @PathVariable long id) {

        utilService.validateId(id, ice.getPatient().getPatientId());

        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Patient patient = optionalPatient.get();
        ICE existingICE = patient.getIce();
        if (existingICE == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        existingICE.setName(ice.getName());
        existingICE.setSurname(ice.getSurname());
        existingICE.setCity(ice.getCity());
        existingICE.setPhone(ice.getPhone());
        existingICE.setStreet(ice.getStreet());
        existingICE.setPostalCode(ice.getPostalCode());
        existingICE.setRelationship(ice.getRelationship());

        iceRepository.save(existingICE);
        return ResponseEntity.ok().body(existingICE);
    }
}