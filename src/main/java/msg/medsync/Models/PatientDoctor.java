package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.Doc;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "patient_doctor")
public class PatientDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "patientid")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctorid")
    private Doctor doctor;
    @Column(name = "patientname")
    private String patientName;
    @Column(name = "patientsurname")
    private String patientSurname;
    @Column(name = "patientkvr")
    private String patientKVR;
    @Column(name = "patienthip")
    private String patientHIP;

    public PatientDoctor(Patient patient, Doctor doctor, String patientName, String patientSurname, String patientKVR, String patientHIP) {
        this.patient = patient;
        this.doctor = doctor;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientKVR = patientKVR;
        this.patientHIP = patientHIP;
    }
}
