package org.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private CyclicBarrier barrier;

    public StreamGobbler(InputStream inputStream,CyclicBarrier i_Barrier) {
        this.inputStream = inputStream;
        this.barrier = i_Barrier;
    }

    @Override
    public void run() {
    	log.info("About to consume command output");
    	final StringBuilder outputJson = new StringBuilder();
    	try(BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream))){
    		reader.lines().forEach(line -> {
    			outputJson.append(line);
    		});
    		log.info("Command output {}",outputJson.toString());
    		this.barrier.await();
    	}catch(IOException | InterruptedException | BrokenBarrierException ex) {
    		log.error("Exception occured while running command {}",ex);
    	}
    }
}