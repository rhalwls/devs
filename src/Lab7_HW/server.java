package Lab7_HW;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class server extends ViewableAtomic
{
  
	protected entity job;
	protected packet packet_msg;
	protected double processing_time;

	public server()
	{
		this("server", 20);
	}
	public server(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		packet_msg = new packet("");
		
		holdIn("Listen", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("Listen"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("SYN-RCVD", processing_time);
				}
			}
		}
		else if (phaseIs("SYN+ACK-Sent"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("ACK-RCVD", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("ACK-RCVD"))
		{
			holdIn("Established", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("SYN-RCVD"))
		{
			m.add(makeContent("out", new packet("SYN+ACK")));
			holdIn("SYN+ACK-Sent",processing_time);
		}
		return m;
	}
	/*
	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}
	*/
}

