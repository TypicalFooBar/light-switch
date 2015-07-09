var templates = [];

$(function() {
	loadTemplates();
	getLightSwitchList();
});

function loadTemplates() {
	$.get('/template/light-switch-list.template', function(data) {
		templates['light-switch-list'] = data;
	});
}

function getLightSwitchList() {
	// AJAX call to get the light switches
	$.get("/api/light-switch?action=getLightSwitchList")
		.done(function(data) {			
			// Parse the return json
			var lightSwitchList = JSON.parse(data);
			
			// Create the HTML based on the template
			var buttonHtml = Mustache.render(templates['light-switch-list'], lightSwitchList);
			
			// This is the div we'll put the light switch buttons in
			var buttonsDiv = $('#buttons');
			
			// Clear the HTML
			buttonsDiv.html('');
			
			// Add the buttons html
			buttonsDiv.append(buttonHtml);
			
			// Now let's initialize these buttons
			initButtons();
		});
}

/**
 * Initializes all light-switch buttons currently on the screen
 */
function initButtons() {
	// Loop through each light-switch button
	$('*.light-switch').each(function() {
		// The current button
		var button = $(this);
		
		// Set this button's click handler
		$(this).on('click', function() {
			// First, disable each of the light-switch buttons
			$('*.light-switch').each(function() {
				$(this).addClass('disabled');
			});

			// Create the light switch object (will JSON.stringify() later)
			var lightSwitch = {
				id: button.attr('data-id'), // The light-switch id
				name: button.attr('data-name'), // The light-switch name
				active: !button.hasClass('active'), // The opposite value from the button's current 'active' state
				pinNumber: button.attr('data-pinNumber') // The GPIO pin that this light-switch controls
			};
			
			// AJAX call to update the button's status on the server
			$.ajax({
				url: '/api/light-switch?action=updateLightSwitch',
				type: 'get',
				data: {
					lightSwitch: JSON.stringify(lightSwitch)
				}
			})
			.done(function(data) {
				getLightSwitchList();
			});
		});
	});
}