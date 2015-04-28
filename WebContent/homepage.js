/**
 * @author: lxybi_000
 * Date: 25, march, 2015
 * Description: js source file for homepage.html
 */

var ticketDiv = document.getElementById("empty_div");
//ticketDiv.innerHTML = document.getElementById("single_ticket").innerHTML;
var ticket= {
    ul_one: document.getElementById("oneWay"),
    ul_round: document.getElementById("roundTrip"),
    ul_multi: document.getElementById("multiDest"),
    oneWay: function() {
       document.getElementById("single_ticket").style.display = "block";
       document.getElementById("round_ticket").style.display = "none";
       document.getElementById("multi_ticket").style.display = "none";
       this.ul_one.style.color = 'black';
       this.ul_one.style.backgroundColor = 'darkgray';
       this.ul_one.style.borderBottom = "7px solid darkgray";
       this.ul_round.style.backgroundColor = 'lightgray';
       this.ul_round.style.borderBottom = "0";
       this.ul_round.style.color = 'navy';
       this.ul_multi.style.backgroundColor = 'lightgray';
       this.ul_multi.style.borderBottom = "0";
       this.ul_multi.style.color = 'navy';
	   },
    roundTrip: function() {
       document.getElementById("round_ticket").style.display = "block";
       document.getElementById("single_ticket").style.display = "none";
       document.getElementById("multi_ticket").style.display = "none";
       this.ul_one.style.backgroundColor = 'lightgray';
       this.ul_one.style.color = 'navy';
       this.ul_one.style.borderBottom = "0";
       this.ul_round.style.backgroundColor = 'darkgray';
       this.ul_round.style.color = 'black';
       this.ul_round.style.borderBottom = "7px solid darkgray";
       this.ul_multi.style.color = 'navy';
       this.ul_multi.style.backgroundColor = 'lightgray';
       this.ul_multi.style.borderBottom = "0";
	   },
    multiDest: function() {
       document.getElementById("multi_ticket").style.display = "block";
       document.getElementById("single_ticket").style.display = "none";
       document.getElementById("round_ticket").style.display = "none";
       this.ul_one.style.backgroundColor = 'lightgray';
       this.ul_one.style.color = 'navy';
       this.ul_one.style.borderBottom = "0";
       this.ul_multi.style.backgroundColor = 'darkgray';
       this.ul_multi.style.color = 'black';
       this.ul_multi.style.borderBottom = "7px solid darkgray";   
       this.ul_round.style.backgroundColor = 'lightgray';
       this.ul_round.style.color = 'navy';
       this.ul_round.style.borderBottom = "0";}
}
// add_flight/ del_flight function to update 
var flight = {
	flights : 0,
	invisible_div1 : document.getElementById("add_div1"),
	invisible_div2 : document.getElementById("add_div2"),
	addFlight : function() {
		if(this.flights < 2) {
			this.flights += 1;
			if(this.flights == 1) {
				this.invisible_div1.style.display = "block";
				this.invisible_div2.style.display = "none";
			}else{
				this.invisible_div2.style.display = "block";
			}
		}
	},
	delFlight : function() {
		if(this.flights > 0) {
			this.flights -= 1;
			if(this.flights == 0) {
				this.invisible_div1.style.display = "none";
			}else {
				this.invisible_div1.style.display = "block";
				this.invisible_div2.style.display = "none";
			}
		}
	}
}

$(function() {
	//alert("Welcome to cs509 Team07 online flight ticket reservation system");
	$("#header").click(function() {
		alert("You have clicked on me, auch ~~~ ");
	});

	// focues and blur functions for the user input 
	$(".single_ticket_input").focus(function() {
		$(this).css("background-color", "white");
	});
	$(".round_ticket_input").focus(function() {
		$(this).css("background-color", "white");
	});
	$(".multi_ticket_input").focus(function() {
		$(this).css("background-color", "white");
	});
	
	$(".single_ticket_input").blur(function() {
		$(this).css("background-color", "cadetblue");
	});
	$(".round_ticket_input").blur(function() {
		$(this).css("background-color", "cadetblue");
	});
	$(".multi_ticket_input").blur(function() {
		$(this).css("background-color", "cadetblue");
	});
	
	// login button hover function
	$("#login_option").hover(
	function(){
		$(this).css({"color": "orange", "font-size": "2em"});
	},
	function(){
		$(this).css({"color": "orange", "font-size": "1.6em"});
	});
	
	// navigation bar and its sub navigators
	$("#hotel").hover(function() {
		$(this).children().slideToggle(800);
		$("#car ul").slideUp(800);
		$("#package ul").slideUp(800);
	});
	$("#car").hover(function() {
		$(this).children().slideToggle(800);
		$("#hotel").children().slideUp(800);
		$("#package").children().slideUp(800);
	});
	$("#package").hover(function() {
		$("#hotel").children().slideUp(800);
		$("#car").children().slideUp(800);
		$(this).children().slideToggle(800);
	});
	
	// help option implementation
	$("#help_option").click(function() {
		$("ul.nav_sub").slideToggle();
	});
	
});

document.getElementById("footer").innerHTML = "<p>@copy, "+ new Date()+ "cs509_team07, All rights reserved.</p>";
