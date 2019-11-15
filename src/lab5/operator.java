package lab5;
import java.util.Arrays;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class operator extends ViewableAtomic
{
  
	//protected entity job;
	protected double processing_time;
	protected opRequest op_req;
	protected double result;
	protected int op;//what kind of operator it is?
	public static String names[] = {"add", "sub", "mul","div"};//name index can range from 0 to 3
	public static char op_char[] = {'+','-','*','/'};

	public operator(int Op)
	{
		//default constructor 고침
		this(names[Op], 20);
		op = Op;
		String name = names[op];
	}

	public operator(String name, double Processing_time)
	{
		super(name);
		op = Arrays.binarySearch(names, name);
		
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		//job = new entity("");
		
		
		result = 0;
		op_req=new opRequest("none",0,0);
		
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
					op_req = (opRequest)x.getValOnPort("in", i);
					int a = op_req.num1;
					int b = op_req.num2;
				
					switch(op) {
					case 0:
						//+
						result = a+b;
						break;
					case 1:
						result = a-b;
						break;
					case 2:
						result = a*b;
						break;
					case 3:
						result= a / ((double)b);
						//System.out.println("division result is  "+result);
						break;
					}
					System.out.println(names[op]+"_result is : "+ result);
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
			op_req = new opRequest("none",0,0);
			result = 0;
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new entity(Double.toString(result))));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

