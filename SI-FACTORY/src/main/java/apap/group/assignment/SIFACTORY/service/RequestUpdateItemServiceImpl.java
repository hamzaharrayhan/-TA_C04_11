package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.MesinModel;
import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.MesinDB;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    ItemRestService itemRestService;

    @Override
    public List<RequestUpdateItemModel> getRequestUpdateItemList() {
        return requestUpdateItemDB.findAll();
    }

    @Override
    public void updateProduksiItem(ItemModel item, Integer jumlahStokDitambahkan, PegawaiModel pegawai, MesinModel mesin, Long idRequestUpdateItem) {
        ProduksiModel produksi = new ProduksiModel();
        java.util.Date date = new java.util.Date();
        produksi.setRequestUpdateItem(getRequestUpdateItemByIdRequestUpdateItem(idRequestUpdateItem));
        produksi.setPegawai(pegawai);
        produksi.setIdItem(item.getUuid());
        produksi.setIdKategori(itemRestService.getIdKategoriByKategori(item.getKategori()));
        produksi.setTambahanStok(jumlahStokDitambahkan);
        produksi.setTanggalProduksi(date);
        produksi.setMesin(mesin);
        mesin.setKapasitas(mesin.getKapasitas() - 1);
        System.out.println("tgl produksi yang mau disave = " + produksi.getTanggalProduksi());
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
