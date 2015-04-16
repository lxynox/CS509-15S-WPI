/**
 * @author: 
 * Date: April, 05, 2015
 */

$(document).ready(function() {
	
	$("#take_off_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#take_off_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#take_off_min").attr("value", ui.values[0]),
	      $("#take_off_max").attr("value", ui.values[1]);
	    }
	});
	
	$("#landing_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#landing_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#landing_min").attr("value", ui.values[0]),
	      $("#landing_max").attr("value", ui.values[1]);
	    }
	});
	
	  
	$("#layover_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#layover_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#layover_min").attr("value", ui.values[0]),
	      $("#layover_max").attr("value", ui.values[1]);
	    }
	});
	
	  
	$("#leg_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#leg_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#leg_min").attr("value", ui.values[0]),
	      $("#leg_max").attr("value", ui.values[1]);
	    }
	});
	
	
//  Readily here is the problem we can see
	
	$("#depart_take_off_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#depart_take_off_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#depart_take_off_min").attr("value", ui.values[0]),
	      $("#depart_take_off_max").attr("value", ui.values[1]);
	    }
	});
	

	$("#return_take_off_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#return_take_off_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#return_take_off_min").attr("value", ui.values[0]),
	      $("#return_take_off_max").attr("value", ui.values[1]);
	    }
	});


	$("#depart_landing_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#depart_landing_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#depart_landing_min").attr("value", ui.values[0]),
	      $("#depart_landing_max").attr("value", ui.values[1]);
	    }
	});
	

	$("#return_landing_slider").slider({
	    range: true,
	    min: 0,
	    max: 100,
	    values: [ 0, 100 ],
	    slide: function( event, ui ) {
	      $('#return_landing_rangeval').html("range: " + ui.values[0]+"% - "+ui.values[1]+"%"),
		  $("#return_landing_min").attr("value", ui.values[0]),
	      $("#return_landing_max").attr("value", ui.values[1]);
	    }
	});
	
});
	