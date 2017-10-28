package com.app.app.workers;

import com.app.app.domain.User;
import com.app.app.repository.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class ReadDbTask implements Runnable {


    private UserService userService;
    private static AtomicInteger atomicId = new AtomicInteger(0);
    private Long countValue;
    static Semaphore semaphore = new Semaphore(4);


    public ReadDbTask(Long countValue, UserService userService, String name) {
        this.countValue = countValue;
        this.userService = userService;
        Thread.currentThread().setName(name);
    }

    @Override
    public void run() {
        try {
            processReadWriteCommand();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " has stopped");
    }

    private void processReadWriteCommand() throws InterruptedException, IOException {
        String threadName = Thread.currentThread().getName();
        String operationStartMessage = "Started r-w with countValue = " + countValue;
        String FILENAME = "F:\\filename.txt";

        Files.write(Paths.get(FILENAME), operationStartMessage.getBytes(), StandardOpenOption.APPEND);

        System.out.println(Thread.currentThread().getName() + " reads id " + atomicId.longValue());
        while (atomicId.longValue() <= countValue) {

            String atomicID = "id is : + " + atomicId.longValue() + " \n";
            User user = userService.findById(Long.valueOf(atomicId.getAndIncrement()));
            semaphore.acquire();

            System.out.println("Available spots :  " + semaphore.availablePermits());
            try {
                Files.write(Paths.get(FILENAME), atomicID.getBytes(), StandardOpenOption.APPEND);

                String content = user != null ? user.toString() + "\n" : "no content";
                Files.write(Paths.get(FILENAME), content.getBytes(), StandardOpenOption.APPEND);

            } finally {
                semaphore.release();
            }
            System.out.println(Thread.currentThread().getName() + " has stopped writing");

        }
    }

    /**
     * Profiling :
     * jProfiler
     *
     * mecanism lock
     * endpoint cancel job
     *
     *
     */

}
