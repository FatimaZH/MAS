package test;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Agent1 extends Agent{
	
	public String state = "ON";
	public final static String WHATSYOURSTATE = "What is your state?";
	public final static String SENDCF = "Send cost function";
	public String COST = "100";
	
	protected void setup() {
		System.out.println(getLocalName()+" STARTED");
		ServiceDescription sd  = new ServiceDescription();
        sd.setType( "Agent1" );
        sd.setName( getLocalName() );
        register( sd );
		System.out.println(getLocalName()+" agent initial state is: "+ state);
		try {
			//
			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription serd  = new ServiceDescription();
			serd.setType( "Agent1" );
			dfd.addServices(serd);
			DFAgentDescription[] result = DFService.search(this, dfd);
			System.out.println("Search returns: " + result.length + " agents with the description 'Agent1' "+ '\n' );
			
			//If he is the first agent of type agent1, he won't send any message
			//It there is more than two agents with the type Agent1, he will send a message to all of them
			if (result.length>1){
				for (int i = 0; i < result.length; ++i){
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent(WHATSYOURSTATE);
					//Send the message for all agent except himself
					   if(getLocalName().equals(result[i].getName().getLocalName()) == false ){
						msg.addReceiver(result[i].getName());
						System.out.println(getLocalName()+" SENDS 'WHAT IS YOUR STATE MESSAGE'  TO "+result[i].getName().getLocalName());
						send(msg);
					   }
				    }    
			}		
		}catch (FIPAException fe) { fe.printStackTrace(); }
		
	addBehaviour(new CyclicBehaviour(this) {
public void action() {
	// listen if a WHATSYOURSTATE message arrives
	ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
	if (msg != null) {
		if (WHATSYOURSTATE.equalsIgnoreCase(msg.getContent())) {
			// Change the state when the agent receive a message
						if (state == "OFF"){
							state = "ON";
						}else {
							state = "OFF";
						}
			// if a WHATSYOURSTATE message is arrived then send an ANSWER
			System.out.println(myAgent.getLocalName()+" RECEIVED WHAT IS YOUR STATE MESSAGE FROM "+msg.getSender().getLocalName()); 
			System.out.println(myAgent.getLocalName()+" SENDS ANSWER MESSAGE : My state is:" +state);
			ACLMessage reply = msg.createReply();
			reply.setContent(state);
			myAgent.send(reply);
		}

	}else {
		
	}
	
}
	});
	
	addBehaviour(new CyclicBehaviour(this) {
	//Waiting for SENDCF message from GlobalCostFunction
	public void action() {
		// listen if a SENDCF message arrives
		ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msg != null) {
			if (SENDCF.equalsIgnoreCase(msg.getContent())) {
				// if a WHATSYOURSTATE message is arrived then send an ANSWER
				System.out.println(myAgent.getLocalName()+" RECEIVED SENDCF FROM "+msg.getSender().getLocalName()); 
				System.out.println(myAgent.getLocalName()+" SENDS THE COST");
				ACLMessage reply = msg.createReply();
				reply.setContent(COST);
				myAgent.send(reply);
			}
		}else {
			
		}
		
	}
	});
	
	
	}
	 void register( ServiceDescription sd){
	        DFAgentDescription dfd = new DFAgentDescription();
	        dfd.setName(getAID());
	        dfd.addServices(sd);
	        try {  
	            DFService.register(this, dfd );  
	        }
	        catch (FIPAException fe) { fe.printStackTrace(); }
	    }
}

