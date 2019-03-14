package NmapBeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Development.LogEventDev;

import java.util.Map;

public class AckScanEventSubscriber {

	/**
	 * Logger
	 */
	private static Logger LOG = LoggerFactory.getLogger(AckScanEventSubscriber.class);
	private int count = 0;

	/**
	 * If 2 consecutive temperature events are greater than this - issue a warning
	 */
	private static final String WARNING_EVENT_THRESHOLD = "400";

	/**
	 * {@inheritDoc}
	 */
	public String getStatement() {

		String warningEventExpression1 = "select temp1, temp2 from pattern [ every (temp1 = TemperatureEvent(temperature > 300) -> temp2 = TemperatureEvent(temperature < 1*temp1.temperature) ) ]";
		// return "select avg(temperature) as avg_val from
		// TemperatureEvent.win:time_batch(5 sec)";

		// every at the top is messed up, put it in the first event so that it triggers
		// at every SYN; if put as wrapper of the pattern it will ignore everything
        String AckScanEventExpression = "" +
                "insert into PortScan (A, B, C, srcIP, destIP,srcPt, destPt) " +
                "select EventA, EventB, EventB, EventA.srcIP as srcIP, EventA.destIP as destIP, EventA.srcPt as srcPt, EventA.dstPt as destPt "
                + "from pattern [ "
                + "              every EventA = LogEventDev(proto = 'TCP' and flag = ' ACK ')                "
                + "                 -> EventB = LogEventDev(proto = 'TCP' and flag = ' RST '              "
                + "                                                       and srcPt = EventA.dstPt    		  "
                + "                                                        and dstPt = EventA.srcPt     	   "
                + "                                                         and srcIP = EventA.destIP            "
                + "                                                          and destIP = EventA.srcIP	)        "
+ "             ]";
		return AckScanEventExpression;
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
		sb.append("\n- ACK Scan detected " + EventA + "," + EventB + ",");
		sb.append("\n- ACK Scan detected EventA " + EventA.getMessage());
		sb.append("\n- ACK Scan detected EventB " + EventB.getMessage());
		sb.append("\n--------------------------------------------------");

//		System.out.println(sb.toString());
	}
}