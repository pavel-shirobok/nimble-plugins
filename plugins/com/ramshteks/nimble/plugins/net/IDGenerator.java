package com.ramshteks.nimble.plugins.net;

import java.util.Hashtable;
import java.util.LinkedList;

/**
 * ...
 *
 * @author Pavel Shirobok (ramshteks@gmail.com)
 */
public class IDGenerator {
	private LinkedList<Integer> freeIDList;
	private Hashtable<Integer, Boolean> idToStatus;

	public IDGenerator(int min, int max){
		idToStatus = new Hashtable<>();
		freeIDList = new LinkedList<>();

		for(int id = min; id < max; id++){
			updateAllocateStatus(id, false);
			freeIDList.add(id);
		}
	}

	public synchronized int nextID(){
		if(freeIDList.isEmpty()){
			throw new IndexOutOfBoundsException("No more free id.");
		}

		int id = freeIDList.pollFirst();
		updateAllocateStatus(id, true);

		return id;
	}

	public synchronized void free(int id){
		if(freeIDList.contains(id)){
			throw new RuntimeException("This id='"+id+"' already free");
		}

		if(!idToStatus.containsKey(id)){
			throw new RuntimeException("This id='"+id+"' out of bounds");
		}

		freeIDList.addLast(id);
		updateAllocateStatus(id, false);
	}

	private void updateAllocateStatus(int id, boolean allocated){
		idToStatus.put(id, allocated);
	}
}
