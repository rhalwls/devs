package Lab7_HW;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	packet packet_msg;
  
	public client() 
	{
		this("client", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()	
	{
		count = 1;
		packet_msg = new packet("");
		holdIn("Active open", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{	
		System.out.println("external function was called");
		/*
		if(phaseIs("Active open")) {
			holdIn("SYN-Sent",INFINITY);
		}
		*/
		if (phaseIs("SYN-Sent")) {
			System.out.println("right before phase change to syn+ack-rcvd");
			holdIn("SYN+ACK-RCVD",0);// 원래 코드는 이거였대
			//holdIn("SYN+ACK-RCVD",int_arr_time);
		}
		
	}

	public void deltint()
	{
		System.out.println("internal function was called");
		if (phaseIs("Active open"))
		{
			count = count + 1;
			
			holdIn("SYN-Sent", int_arr_time);
			
		}
		else if(phaseIs("SYN+ACK-RCVD")) {
			System.out.println("right before change to established(internal)");
			holdIn("Established",INFINITY);
		}
	}

	public message out()
	{
		System.out.println("out function was called");
		message m = new message();
		if(phaseIs("Active open")) {
			m.add(makeContent("out",new packet("SYN")));
		}
		else if(phaseIs("SYN+ACK-RCVD")) {
			System.out.println("syn+act-rcvd 상태에서 act패킷을 보낼거야");
			m.add(makeContent("out",new packet("ACK")));
		}
		return m;
	}
	/*
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}
	*/

}
