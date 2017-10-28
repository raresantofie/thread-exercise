package com.app.app.workers;

import com.app.app.domain.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class WriteToFileTask implements Callable<ArrayList<User>> {

    @Override
    public ArrayList<User> call() throws Exception {
        return null;
    }

}
