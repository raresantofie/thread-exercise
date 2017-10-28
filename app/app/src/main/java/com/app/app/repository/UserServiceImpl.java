package com.app.app.repository;

import com.app.app.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rares on 10/14/2017.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findUsersInRange(Long min, Long max) {
        return userRepository.findUsersInRange(min,max);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public Long countById() {
        return userRepository.countDistinctByIdIsNotNull();
    }
}
