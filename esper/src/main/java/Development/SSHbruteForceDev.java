package Development;

import java.util.Map;

import LogReader.Logtransform;

public class SSHbruteForceDev {
	private int count = 0;
	private int oldCount = 0;
	private int newCount = 0;

	public String getStatement() {
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('ssh'), B as B.message.contains('ssh') and (B.time - A.time >= 1))";
		return log2;
	}

	public void update(Map<String, LogEventDev> Eventmap) {
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		count += 1;
		if (count == 25) {
			oldCount += count;
			count = 0;
			StringBuffer sb = new StringBuffer();
			sb.append("multiple ssh login attempt failed.");
			System.out.println(sb.toString());
			System.out.println("number of attempt since boot: " + oldCount);
		}
	}
}
