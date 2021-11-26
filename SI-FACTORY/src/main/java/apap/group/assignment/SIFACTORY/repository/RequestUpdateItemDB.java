package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestUpdateItemDB extends JpaRepository<RequestUpdateItemModel, Long> {
    RequestUpdateItemModel findByIdRequestUpdateItem(Integer id);
}
