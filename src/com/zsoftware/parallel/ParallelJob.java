package com.zsoftware.parallel;

public class ParallelJob {
	private String name; // 返回结果的名
	private IJob ijob;

	public ParallelJob(String name, IJob ijob) {
		super();
		this.name = name;
		this.ijob = ijob;
	}

	public ParallelJob(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public IJob getIjob() {
		return ijob;
	}

	public void setIjob(IJob ijob) {
		this.ijob = ijob;
	}

}
