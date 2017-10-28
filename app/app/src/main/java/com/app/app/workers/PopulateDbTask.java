package com.app.app.workers;

import com.app.app.domain.User;
import com.app.app.repository.UserService;

/**
 * Created by Rares on 10/14/2017.
 */

public class PopulateDbTask implements Runnable {

    private UserService userService;
    private int value;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Starts writing " + value + " values in db");
        processSaveCommand();
        System.out.println(Thread.currentThread().getName() + " has stopped");
    }


    private void processSaveCommand() {
        String threadName = Thread.currentThread().getName();

        for (int i = 0; i < value; i++) {
            userService.save(new User("name " + i + " " + threadName, "surname " + i));
        }
    }

    public PopulateDbTask(int value, UserService userService, String name) {
        this.value = value;
        this.userService = userService;
        Thread.currentThread().setName(name);
    }
}
