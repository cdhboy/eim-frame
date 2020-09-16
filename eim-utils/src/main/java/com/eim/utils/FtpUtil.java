package com.eim.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class FtpUtil {

	public static void download(String host, String workdirectory,
			String filename, String username, String password,
			OutputStream output) throws Exception {

		FTPClient ftp = new FTPClient();

		try {
			int reply;
			ftp.connect(host);

			ftp.login(username, password);

			ftp.enterLocalPassiveMode();
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return;
			}

			ftp.setControlEncoding(System.getProperty("file.encoding"));
			ftp.changeWorkingDirectory(workdirectory);

			ftp.retrieveFile(new String(filename.getBytes(), "ISO-8859-1"),
					output);

			ftp.logout();

		} catch (Exception e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}
	}

	public static byte[] download(String host, String workdirectory,
			String filename, String username, String password) throws Exception {

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		download(host, workdirectory, filename, username, password, output);

		byte[] bs = output.toByteArray();

		try {
			output.close();
		} catch (IOException e) {
		}

		return bs;
	}

	public static boolean upload(String host, String workdirectory,
			String filepath, String remotefilename, String username,
			String password) {
		boolean sucess = true;
		InputStream is = null;
		try {
			is = new FileInputStream(filepath);

			sucess = upload(host, workdirectory, is, remotefilename, username,
					password);

		} catch (Exception e) {
			e.printStackTrace();
			sucess = false;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}

		}

		return sucess;
	}

	public static boolean upload(String host, String workdirectory,
			InputStream is, String remotefilename, String username,
			String password) throws Exception {
		FTPClient ftp = new FTPClient();

		boolean success = false;

		try {
			int reply;
			ftp.connect(host);

			ftp.login(username, password);
			ftp.enterLocalPassiveMode();

			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}

			ftp.setControlEncoding(System.getProperty("file.encoding"));

			ftp.makeDirectory(workdirectory);

			ftp.changeWorkingDirectory(workdirectory);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			success = ftp.storeFile(new String(remotefilename.getBytes(),
					"iso-8859-1"), is);

			is.close();

			ftp.logout();

		} catch (Exception e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}

		return success;
	}

	public static boolean delete(String host, String workdirectory,
			String filename, String username, String password) throws Exception {
		FTPClient ftp = new FTPClient();

		boolean success = false;

		int reply;
		ftp.connect(host);

		ftp.login(username, password);

		ftp.enterLocalPassiveMode();
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return success;
		}

		ftp.setControlEncoding(System.getProperty("file.encoding"));

		ftp.changeWorkingDirectory(workdirectory);

		success = ftp.deleteFile(new String(filename.getBytes(), "iso-8859-1"));

		ftp.logout();
		if (ftp.isConnected()) {
			ftp.disconnect();
		}

		return success;
	}

	public static List<Map<String, String>> getFiles(String host,
			String workdirectory, String username, String password) {
		try {
			FTPClient ftp = new FTPClient();
			int reply;
			ftp.connect(host);

			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			// ����ģʽ
			ftp.enterLocalPassiveMode();
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			} else {
				ftp.setControlEncoding(System.getProperty("file.encoding"));
				ftp.changeWorkingDirectory(workdirectory);// ת�Ƶ�FTP������Ŀ¼
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				FTPFile[] fs = ftp.listFiles();
				for (FTPFile file : fs) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.MONTH, -1);
					if (cal.getTimeInMillis() < file.getTimestamp()
							.getTimeInMillis()) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("filename", file.getName());
						map.put("time", new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(file
								.getTimestamp().getTime()));
						list.add(map);
					}
				}
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static void main(String[] arg) {
//		upload("169.169.171.49", "update", "F:\\f��.xls", "f��i.xls", "tbms",
//				"TbmS2014");

		try {
			System.out.println(new String("我的.xls".getBytes(),"iso-8859-1"));
			System.out.println(URLEncoder.encode("我的.xls",System.getProperty("file.encoding")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
