package com.example.javap;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsApp extends Application {
    public ExecutorService srv = Executors.newCachedThreadPool();
}
