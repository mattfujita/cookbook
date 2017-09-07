const baseurl = 'http://localhost:13000/api/recipes';

$(document).on('click', 'a[data-recipe-id]', function (e) {
	e.preventDefault();
	const recipeId = $(this).data('recipeId');
	
	$.getJSON(baseurl + '/' + recipeId, function(data) {
		console.log('Data for', recipeId, 'is', data);
		data.ingredients = data.ingredients || '';
		data.instructions = data.instructions || '';
		$('#recipe-detail')
			.html(`
					<h1>${data.title}</h1>
					<h2>${data.description}</h2>
					<div>${data.minutes}</div>
					<div>${data.ingredients}</div>
					<div>${data.instructions}</div>
			`);
			
	});
});

$.getJSON(baseurl, function (data) {
	console.log('I got data:', data);
	if(data.length) {
		for(let recipe of data) {
			$('<li></li>')
			.html('<a href="#" data-recipe-id="' + recipe.id + '">' + recipe.title + '</a>')
			.appendTo($('#recipe-list'));
		}
	} else {
		$('<li></li>')
			.css('color', 'red')
			.html('You have no data.')
			.appendTo($('#recipe-list'));
	}
});