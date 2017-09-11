const baseurl = 'http://localhost:13000/api/recipes';

function fillinDetails(data) {
	let html = `
		<h1>Recipe Title: ${data.title}</h1>
		<h2>Recipe Description: ${data.description}</h2>
		<div>Recipe Time: ${data.minutes}</div>
	`;
	
	for(let ingredient of data.ingredients) {
		html += `
			<div>
				<div>${ingredient.ingredientName}</div> 
			<form class= "delete-ingredient-form" method="post" action="/api/recipes/${data.id}/ingredients/${ingredient.id}">
		        <button>Delete</button>
		    </form>
				<div>${ingredient.ingredientQuantity}</div>
				<div>${ingredient.measureUnit}</div>
			</div>
		`;
	}
	html += `
	<form id="create-ingredient-form" method="post" action="/api/recipes/${data.id}/ingredients">
		<input name="ingredientName" id="ingredientName" placeholder="ingredient name">
		<br>
		<input name="measureUnit" id="measureUnit" placeholder="measure unit">
		<br>
		<input name="ingredientQuantity" id="ingredientQuantity" placeholder="ingredient quantity">
		<br>
		<button>Add Ingredient</button>
		
	</form>

	`;
	
	for(let instruction of data.instructions) {
		html += `
			<div>
				<div>${instruction.stepNumber}</div> 			
			<form class= "delete-instruction-form" method="post" action="/api/recipes/${data.id}/instructions/${instruction.id}">
		        <button>Delete</button>
		    </form>
				<div>${instruction.instructionText}</div>
			</div>
		`;
	}
	
	html += `
	<form id="create-instruction-form" method="post" action="/api/recipes/${data.id}/instructions">
		<input name="stepNumber" id="stepNumber" placeholder="stepNumber">
		<br>
		<input name="instructionText" id="instructionText" placeholder="instruction text">
		<br>
		<br>
		<button>Add Instruction</button>
	</form>
	`;

	$('#recipe-detail').html(html);
}


function createListElement(recipe){
    $('<li></li>')
    .html(`
            <a href="#" data-recipe-id="${recipe.id}">
                    ${recipe.title}
            </a>
            <form class= "delete-recipe-form" method="post" action="/api/recipes/${recipe.id}">
                <button>Delete</button>
            </form>
            `)
            .appendTo($('#recipe-list'));
}	

$(document).on('submit', '.delete-recipe-form', function(e) {
	e.preventDefault();
	//we aren't using the form, we are crafting an http request of type delete
	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('li')
				.remove();
		})
 		.fail(error => console.error(error));
	
});

$(document).on('submit', '.delete-ingredient-form', function(e) {
	e.preventDefault();

	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
 		.fail(error => console.error(error));
	
});

$(document).on('submit', '.delete-instruction-form', function(e) {
	e.preventDefault();

	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
 		.fail(error => console.error(error));
	
});


$('#create-recipe-form').on('submit', function(e) {
	//first thing to do is prevent default behavior
	e.preventDefault();
	//create object out of values submitted in webform to be sent to the server
	let payload = {
			//same property name as you would expect entities to have
			title: $('#title').val(),
			description: $('#description').val(),
			minutes: $('#minutes').val(),
			pictureURL: $('#pictureURL').val()
			
	};
	
	//changes server behavior when data is sent to the server, creating something that is json to server
	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
		.done(function (recipe) {
			createListElement(recipe);
		})
		.fail(error => console.log(error));
	
});

$(document).on('click', 'a[data-recipe-id]', function (e) {
	e.preventDefault();
	const recipeId = $(this).data('recipeId');
	
	$.getJSON(baseurl + '/' + recipeId, function(data) {
		console.log('Data for', recipeId, 'is', data);
		data.ingredients = data.ingredients || '';
		data.instructions = data.instructions || '';
		
		fillinDetails(data);
			
	});
});

$(document).on('submit', '#create-ingredient-form', function(e) {
	e.preventDefault();
	
	let payload = {
			ingredientName: $('#ingredientName').val(),
			measureUnit: $('#measureUnit').val(),
			ingredientQuantity: $('#ingredientQuantity').val()
	};
	
	let ajaxOptions = {
			type: "POST",
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
	.done(function (ingredient) {
		fillinDetails(ingredient);
	})
	.fail(error => console.log(error));
	
});

$(document).on('submit', '#create-instruction-form', function(e) {
	e.preventDefault();
	
	let payload = {
			stepNumber: $('#stepNumber').val(),
			instructionText: $('#instructionText').val()
	};
	
	let ajaxOptions = {
			type: "POST",
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
	.done(function (instruction) {
		fillinDetails(instruction);
	})
	.fail(error => console.log(error));
	
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