package com.zsoftware.download;

/**
 * 下载监听
 * 
 * @author coolzlay
 *
 */
public interface DownLoadListener {

	public void DownLoadBegin(int fileSizebytes);

	public void DownLoadEnd(int fileSizebytes, int downLoadTime);

	public void DownLoading(int speed, int person, int currentCostTime);
}
