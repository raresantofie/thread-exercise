package com.app.app.repository;

import com.app.app.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rares on 10/14/2017.
 */

public interface UserService {
    User save(User user);
    List<User> findUsersInRange(Long min, Long max);
    User findById(Long id);
    Long countById();
}
