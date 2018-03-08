package com.cinek.rozlitch.repositories;

import com.cinek.rozlitch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Cinek on 09.11.2017.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

}
