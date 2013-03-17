package com.ramshteks.nimble.plugins.net;

import com.ramshteks.nimble.Event;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public class TcpPacketEvent extends Event {
	public static final String TCP_PACKET_SEND = "TcpPacketEvent#TCP_PACKET_SEND";
	public static final String TCP_PACKET_RECEIVE = "TcpPacketEvent#TCP_PACKET_RECEIVE";

	private TcpConnectionInfo target;

	private byte[] bytes;

	public TcpPacketEvent(String eventType, TcpConnectionInfo target, byte[] bytes) {
		super(eventType);
		this.target = target;
		this.bytes = bytes;
	}

	public TcpConnectionInfo target() {
		return target;
	}

	public byte[] bytes() {
		return bytes;
	}
}
