package NmapBeta;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

public class HorizontalScanCounterSubscriber{

	private ArrayList<String>Port=new ArrayList<String>();
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return " insert into HorizontalScan(destPt,destIP,srcIP) select destPt as desPt, "
        		+ "destIP as destI, srcIP as srcIP from PortScan.win:keepall() ";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, String> eventMap) {

        // count when there are distinct value
        String destPt = (String) eventMap.get("desPt");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] DestPort " + destPt);
        sb.append("\n---------------------------------");
        Port.add(destPt);
//        System.out.println(sb.toString());
    }
}