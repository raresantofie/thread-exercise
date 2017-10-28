package com.app.app.repository;

import com.app.app.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rares on 10/14/2017.
 */

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.id >= ?1 and u.id<= ?2")
    List<User> findUsersInRange(Long min, Long max);

    User findUserById(Long id);

    Long countDistinctByIdIsNotNull();
}
