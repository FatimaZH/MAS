package test;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class GlobalCostFunction extends Agent {
	public final static String SENDCF = "Send cost function";
	private double[] cost;
	AgentCostSetpoint[] ACS;
	
	protected void setup() {
	//First get the AID of registered agents
		try {
			//Registered agents of type1
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd  = new ServiceDescription();
			sd.setType( "Agent1" );
			dfd.addServices(sd);
			DFAgentDescription[] agent1_result = DFService.search(this, dfd);
			System.out.println("Search GCF returns: " + agent1_result.length + " agents with the description 'Agent1' "+ '\n' );
			
			//Registered agents of type2
			DFAgentDescription dfd2 = new DFAgentDescription();
			ServiceDescription sd2  = new ServiceDescription();
			sd2.setType( "Agent2" );
			dfd2.addServices(sd2);
			DFAgentDescription[] agent2_result = DFService.search(this, dfd2);
			System.out.println("Search GCF returns: " + agent2_result.length + " agents with the description 'Agent2' "+ '\n' );
			
			//Send message to agent of type 1 to send their cost function
			if (agent1_result.length>=1){
				for (int i = 0; i < agent1_result.length; ++i){
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setContent(SENDCF);
					msg.addReceiver(agent1_result[i].getName());
					System.out.println(getLocalName()+" SENDS 'SEND COST FUNCTION'  TO "+agent1_result[i].getName().getLocalName());
					send(msg);
				}
			}
				//Send message to agent of type 2 to send their cost function
				if (agent2_result.length>=1){
					for (int i = 0; i < agent2_result.length; ++i){
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.setContent(SENDCF);
						msg.addReceiver(agent2_result[i].getName());
						System.out.println(getLocalName()+" SENDS 'SEND COST FUNCTION'  TO "+agent2_result[i].getName().getLocalName());
						send(msg);
					}
			}	
			
		}catch (FIPAException fe) { fe.printStackTrace(); }
	
	addBehaviour(new CyclicBehaviour(this) {
		//Waiting for SENDCF message from Agents
		public void action() {
			ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
			if (msg != null) {
			System.out.println(myAgent.getLocalName()+" RECEIVED COST="+msg.getContent() +" FROM "+msg.getSender().getLocalName());
			
			}
		}
	});
		
		
	}
}