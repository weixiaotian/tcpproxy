package proxyserver.protocol;

public class ProtocolHeader {
	private short length;
	private byte mark;
	private byte accountLength;
	private String userAccount;
	private int cmd;
	private short seq;
	private byte offset;
	private byte flag;
	private short statusCode;
	
	public byte getMark() {
		return mark;
	}
	public void setMark(byte mark) {
		this.mark = mark;
	}
	public short getLength() {
		return length;
	}
	public void setLength(short length) {
		this.length = length;
	}
	public byte getAccountLength() {
		return accountLength;
	}
	public void setAccountLength(byte accountLength) {
		this.accountLength = accountLength;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(short seq) {
		this.seq = seq;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(byte offset) {
		this.offset = offset;
	}
	public byte getFlag() {
		return flag;
	}
	public void setFlag(byte flag) {
		this.flag = flag;
	}
	public short getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(short statusCode) {
		this.statusCode = statusCode;
	}
	
}
