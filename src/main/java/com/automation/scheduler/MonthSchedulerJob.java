package com.automation.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MonthSchedulerJob extends QuartzJobBean {
	private MonthSchedulerTask monthSchedulerTask;

  

	public void setMonthSchedulerTask(MonthSchedulerTask monthSchedulerTask) {
		this.monthSchedulerTask = monthSchedulerTask;
	}

 


	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		monthSchedulerTask.scheduler();

	}
}