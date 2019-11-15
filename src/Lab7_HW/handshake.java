package Lab7_HW;
import java.awt.*;
import simView.*;

public class handshake extends ViewableDigraph
{

	public handshake()
	{
		super("handshake");
    	
		ViewableAtomic g = new client("client", 10);
		ViewableAtomic p = new server("server", 10);
    	
		add(g);
		add(p);
  
		addCoupling(g, "out", p, "in");

     
		addCoupling(p, "out", g, "in");
	}

    
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(988, 646);
        ((ViewableComponent)withName("client")).setPreferredLocation(new Point(63, 279));
        ((ViewableComponent)withName("server")).setPreferredLocation(new Point(609, 497));
    }
}
