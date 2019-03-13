package NmapBeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Development.LogEventDev;

import java.util.Map;

public class UdpScanEventSubscriber {

	private static Logger LOG = LoggerFactory.getLogger(UdpScanEventSubscriber.class);

	/**
	 * {@inheritDoc}
	 */
	public String getStatement() {

		// every at the top is messed up, put it in the first event so that it triggers
		// at every SYN; if put as wrapper of the pattern it will ignore everything
		String UdpScanEventExpression = "select EventA, EventB " + "from pattern [ "
				+ "              every EventA = LogEventDev(proto = 'UDP')                "
				+ "                 -> EventB = LogEventDev(proto = 'ICMP'                "
				+ "                                        and srcIP = EventA.destIP       "
				+ "                                        and destIP = EventA.srcIP	)      " + "             ]";

		return UdpScanEventExpression;
	}

	/**
	 * Listener method called when Esper has detected a pattern match.
	 */
	public void update(Map<String, LogEventDev> eventMap) {

		// Event A
		LogEventDev EventA = (LogEventDev) eventMap.get("EventA");
		// Event B
		LogEventDev EventB = (LogEventDev) eventMap.get("EventB");

		StringBuilder sb = new StringBuilder();
		sb.append("--------------------------------------------------");
		sb.append("\n- UDP Scan detected " + EventA + "," + EventB);
		sb.append("\n- UDP Scan detected " + EventA.getMessage());
		sb.append("\n- UDP Scan detected " + EventB.getMessage());
		sb.append("\n--------------------------------------------------");

//		System.out.println(sb.toString());
	}
}