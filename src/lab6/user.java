package lab6;
import simView.*;
import genDevs.modeling.*;

import java.util.Random;

import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	protected number req_num;
	protected int num;
	protected Queue queue;
	
	public user() 
	{
		this("genr", 30);
	}
  
	public user(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		System.out.println("user initialize called");
		
		count = 1;
		req_num = new number("none",0, false);
		num = 0;
		queue = new Queue();
		
		for(int i = 0;i<10;i++) {
			queue.add(i);
		
		}
		holdIn("start",0);//hi!!
		//holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		System.out.println("user external called");
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					
					holdIn("finished",INFINITY);
					//holdIn("stop", INFINITY);
				}
			}
		}
	}

	public void deltint()
	{
		System.out.println("user internal called");
		/*
		if (phaseIs("active"))
		{
			count = count + 1;
			
			holdIn("active", int_arr_time);
			if(count > 5) {
				holdIn("stop",INFINITY);
			}
		}
		*/
		
		if(phaseIs("start")) {
			holdIn("active",0);
		}
		if(phaseIs("active")) {
			count++;
			if(queue.size()>0) {
				num = (int) queue.removeFirst();
				if(queue.isEmpty()) {
					req_num = new number(Integer.toString(num),num,true);
				}
				else {
					req_num = new number(Integer.toString(num),num);
					holdIn("active", int_arr_time);
				}
			}
			else if(queue.isEmpty()) {
				holdIn("passive",INFINITY);
			}
		}
	}

	public message out()
	{
		System.out.println("user upper out called");
		message m = new message();
		if(phaseIs("active")) {
			m.add(makeContent("out",req_num));
		}
		System.out.println("user lower out called");
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
