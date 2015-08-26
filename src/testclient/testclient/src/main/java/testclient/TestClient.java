package testclient;

import testclient.utils.ClientConfig;

public class TestClient {
	public static void main(String[] args) {
		try {
			ClientConfig.initialize("clientconfig.properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String account = "test";
		ProxyClient client = new ProxyClient(account);
		try {
			client.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
