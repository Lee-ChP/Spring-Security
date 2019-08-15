package org.leecp.loginwithdb.service;

import org.leecp.loginwithdb.dto.User;

public interface UserService {
    User findByUserName(String username);
    void saveUser(User user);
}
