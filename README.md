# MultithreadDownLoader
多线程下载工具类


# 做什么用

* 多线程下载
* 简单API调用
* 自动根据CPU核心数选择合适下载线程数

# 怎么用

'''java
public static void main(String[] args) throws Exception {
		DownloadUtil du = new DownloadUtil("http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe", 5, "O:/weixin.exe");
		du.download(new DownLoadListener() {

			@Override
			public void DownLoading(int speed, int person, int currentCostTime) {
				System.out.println("速度(字节):" + speed + " 百分比:" + person + " 耗时(秒):" + currentCostTime);
			}

			@Override
			public void DownLoadEnd(int fileSizebytes, int downLoadTime) {
				System.out.println("下载结束,原始文件大小(字节):" + fileSizebytes + " 总耗时(秒):" + downLoadTime);
			}

			@Override
			public void DownLoadBegin(int fileSizebytes) {
				System.out.println("下载开始,原始文件大小(字节):" + fileSizebytes);
			}
		});
	}
'''