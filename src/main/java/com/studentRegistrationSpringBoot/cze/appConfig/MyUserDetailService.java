package com.studentRegistrationSpringBoot.cze.appConfig;

import com.studentRegistrationSpringBoot.cze.dao.UserDao;
import com.studentRegistrationSpringBoot.cze.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService  {

    private final UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User Not Found")
        );

        return new MyUserDetail(user);

    }
}
