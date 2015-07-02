$(function() {
	$.get("/api/switch")
		.done(function(data) {
			alert(data);
		});
});