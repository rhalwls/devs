package Lab8_HW;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;
//���� procq����
public class router extends ViewableAtomic
{
	
	protected Queue q;
	protected entity job;
	protected double processing_time;
	protected packet packet_msg;
	boolean ready_shoot = false;
	public router()
	{
		this("router", 20);
	}

	public router(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		for(int i =1;i<=5;i++) {
			addOutport("out"+i);
		}
		System.out.println("adding out port to rouuter");
		addOutport("out");
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		q = new Queue();
		packet_msg = new packet("",0);
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		System.out.println("external has called");
		Continue(e);
		if (phaseIs("passive"))
		{
		
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					packet_msg = (packet) x.getValOnPort("in", i);
					q.add(packet_msg);
					System.out.println("current queue size of router is "+q.size());
					if(q.size()== 5) {
						holdIn("passive",0);
						//holdIn("sending",10);
					}
				}
			}
		}
	}
	
	public void deltint()
	{
		System.out.println("internal has called");
		
		if(q.size() ==5) {
			//holdIn("passive",10);
			holdIn("sending",10);
		}
		
		
		if (phaseIs("sending"))
		{
			if (!q.isEmpty()) {
				holdIn("sending",processing_time);
			}
		}
		
	}

	public message out()
	{
		System.out.println("out has called");
		message m = new message();
		if(phaseIs("passive")&&q.size()==5) {
			holdIn("sending",10);
		}
		if (phaseIs("sending"))
		{
			if(!q.isEmpty()) {
				int portNum = packet_msg.dest;
				System.out.println("port number of this msg is "+portNum);
				packet_msg = (packet)q.removeFirst();
				m.add(makeContent("out"+portNum,packet_msg));
			}
			else {
				System.out.println("out���� ��������������");
				m.add(makeContent("out",new packet("done",0)));
				holdIn("passive",INFINITY);//�ƿ��� Ȧ���ξȾ��� �� ��Ģ�̱���
			}
			
		}
		
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + "queue length: " + q.size()
        + "\n" + "queue itself: " + q.toString();
	}

}



