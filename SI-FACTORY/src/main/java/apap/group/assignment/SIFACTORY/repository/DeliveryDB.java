package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryDB extends JpaRepository<DeliveryModel, Long> {
    DeliveryModel findByIdDelivery(Long id);
<<<<<<< HEAD
=======
    List<DeliveryModel> findAllByIdKurir(Long id);

>>>>>>> 90194b611fb1642350d58c320bea4471930d2781
}
