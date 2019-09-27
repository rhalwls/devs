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
		
		holdIn("passive", INFINITY); //infinity : ���� ��ǲ�� ���� ������ �ƹ��͵� ���� �ʰڴٴ� ����
	}

	public void deltext(double e, message x)
	{
		Continue(e); //������ �ʴ� �� �Ʒ� �� �����ϰڴ�.
		if (phaseIs("passive"))//passive�� ����� ���ؼ���
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i)) 
				{
					job = x.getValOnPort("in", i); //in port�� �ִ� ������ ������
					
					holdIn("busy", processing_time); //phase busy�� ��ȭ
				}
			}
		}
	}
  
	public void deltint() //�̰� internal transision..?
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); //���� �ϰ� �ִ� ���� �������� Ŭ������ job ����Ʈ ������ �ʱ�ȭ
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy")) //���� �ϰ� �� �ֵ鿡 ����
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
