package se.billes.pdf.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import se.billes.pdf.renderer.exception.BMSSocketException;
import se.billes.pdf.renderer.socket.BMSSocketRequest;

import com.google.inject.Inject;

/**
 * This cron job will send poll requests to bms to allow registration of
 * plugin if bms was restarted after this plugin started. We should also send
 * the last generated pdf if this failed.
 * @author Mathias
 *
 */
@javax.inject.Singleton
@org.nnsoft.guice.guartz.Scheduled( cronExpression = "0/30 * * * * ?" )
public class BMSSocketScheduler implements org.quartz.Job{
	
	@Inject BMSSocketRequest bmsSocketRequest;
	
	@Override
	public void execute(JobExecutionContext content) throws JobExecutionException {		
		try {
			bmsSocketRequest.onJsonRequest(null);
		} catch (BMSSocketException e) {
			e.printStackTrace();
		}
	}
}
