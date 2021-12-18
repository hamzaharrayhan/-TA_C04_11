package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import apap.group.assignment.SIFACTORY.rest.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ProduksiServiceImpl implements ProduksiService {

    @Autowired
    private ProduksiDB produksiDB;

    @Autowired
    private ItemRestService itemRestService;

    @Override
    public List<ProduksiModel> getListOfProduksi() {
        return produksiDB.findAll();
    }

    public void deleteProduksi(ProduksiModel produksi) {
        produksiDB.delete(produksi);
    }

    @Override
    public List<ProduksiModel> getListProduksiByCheckItem() {
        List<ProduksiModel> listProduksi = getListOfProduksi();
        List<ItemModel> listItem = new ArrayList<>();

        // mengubah tipe data listItem values dari collection menjadi list
        Collection<List<ItemModel>> CollectionItem = itemRestService.retrieveListItem().values();
        for (List<ItemModel> lstItem : CollectionItem) {
            for (ItemModel item : lstItem) {
                listItem.add(item);
            }
        }

        // menghapus produksi jika memproduksi item yang sudah dihapus
        for (ProduksiModel produksi : listProduksi) {
            Boolean isItemExist = false;
            for (ItemModel item : listItem) {
                if (produksi.getIdItem().equals(item.getUuid())) {
                    isItemExist = true;
                }
            }
            if (isItemExist.equals(false)) {
                deleteProduksi(produksi);
            }
        }
        return listProduksi;
    }
}
