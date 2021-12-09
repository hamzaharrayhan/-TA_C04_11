package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestUpdateItemDB extends JpaRepository<RequestUpdateItemModel, Long> {
    Optional<RequestUpdateItemModel> findByIdRequestUpdateItem(Long id);
}
