package com.zsoftware.download;

import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import com.zsoftware.parallel.ParalleUtils;
import com.zsoftware.parallel.ParallelJob;

/*
 */
public class DownloadUtil {

	private int threadNum;
	private int contentLength;
	private String targetFileName;
	private String path;

	private AtomicInteger contentDownLength; // 原子 当前下载长度;
	private Timer timer = new Timer();
	private int lastContentDownLength = 0;
	private int downLoadCostTime = 0;

	public DownloadUtil(String path, int threadNum, String targetFileName) {
		this.path = path;
		this.threadNum = threadNum;
		this.targetFileName = targetFileName;
	}

	public void download(DownLoadListener downLoadListener) throws Exception {
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		contentLength = httpURLConnection.getContentLength();

		int partSize = contentLength / threadNum + 1;
		RandomAccessFile randomAccessFile = new RandomAccessFile(targetFileName, "rw");
		randomAccessFile.setLength(contentLength);
		randomAccessFile.close();
		httpURLConnection.disconnect();

		timer.schedule(new TimerTask() {
			public void run() {
				if (contentDownLength != null) {
					int currContentDownLength = contentDownLength.get();
					int speedContentDownLength = currContentDownLength - lastContentDownLength;
					double person = currContentDownLength*1.00 / contentLength;
					downLoadListener.DownLoading(speedContentDownLength, (int) (person * 100), downLoadCostTime);
					lastContentDownLength = currContentDownLength;
					downLoadCostTime++;
				}

			}
		}, 0, 1000);

		contentDownLength = new AtomicInteger(0);
		downLoadListener.DownLoadBegin(contentLength);

		ArrayList<ParallelJob> ParallelJob = new ArrayList<ParallelJob>();
		for (int i = 0; i < threadNum; i++) {
			int startPos = i * partSize;
			RandomAccessFile randomAccessFile1 = new RandomAccessFile(targetFileName, "rw");
			randomAccessFile1.seek(startPos);

			DownloadThread downloadThread = new DownloadThread(path, startPos, partSize, randomAccessFile1,
					this.contentDownLength);

			ParallelJob.add(new ParallelJob("P" + i, downloadThread));
		}
		HashMap<String, Object> parallelJobs = ParalleUtils.ParallelJobs(ParallelJob);

		timer.cancel();
		ParalleUtils.shutDown();
		downLoadListener.DownLoadEnd(contentLength, downLoadCostTime);
	}

}