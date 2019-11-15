package lab6;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class accumulator extends ViewableAtomic
{
	
	protected double processing_time;

	
	protected Queue q;
	protected number req_num;
	protected int result;
	
	
	public accumulator()
	{
		this("accumulator", 20);
	}

	public accumulator(String name, double Processing_time)
	{
		super(name);
		System.out.println("accumulator constructor");
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		System.out.println("accumulator initialize");
		holdIn("passive", INFINITY);//상변화를 먼저 해야하는 이유가 있나요?
		
		q = new Queue();
		req_num = new number("",0,false);
		result = 0;
	}

	public void deltext(double e, message x)
	{
		System.out.println("accumulator external");
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					req_num = (number)x.getValOnPort("in", i);
					q.add(req_num);
					holdIn("passive",processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		System.out.println("accumulator internal");
		if (phaseIs("passive"))//원래 busy 였는데?
		{
			
			req_num = (number)q.getLast();
			System.out.println(req_num.isLast);
			if(req_num.isLast) {
				int ql = q.size();//ㅇㅎ 반복문에서 깎여가니까
				for(int i = 0;i<ql;i++) {
					req_num = (number)q.removeFirst();
					result = result+req_num.num;
					holdIn("processing",processing_time);
				}
			}
		}
	}

	public message out()
	{
		System.out.println("accumulator upper out ");
		message m = new message();
		if (phaseIs("processing"))
		{
			m.add(makeContent("out", new number(Integer.toString(result),result)));
		}
		System.out.println("accumulator lower out");
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

