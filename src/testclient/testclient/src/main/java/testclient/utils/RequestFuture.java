package testclient.utils;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import testclient.protocol.ProtocolMessage;

public class RequestFuture {
	LinkedList<EventHandler> events = new LinkedList<EventHandler>();
	private static Object syncObj = new Object();
	private AtomicBoolean complete = new AtomicBoolean(false);
	private int index = 0;
	
	private Executor executor = Executors.newFixedThreadPool(20, new ThreadFactory() {
		
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			index++;
			return new Thread(r, "RequestFuture#"+index);
		}
	});
	
	public void addHandler(EventHandler handler){
		if(complete.get() == true){
			return;
		}
		synchronized (syncObj) {
			if(!events.contains(handler)){
				events.add(handler);
			}
		}
	}
	
	public void complete(final ProtocolMessage result){
		complete.set(true);
		runHandlers(result);
	}
	
	public void complete(Exception ex){
		complete.set(true);
		runHandlers(ex);
	}
	
	public void runHandlers(final Exception ex){
		for(final EventHandler handler : events){
			executor.execute(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					handler.run(this, new EventResult(ex, null));
				}
			});
		}
	}
	
	public void runHandlers(final ProtocolMessage result){
		for(final EventHandler handler : events){
			executor.execute(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					handler.run(this, new EventResult(null, result));
				}
			});
		}
	}
	
	public static interface EventHandler{
		void run(Object sender,EventResult result);
	}
	
	public static class EventResult{
		private Exception ex;
		private ProtocolMessage msg;
		
		public EventResult(Exception ex,ProtocolMessage msg){
			this.ex = ex;
			this.msg = msg;
		}
		
		public Exception getEx() {
			return ex;
		}
		public ProtocolMessage getMsg() {
			return msg;
		}
	}
}
