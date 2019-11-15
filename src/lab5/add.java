package lab5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class add extends ViewableAtomic
{
  
	//protected entity job;
	protected double processing_time;

	
	protected opRequest add_req;
	protected int result;
	
	
	public add()
	{
		this("add", 20);
	}

	public add(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		//job = new entity("");
		
		
		result = 0;
		add_req=new opRequest("none",0,0);
		
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
					//job = x.getValOnPort("in", i);
					add_req = (opRequest)x.getValOnPort("in", i);
					result = add_req.num1+add_req.num2;
					System.out.println("add_result is : "+ result);
					holdIn("busy", processing_time);
				}
			}
		}
	}
	//이게 인터널이라고?
	//일이 끝나면 다시 passive 상태로 초기화
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			//job = new entity("");
			add_req = new opRequest("none",0,0);
			result = 0;
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new entity(Integer.toString(result))));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

