package apap.group.assignment.SIFACTORY.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
                .antMatchers("/pegawai/add").hasAuthority("ADMIN")
                .antMatchers("/request-update-item/viewall").hasAnyAuthority("STAFF_GUDANG", "STAFF_OPERASIONAL")
                .antMatchers("/item/update-request/**").hasAuthority("STAFF_GUDANG")
                .antMatchers("/item/propose-item").hasAuthority("FACTORY_MANAGER")
                .antMatchers("/delivery/list-delivery").hasAnyAuthority("STAFF_KURIR", "STAFF_OPERASIONAL")
                .antMatchers("/delivery/kirim/**").hasAuthority("STAFF_KURIR")
                .antMatchers("/delivery/assign-kurir/**").hasAuthority("STAFF_OPERASIONAL")
                .antMatchers("/item/update/**").hasAuthority("STAFF_GUDANG")
                .antMatchers("/pegawai/viewall").hasAnyAuthority("ADMIN", "FACTORY_MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder())
//                .withUser("admin").password(encoder().encode("admin8"))
//                .roles("ADMIN");
//    }

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailService).passwordEncoder(encoder());
    }
}