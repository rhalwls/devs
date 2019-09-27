package Lab3;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
  
	protected entity job;
	protected double processing_time;

	public proc()
	{
		this("proc", 20);
	}

	public proc(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job = new entity(""); //
		
		holdIn("passive", INFINITY); //infinity : 다음 인풋이 있을 때까지 아무것도 하지 않겠다는 상태
	}

	public void deltext(double e, message x)
	{
		Continue(e); //멈추지 않는 한 아래 것 실행하겠다.
		if (phaseIs("passive"))//passive한 기관에 대해서만
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i)) 
				{
					job = x.getValOnPort("in", i); //in port에 있는 데이터 가져옴
					
					holdIn("busy", processing_time); //phase busy로 변화
				}
			}
		}
	}
  
	public void deltint() //이게 internal transision..?
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); //당장 하고 있는 일이 끝났으니 클래스의 job 디폴트 값으로 초기화
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy")) //일을 하고 난 애들에 대해
		{
			m.add(makeContent("out", job));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}

}
