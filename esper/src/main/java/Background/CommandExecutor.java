package Background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class CommandExecutor {
	private ProcessBuilder pr;
	private Process p;
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private ArrayList<String> listResult = new ArrayList<String>();
	private boolean listed = true;
	private String commandErrorReader = null;
	private String commandOutputReader = null;
	private String commandOutputResult = null;

	public CommandExecutor() {

	}

	public String startCommand(String cmd) {
		String[] cmd1 = { "/bin/bash", "-c", cmd };
//		System.out.println(cmd);
		try {
			pr = new ProcessBuilder(cmd1);
			pr.redirectErrorStream(true);
			p = pr.start();
			// writer.write(pass);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((commandOutputReader = stdInput.readLine()) != null) {
//				System.out.println(commandOutputReader);
				commandOutputResult = commandOutputReader;

			}
			while ((commandErrorReader = stdError.readLine()) != null) {
//				System.out.println(commandErrorReader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return commandOutputReader;
	}

	public ArrayList<String> listDir(String dir) {
		String cmd = "sudo ls " + dir;
		String[] cmd1 = { "/bin/bash", "-c", cmd };
		try {
			pr = new ProcessBuilder(cmd1);
			pr.redirectErrorStream(true);
			p = pr.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			listResult.clear();
			while ((commandOutputReader = stdInput.readLine()) != null) {
				listResult.add(commandOutputReader);
			}
			while ((commandErrorReader = stdError.readLine()) != null) {
//				System.out.println(commandErrorReader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listResult;
	}

	public DefaultListModel<String> listOS(String path) {
		String cmd = "sudo ls " + path;
		String[] cmd1 = { "/bin/bash", "-c", cmd };
		try {
			pr = new ProcessBuilder(cmd1);
			pr.redirectErrorStream(true);
			p = pr.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			OSImageList.clear();
			while ((commandOutputReader = stdInput.readLine()) != null) {
				OSImageList.addElement(commandOutputReader);
			}
			while ((commandErrorReader = stdError.readLine()) != null) {
//				System.out.println(commandErrorReader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return OSImageList;
	}

	public String getResult() {
		return commandOutputResult;
	}

	public DefaultListModel<String> getOutput() {
		return OSImageList;
	}

	public void setListedState(boolean b) {
		listed = b;
	}

}
