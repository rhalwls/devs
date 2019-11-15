package lab5;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	
	public user() 
	{
		this("user", 30);
	}
  
	public user(String name, double Int_arr_time)
	{
		super(name);
		
		for(int i = 1;i<= operator.names.length;i++) {
			if(i==1) {
				addOutport("out");
			}
			else {
			addOutport("out"+i);
			}
			
		}	
		//addOutport("out");
		
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
		
		holdIn("active", int_arr_time);
	}
	//external
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
	//internal
	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			holdIn("stop",INFINITY);
			//holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();//message객체 하나에 여러 request 저장
		
		for(int i = 0;i<operator.names.length;i++) {
			
			if(i==0) {
				m.add(makeContent("out", new opRequest(Integer.toString(2)+operator.op_char[i]+Integer.toString(3)+" = ?",2,3)));
			}
			else {
				m.add(makeContent("out"+(i+1), new opRequest(Integer.toString(2)+operator.op_char[i]+Integer.toString(3)+" = ?",2,3)));
			}
		}
		
		
		//m.add(makeContent("out", new opRequest(Integer.toString(2)+"op"+Integer.toString(3)+" = ?",2,3)));
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
