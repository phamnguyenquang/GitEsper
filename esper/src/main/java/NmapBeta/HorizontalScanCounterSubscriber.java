package NmapBeta;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class HorizontalScanCounterSubscriber{

    /** Logger */


    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return " insert into VerticalScan(destPt,destIP) select destPt as desPt, destIP as destIp from closed_portSyn.win:keepall() ";
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

        System.out.println(sb.toString());
    }
}