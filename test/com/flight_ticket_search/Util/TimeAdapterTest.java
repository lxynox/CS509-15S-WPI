/**
 * 
 */
package com.flight_ticket_search.Util;

import com.flight_ticket_search.Entity.Date;
import com.flight_ticket_search.Entity.Time;

import junit.framework.TestCase;

/**
 * @author lxynox
 *
 */
public class TimeAdapterTest extends TestCase {
	TimeAdapter adapter = new TimeAdapter();
	/**
	 * Test method for {@link com.flight_ticket_search.Util.TimeAdapter#addOneDay(java.lang.String)}.
	 */
	public final void testAddOneDay() {// TODO
//		boundary case 
		assertEquals ("one day after may, 09, 2015 should be may, 10, 2015", "2015_05_10", 
				adapter.addOneDay("2015_05_09"));
//		common case
		assertEquals ("one day after may, 10, 2015 should be may, 11, 20145", "2015_05_11",
				adapter.addOneDay("2015_05_10"));
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Util.TimeAdapter#getDateFromUI(java.lang.String)}.
	 */
	public final void testGetDateFromUI() {
 // TODO
		try {
			adapter.getDateFromUI("20150510");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("invalid number format");
		} catch (DateOutOfRangeException e) {
			// TODO Auto-generated catch block
			System.out.println("date out of range");
		} catch (Exception exp) {
			System.out.println("invalid input");
		}
		finally {
//			try the right date format case
			try {
				adapter.getDateFromUI ("2015-05-10");
			} catch (Exception exp) {
				System.out.println("wrong input fault, check yoru input");
			}
		}
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Util.TimeAdapter#getTimezoneOffset(double, double)}.
	 */
	public final void testGetTimezoneOffset() {
// TODO
//		using Boston to test : W71 N 41 offset 5
		assertSame ("the offset of BOS should be -5", -5, adapter.getTimezoneOffset(41, -72));
//		using los angeles to test: W118 N34 offset -8 
		assertSame ("the offset of LAX should be -8", -8, adapter.getTimezoneOffset(34, -118));
	}

	/**
	 * Test method for {@link com.flight_ticket_search.Util.TimeAdapter#getLocalTime(com.flight_ticket_search.Entity.Time, int)}.
	 */
	public final void testGetLocalTime() {
		Time gmtTime = new Time(12, 0, new Date(2015, 5, 10));
		int offset = -5;
		assertNotSame ("returned time should not be the reference of the argument param", 
				gmtTime, adapter.getLocalTime(gmtTime, offset));
		assertEquals ("the hour of returned time should be 12 -5", 7,
				adapter.getLocalTime(gmtTime, offset).getHour());
	}

}
