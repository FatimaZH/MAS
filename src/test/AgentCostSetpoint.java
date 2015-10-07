package test;
import jade.core.AID;
import jade.core.Agent;

public class AgentCostSetpoint {
	private AID aid;
	private String type;
	private double cost = 0;
	private double setPoint = 0;
	
	public AgentCostSetpoint() {
		super();
	}
	public AgentCostSetpoint(AID aid) {
		super();
		this.aid = aid;
	}
	public AgentCostSetpoint(AID aid,double cost,double setPoint) {
		super();
		this.aid = aid;
		this.cost = cost;
		this.setPoint = setPoint;
	}

	public void setAid (AID aid) {
		this.aid = aid;
	}
	public void setCost (double cost) {
		this.cost = cost;
	}
	public void setAid (double setPoint) {
		this.setPoint = setPoint;
	}
	public AID getAid() {
		return aid;
	}
	public double getCost() {
		return cost;
	}
	public double getSetPoint() {
		return setPoint;
	}
	

}
