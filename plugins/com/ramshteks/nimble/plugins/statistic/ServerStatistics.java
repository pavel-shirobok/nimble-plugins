package com.ramshteks.nimble.plugins.statistic;

import com.ramshteks.lambda.LambdaMap;
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

	private LambdaMap<Integer, EventIO.EventHandler> eventHandlers;

	public ServerStatistics() {
		eventHandlers = new LambdaMap<>((Event e)->{});
		eventHandlers.add(Event.getHashCodeOfEventType(NimbleEvent.LOOP_START), this::onLoopStart);
		eventHandlers.add(Event.getHashCodeOfEventType(NimbleEvent.LOOP_END), this::onLoopEnd);
		eventHandlers.add(Event.getHashCodeOfEventType(TcpPacketEvent.TCP_PACKET_SEND), this::onPacketSend);
		eventHandlers.add(Event.getHashCodeOfEventType(TcpPacketEvent.TCP_PACKET_RECEIVE), this::onPacketReceive);
		eventHandlers.add(Event.getHashCodeOfEventType(TcpConnectionEvent.CONNECT), this::onConnect);
		eventHandlers.add(Event.getHashCodeOfEventType(TcpConnectionEvent.DISCONNECT), this::onDisconnect);

	}

	private void onLoopStart(Event event){
		long newTime = System.currentTimeMillis();
		loopStartTime = newTime;
		loopCount++;
		if(newTime - lastLoopTime > 2000){
			printStatistics();
			clear();
			lastLoopTime = newTime;
		}
	}

	private void onLoopEnd(Event event){
		accumLoopTime+=System.currentTimeMillis() - loopStartTime;
	}

	private void onPacketSend(Event packetEvent){
		sendPackets++;
		totalSendedPackets++;
	}

	private void onPacketReceive(Event packetEvent){
		receivedPackets++;
		totalReceivedPackets++;
	}

	private void onConnect(Event packetEvent){
		newConnectionCount++;
		totalConnectionsCount++;
	}

	private void onDisconnect(Event packetEvent){
		disconnectedCount++;
		totalConnectionsCount--;
	}

	@Override
	public void pushEvent(Event event) {
		eventHandlers.get(event.eventTypeHashCode()).handle(event);
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
