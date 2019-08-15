package org.leecp.loginwithdb.repository;

import org.leecp.loginwithdb.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

}
