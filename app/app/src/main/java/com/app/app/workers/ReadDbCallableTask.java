package com.app.app.workers;

import com.app.app.domain.User;
import com.app.app.repository.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadDbCallableTask implements Callable<List<User>> {


    private static AtomicInteger atomicId = new AtomicInteger(1);
    private List<User> users = new ArrayList<>();
    private UserService userService;

    public ReadDbCallableTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> call() throws Exception {
        while (atomicId.longValue() <= 400) {
            users.add(userService.findById(Long.valueOf(atomicId.getAndIncrement())));
        }
        return users;
    }
}
