package Lab9;
import GenCol.*;

public class msg extends entity
{   
	int ans;
	int dest;
	int[] num;
	public int num1;
	public int num2;
	public msg(String name, int _dest,int _num1,int _num2) {
		super(name);
		dest = _dest;
		num1 = _num1;
		num2 = _num2;
	}
	public msg(String name, int _dest, int[] _num) {
		super(name);
		dest= _dest;
		num = _num;
	}
	public msg(String name, int _ans) {
		super(name);
		ans= _ans;
	}
	public msg(String name)
	{  
		super(name);  
	}
	
}
