package XiaoPix;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.*;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class TwoWaySerialComm {
	public static int[][] pixels = new int[300][300];
	public static int h;
	public static int w;
	public static int send = 0;
	public static int recv;
	public static int contourlvl;
	public static int[] histogram;
	public static int numofpixels = 0;
	public static BufferedImage bi;
	public static boolean collect = false;

	public static void main(String[]args) {
		h = 0;
		w = 0;
		contourlvl = 8;
		histogram = new int[256];
		for (int a = 0; a < 256; a++)
			histogram[a] = 0;
		try {
			(new TwoWaySerialComm()).connect("COM3");
		} catch (Exception e) {
			// TODO Anananauto-generated catch block
			e.printStackTrace();
		}
		AllenGUI mamamama = new AllenGUI();
	}

	/**
	 * GUI part comes here.
	 */

	/**
	 * Serial communication part comes here.
	 */
	public TwoWaySerialComm() {
		super();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {

				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				(new Thread(new SerialWriter(out))).start();

				serialPort.addEventListener(new SerialReader(in));
				serialPort.notifyOnDataAvailable(true);

			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	public static class SerialReader implements SerialPortEventListener {
		private InputStream in;
		private byte[] buffer = new byte[1024];

		public SerialReader(InputStream in) {
			this.in = in;
		}

		/*
		 * All the stuff is here
		 * 
		 * @see
		 * gnu.io.SerialPortEventListener#serialEvent(gnu.io.SerialPortEvent)
		 */
		public void serialEvent(SerialPortEvent arg0) {
			int data;

			try {
				int len = 0;
				while ((data = in.read()) > -1) {
					if (data == '\n') {
						break;
					}
					buffer[len++] = (byte) data;
				}

				String extracted = new String(buffer, 0, len - 1);
				if (collect) {
					int nnnnnh = h, nnnnnw = w;
					numofpixels++;
					if (h == 299 && w % 2 == 0) {
						w++;
					} else if (h == 0 && w % 2 == 1) {
						w++;
					} else if (w % 2 == 0) {

						h++;
					} else {
						h--;
					}
					int gotthis = 0;
					try {

						gotthis = Integer.parseInt(extracted);
						recv = gotthis;
						pixels[nnnnnh][nnnnnw] = recv;
						histogram[pixels[nnnnnh][nnnnnw]]++;
						// System.out.print(gotthis);
						// System.out.println("hw,"+nnnnnh+","+nnnnnw+","+gotthis);

					} catch (NumberFormatException ex) {
						System.out.println("Could not parse integer: " + extracted);
					}

				}

			} catch (IOException e) {
				System.out.println("IO Error encoutered when receiving.");
				e.printStackTrace();
				System.exit(-1);
			}
		}

	}

	/** */
	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;
		}

		public void run() {
			try {
				while (true) {
					if (send != 0) {
						this.out.write(send);
						System.out.println("Sent value " + send + " through serial.");
					}
					send = 0;

					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
