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
import NmapCaseDevelopment.NetFilterLogCaller;
import NmapCaseDevelopment.NetFilterLogParser;
import NmapCaseDevelopment.PingScan;
import NmapCaseDevelopment.PortScanDetection;
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
//
		admin.getConfiguration().addEventType(NetFilterLogCaller.class);
		admin.getConfiguration().addEventType(PortScanDetection.class);
		admin.getConfiguration().addEventType(PingScan.class);
		admin.getConfiguration().addEventType(LogEventDev.class);
//
		SupportSubscriber subscriber = new SupportSubscriber();

//
		EPStatement log2Statement = admin.createEPL(lp.getStatement());
		System.out.println(lp.getStatement());
//
		log2Statement.setSubscriber(lp);

		for (int i1 = 0; i1 < size1; ++i1) {
			engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
			if (lp.check() == true) {
				break;
			}
		}

	}
}
