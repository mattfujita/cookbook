const baseurl = 'http://localhost:13000/api/recipes';

function createListElement(recipe){
	  $('<li></li>')
      .html(`
    		  <a href="#" data-recipe-id="${recipe.id}">
    		  		${recipe.title}
    		  </a>
    		  <form class="delete-recipe-form" method="post" action="/api/recipes/${recipe.id}">
    		  	<button>Delete</button>
    		  </form>
    		  `)
    		  .appendTo($('#recipe-list'));
}

$(document).on('submit', '.delete-recipe-form', function (e) {
	e.preventDefault();
	
	//this crafts our own http request of delete
	$.ajax(this.action, { type: 'DELETE' }) //inline object type: DELETE; if we did a PUT, we'd need a payload
	.done(() => {
		$(this) 
			.closest('li')
			.remove();
	})
	.fail(error => console.error(error));
});

//capture click event on form
$('#create-recipe-form').on('submit', function (e) { 
	e.preventDefault(); //prevent default action on button click
	console.log(e);
	
	//define a brand new object
	let payload = {
		title: $('#title').val(),
		description: $('#description').val(),
		minutes: $('#minutes').val(),
		pictureURL: $('#pictureURL').val()
	};
	//console.log(payload); //validate

	let ajaxArguments = {
	type: 'POST', //http method
	data: JSON.stringify(payload), //convert payload object to string
	contentType: 'application/json' //telling server we're passing JSON changes way server behaves when getting info
	};
	
	$.ajax(this.action, ajaxArguments)
	//equivalent to below function declaration .done((card)) => 
    .done(function (recipe) {
    	createListElement(recipe);
      
    })
    //arrow function syntax
    .fail(error => console.error(error)); 
	
});

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
					<div><img src=${data.pictureURL} /></div>
					<div>${data.ingredients}</div>
					<div>${data.instructions}</div>
			`);
			
	});
});

$.getJSON(baseurl, function (data) {
	console.log('I got data:', data);
	if(data.length) {
		for(let recipe of data) {
			createListElement(recipe);
		}
	} else {
		$('<li></li>')
			.css('color', 'red')
			.html('You have no data.')
			.appendTo($('#recipe-list'));
	}
});
