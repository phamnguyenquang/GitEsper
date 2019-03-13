package NmapBeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

public class ClosedPortCounterSubscriber {

	private ArrayList<String> Port = new ArrayList<String>();
	private ArrayList<String> srcIPList = new ArrayList<String>();

	public String getStatement() {

		// Example of simple EPL with a Time Window
		return "select count(distinct destPt) as val,destPt, srcIP from closed_portSyn.win:length(30) "
				+ "group by destIP, srcIP ";
	}

	/**
	 * Listener method called when Esper has detected a pattern match.
	 */
	public void update(Object[] eventMap) {

		// count when there are distinct value
		String port = eventMap[1].toString();
		String IP = eventMap[2].toString();
		boolean portExist = false;
		boolean IPExist = false;
		StringBuilder sb = new StringBuilder();
		sb.append("---------------------------------");
		sb.append("\n- [MONITOR] COUNT " + port);
		sb.append("\n---------------------------------");
//        System.out.println(sb.toString());
//		System.out.println(port);
//		System.out.println(IP);
//		System.out.println(eventMap[0].toString());
		for (int i = 0; i < Port.size(); ++i) {
			if (Port.get(i).equals(port)) {
				portExist = true;
			}
		}
		if (!portExist) {
			Port.add(port);
		}
		for (int i = 0; i < srcIPList.size(); ++i) {
			if (srcIPList.get(i).equals(IP)) {
				IPExist = true;
			}
		}
		if (!IPExist) {
			srcIPList.add(IP);
		}
	}

	public void print() {
		System.out.println("Vertical Scan Details");
		System.out.println("Port List");
		for (int i = 0; i < Port.size(); ++i) {
			System.out.println(Port.get(i));
		}
		System.out.println("Source IP List");
		for (int i = 0; i < srcIPList.size(); ++i) {
			System.out.println(srcIPList.get(i));
		}
	}
}