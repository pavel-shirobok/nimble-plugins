package com.ramshteks.nimble.plugins.statistic;

import com.ramshteks.nimble.Event;
import com.ramshteks.nimble.EventIO;
import com.ramshteks.nimble.NimbleEvent;
import com.ramshteks.nimble.plugins.net.TcpConnectionEvent;
import com.ramshteks.nimble.plugins.net.TcpPacketEvent;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public class ServerStatistics implements EventIO.EventReceiver {
	private int newConnectionCount = 0;
	private int disconnectedCount = 0;

	private int sendPackets = 0;
	private int receivedPackets = 0;

	private int totalSendedPackets = 0;
	private int totalReceivedPackets = 0;
	private int totalConnectionsCount = 0;

	private int loopCount = 0;
	private long accumLoopTime = 0;
	private long loopStartTime;

	private long lastLoopTime = System.currentTimeMillis();

	@Override
	public void pushEvent(Event event) {
		if(Event.equalHash(event, NimbleEvent.LOOP_START)){
			long newTime = System.currentTimeMillis();
			loopStartTime = newTime;
			loopCount++;
			if(newTime - lastLoopTime > 2000){
				printStatistics();
				clear();
				lastLoopTime = newTime;
			}
		}
		if(Event.equalHash(event, NimbleEvent.LOOP_END)){
			accumLoopTime+=System.currentTimeMillis() - loopStartTime;
		}
		if(Event.equalHash(event, TcpPacketEvent.TCP_PACKET_SEND)){
			sendPackets++;
			totalSendedPackets++;
		}
		if(Event.equalHash(event, TcpPacketEvent.TCP_PACKET_RECEIVE)){
			receivedPackets++;
			totalReceivedPackets++;
		}
		if(Event.equalHash(event, TcpConnectionEvent.CONNECT)){
			newConnectionCount++;
			totalConnectionsCount++;
		}
		if(Event.equalHash(event, TcpConnectionEvent.DISCONNECT)){
			disconnectedCount++;
			totalConnectionsCount--;
		}
	}

	private void clear() {
		receivedPackets = 0;
		sendPackets = 0;
		newConnectionCount = 0;
		disconnectedCount = 0;
		accumLoopTime = 0;
		loopCount = 0;
	}

	private void printStatistics() {
		System.out.println("For current second: ");
		System.out.println("*         connected = " + newConnectionCount);
		System.out.println("*      disconnected = " + disconnectedCount);
		System.out.println("*          received = " + receivedPackets);
		System.out.println("*            sended = " + sendPackets);
		System.out.println("* total connections = " + totalConnectionsCount);
		System.out.println(" ---------------------");
		System.out.println("*        loop count = " + loopCount);
		System.out.println("*     avr.loop time = " + (accumLoopTime/loopCount));
		System.out.println();

	}
}
