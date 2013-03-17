package com.ramshteks.nimble.plugins.net;

import java.io.*;
import java.net.*;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public class TcpReceptor extends Receptor implements Runnable {

	private boolean isSocketBind = false;
	private ServerSocket socket;

	public TcpReceptor() {}

	@Override
	public void startBinding() throws IOException {
		if(isSocketBind){
			throw new IOException("Address already bind");
		}
		isSocketBind = true;

		socket = new ServerSocket(port, 0, inetAddress);
		createAndStartThread();
	}

	private void createAndStartThread() {
		Thread thread = new Thread(this);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

	@Override
	public void stopBinding() throws IOException {
		isSocketBind = false;
	}

	@Override
	public boolean bindStarted() {
		return isSocketBind;
	}

	@Override
	public void run() {
		Socket acceptedSocket;
		while (!socket.isClosed()) {
			try {
				acceptedSocket = socket.accept();
			} catch (Exception ex) {
				dispatchError(ex, "Accepting failed");
				break;
			}

			if(null != acceptedSocket){

				dispatchNewSocket(acceptedSocket);
			}
		}
	}
}
