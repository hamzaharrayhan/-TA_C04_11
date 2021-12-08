package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.RequestUpdateItemModel;
import apap.group.assignment.SIFACTORY.repository.RequestUpdateItemDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RequestUpdateItemServiceImpl implements RequestUpdateItemService{

    @Autowired
    RequestUpdateItemDB requestUpdateItemDB;

    @Override
    public List<RequestUpdateItemModel> getRequestUpdateItemList() {
        return requestUpdateItemDB.findAll();
    }
}
