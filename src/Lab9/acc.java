package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class acc extends ViewableAtomic
{
	protected int sum;
	protected msg acc_msg;
	protected double processing_time;

	public acc()
	{
		this("acc", 20);
	}

	public acc(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		acc_msg= new msg("",0);
		sum =0;
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					acc_msg = (msg)x.getValOnPort("in", i);
					//sum = acc_msg.num1+acc_msg.num2;
					for(int j = 0;j<acc_msg.num.length;j++) {
						sum+=acc_msg.num[j];
					}
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			acc_msg = new msg("",0);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new msg(""+sum,sum)));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

