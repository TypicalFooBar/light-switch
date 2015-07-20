var templates = [];

$(function() {
	loadTemplates();
	getLightSwitchList();
});

function initFormButtons() {
	$('#cancel-button').on('click', function() {
		location.href="/";
	});
	
	$('#save-button').on('click', function() {
		location.href="/";
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
			var lightSwitchList = JSON.parse(data);
			
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