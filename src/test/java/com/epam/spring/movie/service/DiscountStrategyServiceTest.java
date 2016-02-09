package com.epam.spring.movie.service;

import static org.junit.Assert.*;

import java.util.Dictionary;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.spring.movie.AbstractTestCase;
import com.epam.spring.movie.bean.DiscountStrategy;
import com.epam.spring.movie.bean.Ticket;
import com.epam.spring.movie.bean.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscountStrategyServiceTest extends AbstractTestCase {

	public static int testCounter = 0;
	
	@Autowired
	@Qualifier("new_ticket_7")
	private Ticket newTicketFirst;
	
	//@Autowired
	private Ticket newTicketSecond;
	
	@Autowired
	private TicketService ticketService;
	
	
	@Autowired
	@Qualifier("user_0")
	private User userFirst;
	
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@Autowired
	private DiscountStrategyService discountService;
	
	public void setDiscountService(DiscountStrategyService discountService) {
		this.discountService = discountService;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("\n*********************************************** Discount strategy Tests --->");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("\nTest # " + ++testCounter);
	}

	@Test
	public void testGetAll() {
		System.out.println("Get all strategies :");
		List<DiscountStrategy> list = discountService.getAll();
		list.forEach(System.out::println);
		assertTrue(list.size() > 0);
	}

	@Test
	public void testGetListByName() {
		DiscountStrategy discount = discountService.getAll().get(0);
		System.out.println("Get discount by name: " + discount.getName());
		DiscountStrategy found = discountService.getListByName(discount.getName()).get(0);
		System.out.println(found);
		assertEquals(discount.getName(), found.getName());
	}

	@Test
	public void testGetBestDiscountStrategyNotAuthirizedUser() {
		System.out.println("Get discount strategy for not authirized :");
		DiscountStrategy discount = discountService.getBestDiscountStrategy(new Ticket(), 0);
		System.out.println(discount);
		assertNull(discount);
	}
	
	@Test
	public void testGetBestDiscountStrategyForUserFirstCase() {
		User userFirst = newTicketFirst.getUser();
		System.out.println("Get discount strategy for user : " + userFirst);
		System.out.println("Booking time:" + newTicketFirst.getDateTime());
		
		long count = ticketService.getCountOfTicketsForUser(userFirst);
		System.out.println("Number tickets user bought before:" + count);
		
		DiscountStrategy discount = discountService.getBestDiscountStrategy(newTicketFirst, count);
		System.out.println(discount);
		
		assertEquals(DiscountStrategy.BIRTHDAY_DISCOUNT, discount.getName());
	}
	
	@Test
	public void testGetBestDiscountStrategyForUserSecondCase() {
		User userFirst = newTicketFirst.getUser();
		System.out.println("Get discount strategy for user : " + userFirst);
		System.out.println("Booking time:" + newTicketFirst.getDateTime());
		
		long count = 3;
		System.out.println("Number tickets user bought before:" + count);
		
		DiscountStrategy discount = discountService.getBestDiscountStrategy(newTicketFirst, count);
		System.out.println(discount);
		
		assertEquals(DiscountStrategy.EVERY_TICKET_DISCOUNT, discount.getName());
	}
	
	@Test
	public void testGetBestDiscountStrategyForUserThirdCase() throws CloneNotSupportedException {
		Ticket ticket = (Ticket) newTicketFirst.clone();
		ticket.setUser(userFirst);
		
		System.out.println("Get discount strategy for user : " + userFirst);
		System.out.println("Booking time:" + newTicketFirst.getDateTime());
		
		long count = ticketService.getCountOfTicketsForUser(userFirst);
		System.out.println("Number tickets user bought before:" + count);
		
		DiscountStrategy discount = discountService.getBestDiscountStrategy(ticket, count);
		System.out.println(discount);
		
		assertNull(discount);
	}	
	
	@Test
	public void testGetBestDiscountStrategyForUserFourthCase() throws CloneNotSupportedException {
		Ticket ticket = (Ticket) newTicketFirst.clone();
		ticket.setUser(userFirst);
		
		System.out.println("Get discount strategy for user : " + userFirst);
		System.out.println("Booking time:" + newTicketFirst.getDateTime());
		
		long count = 3;
		System.out.println("Number tickets user bought before:" + count);
		
		DiscountStrategy discount = discountService.getBestDiscountStrategy(ticket, count);
		System.out.println(discount);
		
		assertEquals(DiscountStrategy.EVERY_TICKET_DISCOUNT, discount.getName());
	}	
}
