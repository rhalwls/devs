package Lab8;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class sender extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	private int cnt_packet;
	public sender() 
	{
		this("sender", 30);
	}
  
	public sender(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
		cnt_packet = 0;
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("wait"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("active", int_arr_time);
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			cnt_packet++;
			
			
			holdIn("active", int_arr_time);
			if(cnt_packet== 5) {
				count=1;
				cnt_packet = 0;
				holdIn("wait",INFINITY);
			}
		}
	}

	public message out()
	{
		message m = new message();
		if(phaseIs("active")) {
			if(cnt_packet<5) {
				m.add(makeContent("out",new packet("packet"+count,(int)(Math.random()*5)+1)));
			}
		}
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
