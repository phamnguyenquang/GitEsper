package Test;

import java.util.ArrayList;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.scopetest.SupportSubscriber;

import Background.CommandExecutor;
import Development.LogEventDev;
import Development.jsonIO;
import Development.logReaderDev;
import NmapBeta.UdpScanEventSubscriber;
import NmapBeta.AckScanEventSubscriber;
import NmapBeta.VerticalScanPortCounterSubscriber;
import NmapBeta.HorizontalScanCounterSubscriber;
import NmapBeta.PingScan;
import NmapBeta.PortSpikeHorizontalSubscriber;
import NmapBeta.SynScanClosedPortEventSubscriber;
import NmapBeta.SynScanEventSubscriber;
import legacy.EventA;
import legacy.EventB;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */

		CommandExecutor cmd = new CommandExecutor();
		cmd.startCommand("whoami");
		String user = cmd.getResult();
		
		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = engine.getEPAdministrator();

		AckScanEventSubscriber ack = new AckScanEventSubscriber();
		VerticalScanPortCounterSubscriber closedP = new VerticalScanPortCounterSubscriber();
		HorizontalScanCounterSubscriber horz = new HorizontalScanCounterSubscriber();
		UdpScanEventSubscriber udp = new UdpScanEventSubscriber();
		PortSpikeHorizontalSubscriber portS = new PortSpikeHorizontalSubscriber();
		SynScanClosedPortEventSubscriber synC = new SynScanClosedPortEventSubscriber();
		SynScanEventSubscriber synS = new SynScanEventSubscriber();



		admin.getConfiguration().addEventType(LogEventDev.class);
		admin.getConfiguration().addEventType(VerticalScanPortCounterSubscriber.class);
		admin.getConfiguration().addEventType(AckScanEventSubscriber.class);
		admin.getConfiguration().addEventType(UdpScanEventSubscriber.class);
		admin.getConfiguration().addEventType(HorizontalScanCounterSubscriber.class);
		admin.getConfiguration().addEventType(PortSpikeHorizontalSubscriber.class);
		admin.getConfiguration().addEventType(SynScanClosedPortEventSubscriber.class);
		admin.getConfiguration().addEventType(SynScanEventSubscriber.class);



		EPStatement AckStatement = admin.createEPL(ack.getStatement());
		EPStatement ClosedPortStatement = admin.createEPL(closedP.getStatement());
		EPStatement HorizontalScanStatement = admin.createEPL(horz.getStatement());
		EPStatement UDPStatement = admin.createEPL(udp.getStatement());
		EPStatement PortSpikeStatement = admin.createEPL(portS.getStatement());
		EPStatement SynClosedStatement = admin.createEPL(synC.getStatement());
		EPStatement SynScanStatement = admin.createEPL(synS.getStatement());




		AckStatement.setSubscriber(ack);
		ClosedPortStatement.setSubscriber(closedP);
		HorizontalScanStatement.setSubscriber(horz);
		UDPStatement.setSubscriber(udp);
		PortSpikeStatement.setSubscriber(portS);
		SynClosedStatement.setSubscriber(synC);
		SynScanStatement.setSubscriber(synS);


		logReaderDev jlog;
//		for (int j = 0; j < 1; ++j) {
//			jlog = new logReaderDev("/home/"+user+"/journal.log");
//			int size1 = jlog.size();
//
//			for (int i1 = 0; i1 < size1; ++i1) {
//				engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
//			}
//			
//			
//			try {
//				Thread.sleep(60000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		jlog = new logReaderDev("/home/"+user+"/journal.log");
		int size1 = jlog.size();

		for (int i1 = 0; i1 < size1; ++i1) {
			engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
		}

		closedP.print();
		System.out.println("------------------------------");
		portS.print();

	}
}
