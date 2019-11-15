package lab5;
import java.awt.*;
import simView.*;

public class gpt extends ViewableDigraph
{
	

	public gpt()
	{
		super("coupled Model");
    	
		ViewableAtomic g = new user("g", 10);
		//ViewableAtomic adder = new operator("adder",10);
		ViewableAtomic[] p= {new operator(0),new operator(1),new operator(2),new operator(3)};
		
		//ViewableAtomic t = new transd("t", 70);
		add(g);
		for(int i =0;i<p.length;i++) {
			add(p[i]);
			
			if(i==0) {
				addCoupling(g,"out",p[i],"in");
			}
			else {
				addCoupling(g,"out"+(i+1),p[i],"in");
			}
			addCoupling(p[i],"out",g,"in");
		}
		
	}
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(988, 646);
        ((ViewableComponent)withName("sub")).setPreferredLocation(new Point(588, 139));
        ((ViewableComponent)withName("div")).setPreferredLocation(new Point(602, 479));
        ((ViewableComponent)withName("mul")).setPreferredLocation(new Point(642, 386));
        ((ViewableComponent)withName("g")).setPreferredLocation(new Point(63, 279));
        ((ViewableComponent)withName("add")).setPreferredLocation(new Point(269, 74));
    }
}