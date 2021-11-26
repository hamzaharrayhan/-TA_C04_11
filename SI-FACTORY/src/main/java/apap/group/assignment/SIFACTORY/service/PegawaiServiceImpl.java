package apap.group.assignment.SIFACTORY.service;

import apap.group.assignment.SIFACTORY.model.PegawaiModel;
import apap.group.assignment.SIFACTORY.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

    @Autowired
    private PegawaiDB pegawaiDB;

    @Override
    public PegawaiModel addPegawai(PegawaiModel pegawai) {
        String pass = encrypt(pegawai.getPassword());
        pegawai.setPassword(pass);
        String[] arrOfUsername = pegawai.getUsername().split(",", 5);
        String usernameAdmin = (String) Array.get(arrOfUsername, arrOfUsername.length-1);
        System.out.println("usernameAdmin di impl = " + usernameAdmin);
        pegawai.setUsername(usernameAdmin);
        return pegawaiDB.save(pegawai);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public List<PegawaiModel> getListOfPegawai() {
        return pegawaiDB.findAll();
    }

    @Override
    public void deletePegawai(PegawaiModel pegawai) {
        pegawaiDB.delete(pegawai);
    }

    @Override
    public PegawaiModel getPegawaiByUsername(String username) {
        PegawaiModel pengguna = pegawaiDB.findByUsername(username);
        return pengguna;
    }

    @Override
    public void addCounter(PegawaiModel pegawai) {
        PegawaiModel admin = pegawaiDB.findByUsername(pegawai.getUsername());
//        System.out.println("username admin counter = " + admin.getUsername());
        if (admin.getCounter() == null) {
            admin.setCounter(1);
        } else {
            admin.setCounter(admin.getCounter() + 1);
        }
    }
}
