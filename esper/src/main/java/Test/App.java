package Test;

import java.util.ArrayList;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.scopetest.SupportSubscriber;

import Development.LogEventDev;
import Development.jsonIO;
import Development.logReaderDev;
import NmapBeta.AckScanEventSubcriber;
import NmapBeta.PingScan;
import NmapBeta.SAACKScan;
import NmapBeta.SSSynScan;
import NmapCaseDevelopment.NetFilterLogCaller;
import NmapCaseDevelopment.NetFilterLogParser;
import NmapCaseDevelopment.PortScanDetection;
import NmapCaseDevelopment.TestClass;
import legacy.EventA;
import legacy.EventB;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */
		NetFilterLogParser log = new NetFilterLogParser();
		int size = log.getLogSize();

		logReaderDev jlog = new logReaderDev("/home/quang/journal.log");
		int size1 = jlog.size();

//		String StatementNet = "select message from NetFilterLogCaller";

//
//		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
//		EPAdministrator admin = engine.getEPAdministrator();
//
//		admin.getConfiguration().addEventType(NetFilterLogCaller.class);
//
//		EPStatement log2Statement = admin.createEPL(StatementNet);
//		log2Statement.addListener((newData, oldData) -> {
//			String line = (String) newData[0].get("message");
//			System.out.println(line);
//		});
//		for (int i = 0; i < size; ++i) {
//			engine.getEPRuntime().sendEvent(new NetFilterLogCaller(log, i));
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//

		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = engine.getEPAdministrator();
		PingScan lp = new PingScan();
		SSSynScan sS = new SSSynScan();
		SAACKScan sA = new SAACKScan();
		AckScanEventSubcriber ack = new AckScanEventSubcriber();

//
		admin.getConfiguration().addEventType(PingScan.class);
		admin.getConfiguration().addEventType(SSSynScan.class);
		admin.getConfiguration().addEventType(SAACKScan.class);
		admin.getConfiguration().addEventType(LogEventDev.class);
		admin.getConfiguration().addEventType(AckScanEventSubcriber.class);
		
//
//
		EPStatement icmpStatement = admin.createEPL(lp.getStatement());
		EPStatement sSStatement = admin.createEPL(sS.getStatement());
		EPStatement sAStatement = admin.createEPL(sA.getStatement());
		EPStatement AckStatement = admin.createEPL(ack.getStatement());

//
//		icmpStatement.setSubscriber(lp);
//		sSStatement.setSubscriber(sS);
//		sAStatement.setSubscriber(sA);

		AckStatement.setSubscriber(ack);
		
		for (int i1 = 0; i1 < size1; ++i1) {
			engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
		}
//		System.out.println("ping " + lp.getTotalOccur());
//		System.out.println("sS " + sS.getTotalOccur());
//		System.out.println("sA " + sA.getTotalOccur());
//		if ((lp.getFristOccur() != 0) && (lp.getFristOccur() < sS.getFristOccur())) {
//			if (sA.getTotalOccur() > sS.getTotalOccur()) {
//				System.out.println("possible scan with nmap (-sA)");
//			} else {
//				System.out.println("possible scan with nmap (-sS)");
//			}
//		} else {
//			System.out.println("possible scan with nmap (-PS)");
//		}

	}
}
