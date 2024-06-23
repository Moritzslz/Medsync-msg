package msg.medsync.Services;

import msg.medsync.Repositories.VaccinationRepository;
import org.springframework.stereotype.Service;

@Service
public class VaccinationService {

    private VaccinationRepository vaccinationRepository;

    public VaccinationService(VaccinationRepository vaccinationRepository) {
        this.vaccinationRepository = vaccinationRepository;
    }
}
