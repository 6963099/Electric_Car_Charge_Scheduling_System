
package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GetInfo extends CyclicBehaviour {

    private static final long serialVersionUID = -8948876678560026420L;

    @Override
    public void action() {
        ACLMessage message = super.myAgent.receive();
        if (message != null) {
            // Handle the message.
            if (!message.getSender().equals(super.myAgent.getAID())) {
                if (message.getContent().contains("i need to charge")) {
                    System.out.println(super.myAgent.getLocalName() + ": MESSAGE RECEIVED: "
                            + message.getContent() + " ---- From: "
                            + message.getSender().getLocalName());
                    String[] values = message.getContent().replaceAll("[\\D]", " ").trim()
                            .replaceAll(" +", " ").split(" ", 2);
                    Integer senderChargeBy = new Integer(values[0]);
                    Integer senderChargeTime = new Integer(values[1]);
                    Integer myChargeBy = Integer.parseInt(super.getParent().getDataStore()
                            .get("timeTillUse").toString());
                    Integer myChargeTime = Integer.parseInt(super.getParent().getDataStore()
                            .get("timeNeeded").toString());
                    Integer slotValue = Integer.parseInt(super.getParent().getDataStore()
                            .get("slotValue").toString());
                    Integer newSlotValue = new Integer(slotValue);
                    if (senderChargeBy == myChargeBy) {
                        if (senderChargeTime == myChargeTime)
                            newSlotValue = slotValue;
                        else if (senderChargeTime < myChargeTime)
                            newSlotValue = slotValue - 1;
                        else
                            newSlotValue = slotValue + 1;
                    }
                    else if (senderChargeBy < myChargeBy)
                        newSlotValue = slotValue - 1;
                    else
                        newSlotValue = slotValue + 1;
                    //
                    // System.out.println("senderChargeBy " + senderChargeBy);
                    // System.out.println("senderChargeTime " +
                    // senderChargeTime);
                    // System.out.println("myChargeBy " + myChargeBy);
                    // System.out.println("myChargeTime" + myChargeTime);
                    // System.out.println("slotValue " + slotValue);
                    // System.out.println("newSlotValue " + newSlotValue);
                    //
                    super.getParent().getDataStore().put("slotValue", newSlotValue);
                    System.out.println(super.myAgent.getLocalName() + " has a slot value of "
                            + super.getParent().getDataStore().get("slotValue").toString());
        
                    //NEED TO FIGURE OUT A WAY TO TRIGGER THIS
                    //super.myAgent.alertGui(ds.get("slotValue").toString());
                }
            }
        } else
            block();
    }
}
