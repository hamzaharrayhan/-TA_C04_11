package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesinDB extends JpaRepository<MesinModel, Long> {
    MesinModel findByIdMesin(Long id);
}
