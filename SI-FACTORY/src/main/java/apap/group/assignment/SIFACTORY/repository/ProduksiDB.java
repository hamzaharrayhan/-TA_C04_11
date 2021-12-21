package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduksiDB extends JpaRepository<ProduksiModel, Long> {
    Optional<ProduksiModel> findByIdProduksi(Long id);
}
