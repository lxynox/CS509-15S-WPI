
package com.flight_ticket_search.Iterator;

import java.util.ArrayList;
import java.util.List;

import com.flight_ticket_search.Entity.Flight;
import com.flight_ticket_search.Entity.Ticket;
import com.flight_ticket_search.Service.RequestFlightServiceImpl;

/**
 * @author lxybi_000
 * Date: 28, march, 2015
 * Description: concrete class for TicketCollection to implements its main functions
 * 				including: add(), iterator(), romove()
 *
 */
public class TicketCollectionImpl implements TicketCollection {

	
	/* fields */
	private List<Ticket> ticketsList = new ArrayList<Ticket>();
	
	/* (non-Javadoc)
	 * @see Iterator.FlightCollection#add(java.util.List)
	 */
	@Override
	public boolean add(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketsList.add(ticket);
	}

	/* (non-Javadoc)
	 * @see Iterator.FlightCollection#remove()
	 */
	@Override
	public boolean remove(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketsList.remove(ticket);
	}

	public List<Ticket> getList() {
		return ticketsList;
	}
	
	public void setList(List<Ticket> ticketsList) {
		this.ticketsList = ticketsList;
	}
	/* (non-Javadoc)
	 * @see Iterator.FlightCollection#iterator()
	 */
	@Override
	public TicketIterator iterator(TicketTypeEnum type) {
		// TODO Auto-generated method stub
		return new TicketIteratorImpl(type, this.ticketsList);
	}
	
	public class TicketIteratorImpl implements TicketIterator {
		
		private TicketTypeEnum type;
		private List<Ticket> flightsList;	
		private int position; // position starts from 0 to flightsList.size()-1
		
		// represents all combination for (coach, firstclass, coach) ... (coach, firstclass, firstclass) 
		
		
		public TicketIteratorImpl(TicketTypeEnum type, List<Ticket> ticketsList) {
			this.type = type;
			flightsList = ticketsList;
		}
	
		
		/* (non-Javadoc)
		 * @see Iterator.FlightIterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (type.equals(TicketTypeEnum.ALL)) { 
				
				if (position < flightsList.size()) 	return true;
				return false;
					
			}  else if (type.equals(TicketTypeEnum.FIRSTCLASS)) {
			
				while (position < flightsList.size()) 	{
					
					if (flightsList.get(position).getSeatType().equals("FIRSTCLASS"))
						return true;
					position++;
					
				}
				return false;
			
			} else if (type.equals(TicketTypeEnum.COACH)) {
				
				while (position < flightsList.size()) 	{
					
					if (flightsList.get(position).getSeatType().equals("COACH"))
						return true;
					position++;
					
				}
				return false;
			
			} else if (type.equals(TicketTypeEnum.MIXED)) {
			
				while (position < flightsList.size()) 	{
					
					if (flightsList.get(position).getSeatType().equals("MIXED"))
						return true;
					position++;
					
				}
				return false;
			
			} else {
			// case of NONESTOP, ONESTOP, MULTISTOP
					
				switch (type) {
				
					case NONESTOP:
						
						while (position < flightsList.size()) {
							
							if (ticketsList.get(position).getFlightList().size() == 1) 
								return true;
							position++;
							
						}
						return false;
						
					case ONESTOP:
						
						while (position < ticketsList.size()) {
							
							if (ticketsList.get(position).getFlightList().size() == 2) 
								return true; 
							position++;
						}
						return false;
						
					case MULTISTOP:
						
						while (position < ticketsList.size()) {
							
							if (ticketsList.get(position).getFlightList().size() > 2) return true;
							position++;
						}
						return false;	
						
					default: 
						return false;
				}
				
			}
			
		}
			

		/* (non-Javadoc)
		 * @see Iterator.FlightIterator#next()
		 */
		@Override
		public Ticket next() {
			
			// TODO Auto-generated method stub
			position++;
			return flightsList.get(position-1);
			
		}

		/* (non-Javadoc)
		 * @see Iterator.FlightIterator#remove()
		 */
		@Override
		public void remove() {
			
			// TODO Auto-generated method stub
			
		}

	}


	
}
