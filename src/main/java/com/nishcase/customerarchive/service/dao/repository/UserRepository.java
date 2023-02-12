package com.nishcase.customerarchive.service.dao.repository;

import com.nishcase.customerarchive.service.dao.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Boolean existsByUsernameOrEmail (String username, String email);

}
