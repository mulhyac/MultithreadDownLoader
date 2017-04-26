package com.zsoftware.download;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import com.zsoftware.parallel.IJob;

/*
 * Author: EN.Felix
 * Email: Javagoshell@gmail.com
 */
public class DownloadThread implements IJob {
	private int startPos;
	private int partSize;
	public volatile int length;
	private String path;
	private RandomAccessFile randomAccessFile;
	private AtomicInteger contentDownLength; // 原子 当前下载长度;

	public DownloadThread(String path, int startPos, int partSize, RandomAccessFile randomAccessFile,
			AtomicInteger conteSize) {
		this.path = path;
		this.startPos = startPos;
		this.partSize = partSize;
		this.randomAccessFile = randomAccessFile;
		this.contentDownLength = conteSize;
	}

	public Object run() {
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(path);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Range", "bytes=" + startPos + "-" + (partSize + startPos));// 设置该头字段表示下载�?部分数据
			InputStream inputStream = httpURLConnection.getInputStream();
			// inputStream.skip(startPos);

			int report_person = ((partSize + startPos) - startPos) / 100; // 报告百分�?
			int b;
			int report_current = 0;
			int report_current_person = 0;

			while (length < partSize && (b = inputStream.read()) != -1) {
				contentDownLength.incrementAndGet();
				length++;
				randomAccessFile.write(b);
				report_current++;
				if (report_current >= report_person) {
					report_current = 0;
					report_current_person++;
				}
			}
			randomAccessFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return null;
	}
}