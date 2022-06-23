package org.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class KubeClient {
	private static CyclicBarrier barrier = new CyclicBarrier(2);
	public static void main(String[] args) {
	    try(Socket socket = new Socket("127.0.0.1", 9091)){      
	    	DataOutputStream dout=new DataOutputStream(socket.getOutputStream());  
	    	Gson gson = new Gson();
	    	KubeCommand cmd = new KubeCommand();
	    	cmd.setAdminCommand("kubectl get nodes -o JSON");
	    	cmd.setClientID("MyDesktop");
	    	cmd.setCmdType("NODE");
	    	String jsonString = gson.toJson(cmd);
	    	log.info("json command {}",jsonString);
	    	dout.writeBytes(jsonString);
	       	StreamGobbler streamRd = new StreamGobbler(socket.getInputStream(),barrier);
	       	ExecutorService executor = Executors.newSingleThreadExecutor();
	       	executor.submit(streamRd);
	       	barrier.await();
	     }catch(IOException | InterruptedException | BrokenBarrierException ex){
	    	 log.error("Erroring {}",ex);
	     } 

	}
}
