package lab4;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	public genr() 
	{
		this("genr", 30);
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		System.out.println("gern 생성자 : intarrtime is "+Int_arr_time);
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		//count = 1;
		count=0;
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					//holdIn("stop", INFINITY);
				}
			}
		}
	}
	

	
	
	
	public void deltint2() {
		if(phaseIs("active")) {
			count++;
			
			holdIn("active",int_arr_time);
			
		}
	}
	
	
	
	

	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			
			//4주차 과제
			if(count>2) {
				int_arr_time = 60;
			}
			
			
			
			
			holdIn("active", int_arr_time);
			if(count >4) {
				//j4이후엔 일 안만듦
				System.out.println("I want to stop my generator");
				holdIn("stop",INFINITY);
			}
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", new entity("job" + count)));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}

}
