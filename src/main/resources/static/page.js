console.log('I am here');
const baseurl = 'http://localhost:13000/api/recipes'
	
	$(document).on('click', 'a[data-recipe-id]', function (e) {
		e.preventDefault(); //this prevents the page from following Recipe link when clicked
		//console.log(this); //this is the anchor tag that caused the event handler e
		const recipeId = $(this).data('recipeId'); //data function will take data-recipe-id 
												//and look for first -, then make it camelCase; makes it cardId
		$.getJSON(baseurl + '/' + recipeId, function (data) {
			//console.log('Data for', recipeId, 'is', data);
			data.title = data.title || '<i>no title specified</i>'; //if one side of or is truthey, it doesn't evaluate other; if data.title is null, its falsey, so it evaluates other side
			data.description = data.description || '<i>no description specified</i>';
			data.minutes = data.minutes || '<i>no minutes specified</i>';
			$('#recipe-detail')
				.html(`
				<h1>${data.title}</h1>
				<div>Description: ${data.description}</div>
				<div>Minutes: ${data.minutes}</div>
				<div>Ingredients: ${data.ingredients}</div>
				<div>Instructions: ${data.instructions}</div>
				`);
		});
});
	
	
	$.getJSON(baseurl, function( data ) {
		if (data.length){
			for (let recipe of data) {
				$('<li></li>')	//selector - always select by id or class or tag name
				.html('<a href="#" data-recipe-id="' + recipe.id + '">' + recipe.title + '</a>') //functions to modify it
				.appendTo($('#cookbook-list'));
			}
			
		} else {
			$('<li></li>')
				.css('color', 'red')
				.html('There is no data!')
				.appendTo($('#cookbook-list'));
		}
	});
	