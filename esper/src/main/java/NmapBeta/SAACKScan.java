package NmapBeta;

import java.util.Map;

import Development.LogEventDev;

public class SAACKScan {
	private int count = 0;
	private boolean detected = false;
	private double firstOccur = 0;

	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize("
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('PROTO=TCP') and A.message.contains('ACK'), B as B.message.contains('PROTO=TCP')? and B.message.contains('RST')?)";
		return log2;
	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		detected = true;
		count += 1;
		if (count == 1) {
			firstOccur = LogEventDev1.getTime();
			System.out.println("SS " + firstOccur);
		} else if (count > 1) {
			StringBuffer sb = new StringBuffer();
		}

	}

	public double getFristOccur() {
		return firstOccur;
	}

	public boolean check() {
		return detected;
	}

	public int getTotalOccur() {
		return count;
	}
}
