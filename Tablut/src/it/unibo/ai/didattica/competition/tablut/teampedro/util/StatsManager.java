package it.unibo.ai.didattica.competition.tablut.teampedro.util;

public class StatsManager {

	private static StatsManager instance = null;

	private long start;
	private long end;
	private long totalPart;
	private int expandedNodes;

	
	private long startTotal;
	private long endTotal;
	private long totalTime;
	private long totalMemory;

	private StatsManager() {
		this.start = 0;
		this.end = 0;
		this.totalPart = 0;
		this.expandedNodes = 0;
		
		this.startTotal = 0;
		this.endTotal = 0;
		this.totalTime = 0;
		this.totalMemory = 0;
	}

	public static StatsManager getInstance() {
		if (instance == null) {
			instance = new StatsManager();
		}

		return instance;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	public long getTotalPart() {
		return totalPart;
	}
	
	public void setTotalPart(long totalPart) {
		this.totalPart = totalPart;
	}
	
	public void setStartTotal(long startTotal) {
		this.startTotal = startTotal;
	}
	
	public long getStartTotal() {
		return startTotal;
	}
	
	public void setEndTotal(long endTotal) {
		this.endTotal = endTotal;
	}
	
	public long getEndTotal() {
		return endTotal;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	
	public long setTotalMemory() {
		return totalMemory;
	}
	
	public void getTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public int getExpandedNodes() {
		return expandedNodes;
	}

	public long getOccupiedMemoryInMB() {
		long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return usedMemory / (1024 * 1024);
	}

	public void reset() {
		this.start = 0;
		this.end = 0;
		this.expandedNodes = 0;
	}

	public void incrementExpandedNodes() {
		this.expandedNodes++;
	}

	public void printResults() {
		String results = "-----RESULTS-----\n";
		this.totalPart = totalPart + (this.getEnd() - this.getStart());
		//results += "Tempo: " + (this.getEnd() - this.getStart())/1000 + " secondi\n";
		results += "Tempo: "+ totalPart/1000 +" secondi\n";
		results += "Nodi espansi: " + this.getExpandedNodes() + "\n";
		results += "Memoria attualmente occupata: " + this.getOccupiedMemoryInMB() + " MB\n";
		this.totalMemory = totalMemory + this.getOccupiedMemoryInMB();
		results += "\n";

		System.out.println(results);
	}
	
	public void printTotalResults() {
		String results = "-----TOTAL RESULTS-----\n";
		this.totalTime = this.getEndTotal() - this.getStartTotal();
		results += "Tempo totale: " +this.totalTime/60000+" minuti\n";
		results += "Memoria totale: " +this.totalMemory+" MB\n";
		
		System.out.println(results);
	}
}
