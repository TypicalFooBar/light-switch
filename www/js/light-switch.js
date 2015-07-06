var templates = [];

$(function() {
	loadTemplates();
	updateLightSwitchList();
});

function loadTemplates() {
	$.get('/template/light-switch-list.template', function(data) {
		templates['light-switch-list'] = data;
	});
}

function updateLightSwitchList() {
	// AJAX call to get the light switches
	$.get("/api/switch?action=getLightSwitchList")
		.done(function(data) {
			// Parse the return json
			var lightSwitchList = JSON.parse(data);
			
			// Create the HTML based on the template
			var buttonHtml = Mustache.render(templates['light-switch-list'], lightSwitchList);
			
			// This is the div we'll put the light switch buttons in
			var buttonsDiv = $('#buttons');
			buttonsDiv.append(buttonHtml);
			
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