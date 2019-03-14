package NmapBeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;



public class PortSpikeHorizontalSubscriber {

    /** Logger */
	private ArrayList<String>IPAddress= new ArrayList<String>();
	private ArrayList<String>Port=new ArrayList<String>();
	private ArrayList<String>srcIPList = new ArrayList<String>();
    /**
     * {@inheritDoc}
     */
    public String getStatement() {

        // Example of simple EPL with a Time Window
        return " select destPt, destIP, srcIP from HorizontalScan.win:length_batch(10) " +
                " group by destPt having count(destPt) >= 2 ";
    }

    /**
     * Listener method called when Esper has detected a pattern match.
     */
    public void update(Map<String, String> eventMap) {

        // count when there are distinct value
        String destPt = (String) eventMap.get("destPt");
        String destIP = (String) eventMap.get("destIP");
        String srcIP = (String) eventMap.get("srcIP");
        boolean exist = false;

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] destPt " + destPt);
        sb.append("\n- [MONITOR] destIP " + destIP);
        sb.append("\n---------------------------------");

//       System.out.println(sb.toString());
        IPAddress.add(destIP);
        Port.add(destPt);
        for(int i=0;i<srcIPList.size();++i)
        {
        	if(srcIPList.get(i).equals(srcIP)) {
        		exist = true;
        	}
        }
        if(!exist) {
        	srcIPList.add(srcIP);
        }
    }
    
    public void print()
    {
    	System.out.println("Horizontal Scan details");
    	System.out.println("List of scan source");
    	for(int i=0;i<srcIPList.size();++i)
    	{
    		System.out.println(srcIPList.get(i));
    	}
    	System.out.println("Target in form IP:port");
    	for(int i = 0; i<IPAddress.size();++i)
    	{
    		System.out.println(IPAddress.get(i) +": "+ Port.get(i));
    	}
    }
}