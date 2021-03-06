
package com.tab.qa.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class ExecCommand {
	
	private Semaphore outputSem;
	private String output;
	private Integer exitCode = 0;
	private Semaphore errorSem;
	@SuppressWarnings("unused")
	private String error;

	private Process p;

	
	private class OutputReader extends Thread {
		public OutputReader() {
			try {
				outputSem = new Semaphore(1);
				outputSem.acquireUninterruptibly();
			}
			finally {
				outputSem.release();
			}
		}

		@Override
		public void run() {
			try {
				StringBuffer readBuffer = new StringBuffer();
				BufferedReader isr = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String buff = new String();
				while ((buff = isr.readLine()) != null) {
					readBuffer.append(buff);
					System.out.println(buff);
				}
				output = readBuffer.toString();
				outputSem.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ErrorReader extends Thread {
		public ErrorReader() {
			try {
				errorSem = new Semaphore(1);
				errorSem.acquireUninterruptibly();
			}
			finally {
				errorSem.release();
			}
		}

		@Override
		public void run() {
			try {
				StringBuffer readBuffer = new StringBuffer();
				BufferedReader isr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				String buff = new String();
				while ((buff = isr.readLine()) != null) {
					readBuffer.append(buff);
					System.out.println(buff);
				}
				error = readBuffer.toString();
				errorSem.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public ExecCommand(String command) {
		try {
			p = Runtime.getRuntime().exec(command); /*makeArray(command));*/
			new OutputReader().start();
			new ErrorReader().start();
			exitCode = p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Integer getExitCode() {
		try {
			outputSem.acquireUninterruptibly();
		}
		finally {
			outputSem.release();
		}
		Integer value = exitCode;
		outputSem.release();
		return value;
	}
	
	public String getOutput() {
		try {
			outputSem.acquireUninterruptibly();
		}
		finally {
			outputSem.release();
		}
		String value = output;
		outputSem.release();
		return value;
	}

/*	private String[] makeArray(String command) {
		ArrayList<String> commandArray = new ArrayList<String>();
		String buff = "";
		boolean lookForEnd = false;
		for (int i = 0; i < command.length(); i++) {
			if (lookForEnd) {
				if (command.charAt(i) == '\"') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
					lookForEnd = false;
				} else {
					buff += command.charAt(i);
				}
			} else {
				if (command.charAt(i) == '\"') {
					lookForEnd = true;
				} else if (command.charAt(i) == ' ') {
					if (buff.length() > 0)
						commandArray.add(buff);
					buff = "";
				} else {
					buff += command.charAt(i);
				}
			}
		}
		if (buff.length() > 0)
			commandArray.add(buff);

		String[] array = new String[commandArray.size()];
		for (int i = 0; i < commandArray.size(); i++) {
			array[i] = commandArray.get(i);
		}

		return array;
	}*/
}