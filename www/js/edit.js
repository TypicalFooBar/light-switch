var templates = [];
var lightSwitchList;

$(function() {
	loadTemplates();
	getLightSwitchList();
});

function initFormButtons() {
	$('#cancel-button').on('click', function() {
		location.href='/';
	});
	
	$('#save-button').on('click', function() {
		// Disable the save and cancel buttons
		$('#save-button').addClass('disabled');
		$('#cancel-button').addClass('disabled');
		
		$('.button-edit-form').each(function(index, value) {
			// Get the new values (only name should have changed)
			var id = $(this).find("input[name='id']").val();
			var name = $(this).find("input[name='name']").val();
			var active = $(this).find("input[name='active']").val();
			var pinNumber = $(this).find("input[name='pinNumber']").val();
			
			// If the name is different, update this light switch
			if (name != lightSwitchList[index].name) {
				// Create the light switch object (will JSON.stringify() later)
				var lightSwitch = {
					id: id, // The light-switch id
					name: name, // The light-switch name
					active: active, // The opposite value from the button's current 'active' state
					pinNumber: pinNumber // The GPIO pin that this light-switch controls
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
					location.href='/';
				});
			}
		});
	});
}

function loadTemplates() {
	$.get('/template/edit-form.template', function(data) {
		templates['edit-form'] = data;
	});
}

function getLightSwitchList() {
	// AJAX call to get the light switches
	$.get("/api/light-switch?action=getLightSwitchList")
		.done(function(data) {			
			// Parse the return json
			lightSwitchList = JSON.parse(data);
			
			// Create the HTML based on the template
			var buttonHtml = Mustache.render(templates['edit-form'], lightSwitchList);
			
			// This is the div we'll put the light switch info in
			var contentDiv = $('#content');
			
			// Clear the HTML
			contentDiv.html('');
			
			// Add the buttons html
			contentDiv.append(buttonHtml);
			
			// Init the form buttons now that the page is loaded
			initFormButtons();
		});
}