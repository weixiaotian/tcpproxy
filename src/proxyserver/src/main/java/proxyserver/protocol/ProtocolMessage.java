package proxyserver.protocol;

public class ProtocolMessage {
	private ProtocolHeader header;
	private byte[] body;
	
	public ProtocolHeader getHeader() {
		return header;
	}
	public void setHeader(ProtocolHeader header) {
		this.header = header;
	}
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
}
