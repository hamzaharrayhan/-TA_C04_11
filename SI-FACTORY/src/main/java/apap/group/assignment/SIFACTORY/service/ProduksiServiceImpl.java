package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.ProduksiModel;
import apap.group.assignment.SIFACTORY.repository.ProduksiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.List;

@Service
@Transactional
public class ProduksiServiceImpl implements ProduksiService {

    @Autowired
    private ProduksiDB produksiDB;

    @Override
    public List<ProduksiModel> getListOfProduksi() {
        return produksiDB.findAll();
    }
}
