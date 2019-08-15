package org.leecp.loginwithdb.service.serviceImpl;

import org.leecp.loginwithdb.dto.User;
import org.leecp.loginwithdb.repository.UserRepository;
import org.leecp.loginwithdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username) ;
    }

    @Override
    public void saveUser(User user) {
        /**
         * 加密
         */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }
}
