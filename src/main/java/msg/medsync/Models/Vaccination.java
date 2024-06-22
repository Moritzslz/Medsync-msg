package msg.medsync.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vaccination")
@JsonIgnoreProperties({"patient"})
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "administering_doctor", nullable = false)
    private Doctor administeringDoctor;

    private String vaccineName;
    private String dosage;
    private String frequency;
    private Date vaccinationDate;
    private Date notificationDate;
}
