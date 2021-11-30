package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.RoleModel;
import apap.group.assignment.SIFACTORY.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDB roleDB;

    @Override
    public List<RoleModel> getListRole() {
        return roleDB.findAll();
    }
}
