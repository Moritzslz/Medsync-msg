package msg.medsync.Services;

import msg.medsync.Models.Drug;
import msg.medsync.Repositories.DrugRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DrugService {

    private DrugRepository drugRepository;

    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public ResponseEntity<Drug> findDrugById(Long id) {
        Optional<Drug> optionalDrug = drugRepository.findById(id);
        if (optionalDrug.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Drug drug = optionalDrug.get();
        return ResponseEntity.ok(drug);
    }
}
