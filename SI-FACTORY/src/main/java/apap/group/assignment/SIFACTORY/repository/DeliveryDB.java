package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryDB extends JpaRepository<DeliveryModel, Long> {
    DeliveryModel findByIdDelivery(Long id);
    Optional<DeliveryModel> findDeliveryByIdDelivery(Long id);
    List<DeliveryModel> findAllByIdKurir(Long id);

}
