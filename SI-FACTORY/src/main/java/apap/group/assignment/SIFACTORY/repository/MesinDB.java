package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MesinDB extends JpaRepository<MesinModel, Long> {
<<<<<<< HEAD
    MesinModel findByIdMesin(Long id);
=======
<<<<<<< HEAD
    MesinModel findByIdMesin(Long id);
=======
    Optional<MesinModel> findByIdMesin(Long id);
>>>>>>> b811d8f96b86e9be4976c1f155c7a67387848efa
>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
}
