package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import java.util.List;
public interface ProduksiService {
    List <ProduksiModel> getListOfProduksi();
    void deleteProduksi(ProduksiModel produksi);
    ProduksiModel getProduksiByIdProduksi(Long id);
    List<ProduksiModel> getListProduksiByCheckItem();
}
