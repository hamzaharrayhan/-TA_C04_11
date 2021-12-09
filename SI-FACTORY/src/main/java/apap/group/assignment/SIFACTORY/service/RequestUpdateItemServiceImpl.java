package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RequestUpdateItemServiceImpl implements RequestUpdateItemService{

    @Autowired
    RequestUpdateItemDB requestUpdateItemDB;

    @Autowired
    ProduksiDB produksiDB;

    @Autowired
    MesinService mesinService;

    @Override
    public List<RequestUpdateItemModel> getRequestUpdateItemList() {
        return requestUpdateItemDB.findAll();
    }

    @Override
    public void updateRequestUpdateItem(Long idRequestUpdateItem, PegawaiModel staf, Integer mesin) {
        ProduksiModel produksi = new ProduksiModel();
        produksi.getRequestUpdateItem().setIdRequestUpdateItem(idRequestUpdateItem);
        produksi.getPegawai().setIdPegawai(staf.getIdPegawai());
        produksi.getMesin().setIdMesin(Long.valueOf(mesin));
        MesinModel mesinSelected = mesinService.getMesinByIdMesin(Long.valueOf(mesin));
        mesinSelected.setKapasitas(mesinSelected.getKapasitas() - 1);
        produksiDB.save(produksi);
    }

    @Override
    public RequestUpdateItemModel getRequestUpdateItemByIdRequestUpdateItem(Long idRequestUpdateItem) {
        Optional<RequestUpdateItemModel> requestUpdateItem = requestUpdateItemDB.findByIdRequestUpdateItem(idRequestUpdateItem);
        if (requestUpdateItem.isPresent()) {
            return requestUpdateItem.get();
        }
        return null;
    }
}
