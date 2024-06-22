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
@Table(name = "drug")
@JsonIgnoreProperties({"patient"})
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "prescribing_doctor", nullable = false)
    private Doctor prescribingDoctor;

    private String name;
    private String dosage;
    private String frequency;
    private Date startDate;
    private Date endDate;
    private String sideEffects;
}
