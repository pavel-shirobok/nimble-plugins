package com.ramshteks.nimble.plugins.logger;

import com.ramshteks.nimble.Event;
import com.ramshteks.nimble.EventIO.EventReceiver;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public abstract class LoggerPlugin implements EventReceiver{

	@Override
	public void pushEvent(Event event) {
		if(Event.equalHash(event, LoggerEvent.LOG_MESSAGE)){
			processLogEvent((LoggerEvent)event);
		}
	}

	private void processLogEvent(LoggerEvent loggerEvent) {
		String formattedMessage = formatLoggerEvent(loggerEvent);
		write(loggerEvent, formattedMessage);
	}

	protected abstract String formatLoggerEvent(LoggerEvent loggerEvent);
	protected abstract void write(LoggerEvent event, String formattedMessage);
}
