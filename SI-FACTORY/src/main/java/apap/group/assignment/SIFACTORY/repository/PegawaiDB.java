package apap.group.assignment.SIFACTORY.repository;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByUsername(String username);
}
