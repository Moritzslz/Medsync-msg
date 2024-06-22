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

    @JoinColumn(name = "patientId", referencedColumnName = "patientId")
    private Long patientId;

    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId")
    private Long doctorId;

    @Column(name = "patientName")
    private String patientName;

    @Column(name = "patientSurname")
    private String patientSurname;

    @Column(name = "patientKVR")
    private String patientKVR;

    @Column(name = "patientHIP")
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
