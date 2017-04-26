##MultithreadDownLoader是什么?

多线程下载工具包.

##MultithreadDownLoader有哪些功能？

* 多线程下载
* 自动控制最大线程数.避免线程数设置错误造成过多cpu时间片切换
* 反馈实时速度以及下载进度

##使用情况

单线程下载微信windows版本
```java
速度(字节):222717 百分比:98 耗时(秒):187
速度(字节):208382 百分比:98 耗时(秒):188
速度(字节):202150 百分比:99 耗时(秒):189
下载结束,原始文件大小(字节):36796568 总耗时(秒):190
```

5线程下载微信windows版本
```java
速度(字节):370736 百分比:98 耗时(秒):103
速度(字节):377500 百分比:99 耗时(秒):104
速度(字节):324923 百分比:99 耗时(秒):105
下载结束,原始文件大小(字节):36796568 总耗时(秒):106
```

##MultithreadDownLoader如何使用？

```java
      public static void main(String[] args) throws Exception {
		DownloadUtil du = new DownloadUtil("http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe", 10, "O:/weixin.exe");
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
```



##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(21961252#qq.com, 把#换成@)
* QQ: 21961252