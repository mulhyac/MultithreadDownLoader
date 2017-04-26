package com.zsoftware.parallel;

/**
 * 并行任务接口
 * 
 * @author zjy
 * 
 */
 
public interface IJob {
	public Object run() throws Exception;
}