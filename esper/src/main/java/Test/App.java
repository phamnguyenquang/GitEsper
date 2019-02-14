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
import NmapBeta.SynScanEventSubcriber;
import NmapBeta.SynScanEventSubscriberWithACK;
import NmapBeta.UdpScanEventSubscriber;
import NmapBeta.AckScanEventSubscriber;
import NmapBeta.PingScan;
import NmapBeta.SynScanClosedPort;
import NmapBeta.SynScanOpenPort;
import legacy.EventA;
import legacy.EventB;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */

		logReaderDev jlog = new logReaderDev("/home/quang/journal.log");
		int size1 = jlog.size();

		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = engine.getEPAdministrator();

		ArrayList<SynScanOpenPort> chainScan = new ArrayList<SynScanOpenPort>();
		SynScanEventSubcriber syn = new SynScanEventSubcriber();
		SynScanEventSubscriberWithACK synA = new SynScanEventSubscriberWithACK();
		AckScanEventSubscriber ack = new AckScanEventSubscriber();
		UdpScanEventSubscriber udp = new UdpScanEventSubscriber();

		String createSchemaSyn = "create schema syn_ack as(srcIP string, destIP string, srcPt string, dstPt string, Proto string, flag string) ";
		String createSchemaClosedPort = "create schema closed_port as(srcIP string, destIP string, srcPt string, dstPt string,flag string) ";

		String insertCPStatement = "insert into closed_port (srcIP,destIP, srcPt, dstPt, flag) select srcIP,destIP, srcPt, dstPt, flag from SynScanClosedPort";
		String insertSAStatement = "insert into syn_ack (srcIP,destIP, srcPt, dstPt, flag) select srcIP,destIP, srcPt, dstPt, flag from SynScanOpenPort";
		
		String selectSynACk = "select srcIP, destIP, dstPt from syn_ack ";
		String selectAckRst = "select srcIP, destIP, dstPt from closed_port ";

		admin.getConfiguration().addEventType(LogEventDev.class);
		admin.getConfiguration().addEventType(SynScanEventSubcriber.class);
		admin.getConfiguration().addEventType(AckScanEventSubscriber.class);
		admin.getConfiguration().addEventType(UdpScanEventSubscriber.class);
		admin.getConfiguration().addEventType(SynScanEventSubscriberWithACK.class);
		admin.getConfiguration().addEventType(SynScanOpenPort.class);
		admin.getConfiguration().addEventType(SynScanClosedPort.class);

		admin.createEPL(createSchemaSyn);
		admin.createEPL(createSchemaClosedPort);
		EPStatement selectsyn = admin.createEPL(selectSynACk);
		EPStatement selectackrst = admin.createEPL(selectAckRst);
		EPStatement synStatement = admin.createEPL(syn.getStatement());
		EPStatement AckStatement = admin.createEPL(ack.getStatement());
		EPStatement UDPStatement = admin.createEPL(udp.getStatement());
		EPStatement synAStatement = admin.createEPL(synA.getStatement());
		EPStatement insert = admin.createEPL(insertCPStatement);
		EPStatement SAinsert = admin.createEPL(insertSAStatement);
		EPStatement select1 = admin.createEPL(selectSynACk);

		synStatement.setSubscriber(syn);
		synAStatement.setSubscriber(synA);
		AckStatement.setSubscriber(ack);
		UDPStatement.setSubscriber(udp);
	
		insert.addListener((newData, oldData) -> {
			String srcIP = (String) newData[0].get("srcIP");
			String destIP = (String) newData[0].get("destIP");
			String srcPt = (String) newData[0].get("srcPt");
			String dstPt = (String) newData[0].get("dstPt");
			String flag = (String) newData[0].get("flag");
			System.out.println("insert closed port " + srcIP + " "+destIP);
		});
		SAinsert.addListener((newData, oldData) -> {
			String srcIP = (String) newData[0].get("srcIP");
			String destIP = (String) newData[0].get("destIP");
			String srcPt = (String) newData[0].get("srcPt");
			String dstPt = (String) newData[0].get("dstPt");
			String flag = (String) newData[0].get("flag");
			System.out.println("insert syn ack " + srcIP+ " "+destIP);
		});

		for (int i1 = 0; i1 < size1; ++i1) {
			engine.getEPRuntime().sendEvent(new LogEventDev(jlog, i1));
		}
		for(int i = 0; i< syn.getOccuredEvent().size();++i)
		{
			engine.getEPRuntime().sendEvent(new SynScanOpenPort(syn.getOccuredEvent().get(i)));
		}
		for(int i = 0; i< synA.getOccuredEvent().size();++i)
		{
			engine.getEPRuntime().sendEvent(new SynScanClosedPort(synA.getOccuredEvent().get(i)));
		}

	}
}
