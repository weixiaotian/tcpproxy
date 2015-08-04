package proxyserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
	private static Logger log = LoggerFactory.getLogger(SessionManager.class);
	private Map<String,ClientSession> sessionMap = new ConcurrentHashMap<String, ClientSession>(8192);
	private static SessionManager instance = new SessionManager();
	
	private Thread checkThread;
	private SessionManager(){
		checkThread = new Thread(new CheckSessionAction());
		checkThread.setDaemon(true);
		checkThread.setName("Check Session Thread");
		checkThread.start();
	}
	
	public static SessionManager instance(){
		return instance;
	}
	
	public void removeSession(String account){
		sessionMap.remove(account);
	}
	
	public ClientSession getClientSession(String account){
		return sessionMap.get(account);
	}
	
	public void setClientSession(String account,ClientSession session){
		sessionMap.put(account, session);
	}
	
	class CheckSessionAction implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			Map<String,ClientSession> timeoutMap = new HashMap<String, ClientSession>();
			while(true){
				try{
					Thread.sleep(10000);//10秒检查一次
					timeoutMap.clear();
					
					for(Entry<String, ClientSession> kv : sessionMap.entrySet()){
						if(kv.getValue().checkTimeout()){
							timeoutMap.put(kv.getKey(), kv.getValue());
						}
					}
					
					for(Entry<String, ClientSession> kv : timeoutMap.entrySet()){
						sessionMap.remove(kv.getKey());
						kv.getValue().close();
						//TODO 通知业务层 清除在线
					}
					log.info(String.format("current session count is: %s", sessionMap.size()));
				}catch(Exception ex){
					log.error("CheckSessionAction error", ex);
				}
			}
		}
	}
}
