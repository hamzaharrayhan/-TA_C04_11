//package apap.group.assignment.SIFACTORY.security;
//
//import apap.group.assignment.SIFACTORY.model.PegawaiModel;
//import apap.group.assignment.SIFACTORY.repository.PegawaiDB;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private PegawaiDB pegawaiDB;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        PegawaiModel pegawai = pegawaiDB.findByUsername(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(pegawai.getRole().getNamaRole()));
//        return new User(pegawai.getUsername(), pegawai.getPassword(), grantedAuthorities);
//    }
//}
