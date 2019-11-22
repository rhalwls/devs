package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class router extends ViewableAtomic
{
	protected msg forward_msg;
	protected entity job;
	protected double processing_time;

	public router()
	{
		this("router", 20);
	}

	public router(String name, double Processing_time)
	{
		super(name);
		addOutport("out0");
		addOutport("out1");
		addOutport("out2");;
		addInport("in");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		//job = new entity("");
		forward_msg = new msg("",0);//error?? 못받아적음 ㅠ
		
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
					forward_msg= (msg)x.getValOnPort("in", i);
					
					holdIn("sending", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("sending"))
		{
			forward_msg = new msg("",0);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		//내 수정
		if (phaseIs("sending"))
		{	
			System.out.println("sending msg from router to processor"+forward_msg.dest);
			m.add(makeContent("out"+ forward_msg.dest, forward_msg));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

