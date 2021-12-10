package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MesinDB extends JpaRepository<MesinModel, Long> {
    Optional<MesinModel> findByIdMesin(Long id);
    List<MesinModel> findByIdKategori(Integer idKategori);
}
