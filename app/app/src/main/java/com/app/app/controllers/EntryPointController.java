package com.app.app.controllers;

import com.app.app.domain.User;
import com.app.app.repository.UserService;
import com.app.app.workers.PopulateDbTask;
import com.app.app.workers.ReadDbCallableTask;
import com.app.app.workers.ReadDbTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Rares on 10/14/2017.
 */

@RestController
@RequestMapping("/entry")
public class EntryPointController {

    @Autowired
    private UserService userService;

    List<User> userList = new ArrayList<>();

    @GetMapping("/initDb")
    public void initDb() {
        System.out.println("initDb-starting");
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 1; i <= 20; i++) {
            Runnable worker = new PopulateDbTask(20, userService, "write in db thread ~ " + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    @GetMapping("/read-write")
    public void readAndWrite() {
        System.out.println("initDb - reading");
        Long countValue = userService.countById();
        ExecutorService executor = Executors.newFixedThreadPool(35);


        for (int i = 1; i <= 50; i++) {
            Runnable worker = new ReadDbTask(countValue, userService, "thread ~ " + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

    @GetMapping("/read-with-callable")
    public void readWithCallable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for(int i=0; i< 20; i++) {
            Future<List<User>> future = executorService.submit(new ReadDbCallableTask(userService));
            List<User> list = future.get();
            userList.addAll(list);
        }

        executorService.shutdown();
        System.out.println(userList.size());
    }

}
