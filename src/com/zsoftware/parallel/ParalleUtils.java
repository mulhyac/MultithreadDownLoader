package com.zsoftware.parallel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParalleUtils {
	static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void shutDown() {
		exec.shutdown();
	}

	public static HashMap<String, Object> ParallelJobs(final ParallelJob... paralleljob)
			throws InterruptedException, ExecutionException {

		long beiginTime = System.currentTimeMillis();
		// 任务总数
		int jobsLength = paralleljob.length;

		CompletionService<HashMap<String, Object>> completionService = new ExecutorCompletionService<HashMap<String, Object>>(
				exec);

		for (int i = 0; i < jobsLength; i++) {
			final int Pos = i;
			Callable tmpCallable = new Callable() {
				public HashMap<String, Object> call() throws Exception {
					String name = paralleljob[Pos].getName();
					Object retVal = paralleljob[Pos].getIjob().run();

					HashMap<String, Object> retHashMap = new HashMap<String, Object>();
					retHashMap.put(name, retVal);
					return retHashMap;
				}

			};
			completionService.submit(tmpCallable);
		}

		HashMap<String, Object> methodRetVal = new HashMap<String, Object>();

		for (int i = 0; i < jobsLength; i++) {
			HashMap<String, Object> hashMapRetVal = completionService.take().get();
			for (String s : hashMapRetVal.keySet()) {
				methodRetVal.put(s, hashMapRetVal.get(s));
			}

		}

		long endTime = System.currentTimeMillis();
		methodRetVal.put("costTime", endTime - beiginTime);
		return methodRetVal;
	}

	public static HashMap<String, Object> ParallelJobs(final List<ParallelJob> paralleljob)
			throws InterruptedException, ExecutionException {

		long beiginTime = System.currentTimeMillis();
		// 任务总数
		int jobsLength = paralleljob.size();

		// ExecutorService exec = Executors.newFixedThreadPool(jobsLength);
		CompletionService<HashMap<String, Object>> completionService = new ExecutorCompletionService<HashMap<String, Object>>(
				exec);

		for (int i = 0; i < jobsLength; i++) {
			final int Pos = i;
			Callable tmpCallable = new Callable() {
				public HashMap<String, Object> call() throws Exception {
					String name = paralleljob.get(Pos).getName();
					Object retVal = paralleljob.get(Pos).getIjob().run();

					HashMap<String, Object> retHashMap = new HashMap<String, Object>();
					retHashMap.put(name, retVal);
					return retHashMap;
				}

			};
			completionService.submit(tmpCallable);
		}

		HashMap<String, Object> methodRetVal = new HashMap<String, Object>();
		for (int i = 0; i < jobsLength; i++) {
			HashMap<String, Object> hashMapRetVal = completionService.take().get();
			for (String s : hashMapRetVal.keySet()) {
				methodRetVal.put(s, hashMapRetVal.get(s));
			}

		}

		long endTime = System.currentTimeMillis();
		methodRetVal.put("costTime", endTime - beiginTime);
		return methodRetVal;
	}
}
