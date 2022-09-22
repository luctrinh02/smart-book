$(document).ready(function() {
	bookIndex();
});

function typeIndex() {
	$.ajax({
		url: "/admin/smart-book/type",
		type: "GET",
		success: function(response) {
			$('#main').html(response);
		},
	});
}
function bookIndex() {
	$.ajax({
		url: "/admin/smart-book/book",
		type: "GET",
		success: function(response) {
			$('#main').html(response);
		},
	});
}