package com.dev.tanners.movieworld.db;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Used as a singletone device to work on a thread for db functionality
 */
public class MovieExecutor {
    // lock to help prevent race conditions
    private static final Object LOCK = new Object();
    // self static object
    private static MovieExecutor mMovieExecutor;
    // executor for disk
    private final Executor mDiskIO;

    /**
     * Constructor
     *
     * @param diskIO
     */
    private MovieExecutor(Executor diskIO) {
        this.mDiskIO = diskIO;
    }

    /**
     * Get new or existing instance of MovieExecutor
     * @return
     */
    public static MovieExecutor getInstance() {
        if (mMovieExecutor == null) {
            // used to prevent multiple threads from race conditions
            synchronized (LOCK) {
                mMovieExecutor = new MovieExecutor(
                        // get single thread executor
                        Executors.newSingleThreadExecutor()
                );
            }
        }
        // return instance
        return mMovieExecutor;
    }

    /**
     * Get disk executor
     *
     * @return
     */
    public Executor mDiskIO() {
        return mDiskIO;
    }
}