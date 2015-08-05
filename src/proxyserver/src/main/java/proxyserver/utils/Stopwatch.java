package proxyserver.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自动开始的计时秒表, 精度到纳秒1E-9<br>
 * @author tianxiaowei
 *
 */
public class Stopwatch
{
	
	private long begin;
	
	public Stopwatch()
	{
		begin = System.nanoTime();
	}

	public void update() 
	{
		begin = System.nanoTime();
	}
	
	public long getBeginNanos()
	{
		return begin;
	}
	
	public long getNanos()
	{
		return System.nanoTime() - begin;
	}
	
	public double getSeconds()
	{
		return (System.nanoTime() - begin) / 1E9;
	}
	
	public double getMillseconds()
	{
		return (double)(System.nanoTime() - begin) / 1E6;
	}
}
