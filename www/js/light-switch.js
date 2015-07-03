$(function() {
	getLightSwitches();
});

function getLightSwitches() {
	// AJAX call to get the light switches
	$.get("/api/switch?action=getLightSwitches")
		.done(function(data) {
			// This is the div we'll put the light switch buttons in
			var buttonsDiv = $('#buttons');
			
			// Parse the return json
			var lightSwitchList = JSON.parse(data);
			
			// For each light switch
			$.each(lightSwitchList, function(index, value) {
				// Start the button html
				var buttonHtml = '<button class="massive toggle ui button';
				
				// If the light switch is on
				if (value.on) {
					buttonHtml += ' active';
				}
				
				buttonHtml += '">' + value.name + '</button>'; 
				
				buttonsDiv.append(buttonHtml);
			});
			
			// Now let's initialize these buttons
			initButtons();
		});
}

function initButtons() {
	$('*.ui.button').each(function() {
		var button = $(this);
		
		$(this).on('click', function() {
			button.toggleClass('active');
		});
	});
}