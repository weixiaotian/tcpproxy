package testclient.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import testclient.protocol.ProtocolMessage;

public class TransactionManager {
	private static Logger log = LoggerFactory.getLogger(TransactionManager.class);
	
	public static TransactionManager Instance = new TransactionManager();
	
	private Map<Integer,Transaction> session = new ConcurrentHashMap<Integer, Transaction>(4096);
	private AtomicInteger sequence = new AtomicInteger(0);
	private int MAX_CAPACITY = 256 * 1024;
	private int TxTimeoutSec = 90;
	
	private int getSeq(){
		if(sequence.get() >= Integer.MAX_VALUE){
			return 1;
		}
		return sequence.getAndIncrement();
	}
	
	public int addTransaction(ProtocolMessage request,RequestFuture future){
		if(session.size() > MAX_CAPACITY){
			throw new IllegalArgumentException("session size to large");
		}
		int seq = getSeq();
		session.put(seq, new Transaction(request, future));
		return seq;
	}
	
	public void removeTransaction(int seq){
		session.remove(seq);
	}
	
	private  class CheckTxTimeoutAction implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			Map<Integer,Transaction> timeoutSession = new HashMap<Integer, Transaction>();
			while(true){
				try{
					Thread.sleep(5 * 1000);
					timeoutSession.clear();
					for(Entry<Integer, Transaction> kv : session.entrySet()){
						if(kv.getValue().getSw().getSeconds()>TxTimeoutSec){
							timeoutSession.put(kv.getKey(), kv.getValue());
						}
					}
					
					for(Entry<Integer, Transaction> kv : timeoutSession.entrySet()){
						kv.getValue().getFuture().complete(new Exception("transaction timeout!"));;
						session.remove(kv.getKey());
					}
				}catch(Exception ex){
					log.error("CheckTxTimeoutAction error", ex);
				}
			}
		}
	}
}
