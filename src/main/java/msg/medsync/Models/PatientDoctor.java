package msg.medsync.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "patientid", referencedColumnName = "patientid")
    private Long patientId;
    @JoinColumn(name = "doctorid", referencedColumnName = "doctorid")
    private Long doctorId;
    @Column(name = "patientname")
    private String patientName;
    @Column(name = "patientsurname")
    private String patientSurname;
    @Column(name = "patientkvr")
    private String patientKVR;
    @Column(name = "patienthip")
    private String patientHIP;

    public PatientDoctor(Long patientId, Long doctorId, String patientName, String patientSurname, String patientKVR, String patientHIP) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientKVR = patientKVR;
        this.patientHIP = patientHIP;
    }
}
