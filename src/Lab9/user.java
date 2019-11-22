package Lab9;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	protected int flag; //can range from 0 to 2
	protected int add_num1 = 2, add_num2 = 9;
	protected int[] num = {3,6,4,4,7,1,8,5,2,1};
	protected msg req_msg;
	public user() 
	{
		this("user", 30);
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
	
		//count = 1;
		req_msg = new msg("",0);
		flag = 0;
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("sent_1"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					req_msg = (msg)x.getValOnPort("in", i);
					System.out.println(add_num1+" + "+add_num2+ " ="+req_msg.ans);
					
					holdIn("active", int_arr_time);
				}
			}
		}
		else if(phaseIs("sent_2")) {
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					req_msg = (msg)x.getValOnPort("in", i);
					System.out.println(add_num1+" * "+add_num2+ " ="+req_msg.ans);
					
					holdIn("active", int_arr_time);
				}
			}
			
		}
		else if(phaseIs("sent_3")) {
			
			for(int i=0;i<x.getLength();i++) {
				if(messageOnPort(x, "in", i)) {
					req_msg = (msg)x.getValOnPort("in", i);
					System.out.println("sum : "+req_msg.ans);
					holdIn("finished",INFINITY);
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active")&&flag==0)
		{
			//count = count + 1;
			flag = 1;
			req_msg = new msg("",0);
			holdIn("sent_1",INFINITY);
		}
		if(phaseIs("active")&&flag==1) {
			flag= 2;
			req_msg = new msg("",0);
			holdIn("sent_2",INFINITY);
		}
		else if(phaseIs("active")&&flag==2) {
			req_msg = new msg("",0);
			holdIn("sent_3",INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if(phaseIs("active")&&flag==0) {
			m.add(makeContent("out",new msg(add_num1+" + "+add_num2,0,add_num1,add_num2)));
		}
		else if(phaseIs("active")&&flag==1) {
			m.add(makeContent("out", new msg(add_num1+" * "+add_num2,1,add_num1, add_num2 )));
		}
		else if(phaseIs("active")&&flag==2) {
			m.add(makeContent("out", new msg("num array",2,num)));
		}
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}
