package Lab9;
import genDevs.modeling.*;
import GenCol.*;

import simView.*;

public class cal extends ViewableAtomic
{
	protected int res;
	protected int what_cal;
	protected msg add_msg;
	protected double processing_time;

	public cal()
	{
		this("add", 20,0);
	}

	public cal(String name, double Processing_time,int _what_cal)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
		if(what_cal>1) {
			System.out.println("not defined calculator");
		}
		what_cal = _what_cal;
	}
  
	public void initialize()
	{
		add_msg= new msg("",0);
		res =0;
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
					add_msg = (msg)x.getValOnPort("in", i);
					if(what_cal == 0) {

						res = add_msg.num1+add_msg.num2;	
					}
					else if(what_cal == 1) {
						res = add_msg.num1*add_msg.num2;
						System.out.println("multiplication");
					}
					System.out.println("doing add's external, my what_cal is "+what_cal);
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			add_msg = new msg("",0);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new msg(""+res,res)));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

