package com.ramshteks.nimble.plugins.net;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public class TcpConnectionInfo {
	private int connection_id;

	public TcpConnectionInfo(int connection_id){

		this.connection_id = connection_id;
	}

	public int connection_id() {
		return connection_id;
	}

	@Override
	public String toString() {
		return "" + connection_id;
	}
}
