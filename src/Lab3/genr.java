package Lab3;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	
	public genr() 
	{
		//this("genr",30);
		this("genr",20);	
		//첫번째 job의 time interval을 20으로 하기 위해서
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
		
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
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

	public void deltint()
	{
		
		if (phaseIs("active"))
		{
			System.out.println("current count is "+count); //프로그램 플로우를 확인하고 싶어서 넣은 로그
			
			if(count%2==1) {//job n 에서 홀수번일 땐 다음 job에 적용될 time interval을  50으로 
				int_arr_time= 50;
			}
			else { //짝수번일 땐 40으로
				int_arr_time = 40;
			}
			count++;
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{	
		
		
		message m = new message();
		m.add( makeContent("out",new entity("job"+count)));
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