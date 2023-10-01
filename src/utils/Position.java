package utils;


public class Position{


	private int absX;
	private int ordY;
	
	
	public Position(){
	
	}
	public Position(int abs ,int ord){
		this.absX=abs;
		this.ordY=ord;
	}

	public int getAbsX(){
		return this.absX;	
	}
	public int getOrdY(){
		return this.ordY;	
	}
	public void setAbsX(int x){
		this.absX=x;
	}
	public void setOrdY(int y){
		this.ordY=y;
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null)
			return false;
		if(o instanceof Position){
			Position pos=(Position)o;
			if((pos.getAbsX()==this.getAbsX())&& (pos.getOrdY()==this.getOrdY()))
				return true;
		}	
		return false;
	}

}
