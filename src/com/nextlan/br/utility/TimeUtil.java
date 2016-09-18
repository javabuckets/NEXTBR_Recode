package com.nextlan.br.utility;

public class TimeUtil 
{
	public static int getMilliseconds(int minutes, int seconds)
	{
		int ms = 0;
		
		ms += (minutes*60000);
		ms += (seconds*1000);
		
		return ms;
	}
	
	public static int getSeconds(int minutes)
	{
		int sec = 0;
		
		sec += (minutes*60);
		
		return sec;
	}
}