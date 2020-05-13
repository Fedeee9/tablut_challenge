package it.unibo.ai.didattica.competition.tablut.teampedro.util;

public class StatsManager {

	private static StatsManager instance = null;

	private long start;
	private long end;
	private int expandedNodes;
	private long occupiedMemory;
	
	private long totalTime;
	private long totalMemory;

	// not used
	private long minFreeMemory;
	private int numMaxCache;
	private int currentCache;

	private StatsManager() {
		this.start = 0;
		this.end = 0;
		this.expandedNodes = 0;
		this.occupiedMemory = 0;
		
		this.totalTime = 0;
		this.totalMemory = 0;

		this.numMaxCache = 0;
		this.currentCache = 0;
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
		this.occupiedMemory = 0;

	}

	public void incrementExpandedNodes() {
		this.expandedNodes++;
	}

	public void printResults() {
		String results = "-----RESULTS-----\n";
		results += "Tempo: " + (this.getEnd() - this.getStart()) + " millisecondi\n";
		this.totalTime = totalTime + (this.getEnd() - this.getStart());
		results += "Nodi espansi: " + this.getExpandedNodes() + "\n";
		results += "Memoria attualmente occupata: " + this.getOccupiedMemoryInMB() + " MB\n";
		this.totalMemory = totalMemory + this.getOccupiedMemoryInMB();
		results += "\n";

		System.out.println(results);
	}
	
	public void printTotalResults() {
		String results = "-----TOTAL RESULTS-----\n";
		results += "Tempo totale: " +this.totalTime/60000+" secondi\n";
		results += "Memoria totale: " +this.totalMemory+" MB\n";
		
		System.out.println(results);
	}

	// not used

	public void update(String arg) {
		switch (arg) {
		case "start": {
			this.start = System.currentTimeMillis();
			this.occupiedMemory = Runtime.getRuntime().totalMemory();
			this.minFreeMemory = Runtime.getRuntime().totalMemory();
			break;
		}
		case "end": {
			this.end = System.currentTimeMillis();
			break;
		}
		case "space": {
			this.expandedNodes++;
			long freeMemory = Runtime.getRuntime().freeMemory();
			if (freeMemory < minFreeMemory)
				minFreeMemory = freeMemory;
			break;
		}
		case "reset": {
			this.end = 0;
			this.minFreeMemory = 0;
			this.start = 0;
			this.occupiedMemory = 0;
			this.expandedNodes = 0;

			break;
		}
		case "cache": {
			currentCache++;
			break;
		}
		case "resetCache": {
			System.out.println("resetto");
			if (currentCache > numMaxCache) {
				System.out.println("new max");
				numMaxCache = currentCache;
			}
			currentCache = 0;

			break;
		}
		default: {
			System.err.println("stat " + arg + " not recognised");
		}
		}
	}

}
