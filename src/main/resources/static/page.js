const baseurl = 'http://localhost:13000/api/recipes';

//Create Recipe element
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

function fillInDetails(data) {
	
	let html = `
		<h1>${data.title}</h1>
		<h2>${data.description}</h2>
		<div>Minutes: ${data.minutes}</div>
	`;
	
	for (let instruction of data.instructions) {
		$('<li></li>')
		html += `
			<div>
				<b>${instruction.stepNumber}</b>
				<div>${instruction.instructionText}<div>
				
			<form class="delete-instruction-form" method="post" action="/api/${recipe.id}/instructions/${instruction.id}">
    		  	<button>Delete</button>
    		</form>
    		  </div>	
		`;
	}
	
	html += `
		
	<form id="create-instruction-form" method="post" action="api/recipes/${data.id}/instructions">
		Step Number:<input required name="stepNumber" id="stepNumber" placeholder="stepNumber">
		<br>
		Instruction Text:<input required name="instructionText" id="instructionText" placeholder="instructionText">
		<br><br>
		<button>Add instruction</button>
	</form><br><br>
	`;
	
	
	for (let ingredient of data.ingredients) {
		html += `
			<div>
				<b>${ingredient.ingredientName}</b>
				<div>${ingredient.measureUnit}<div>
				<div>${ingredient.ingredientQuantity}<div>
			</div>
		`;
	}
	
	html += `
		
	<form id="create-ingredient-form" method="post" action="api/recipes/${data.id}/ingredients">
		Ingredient Name: <input required name="ingredientName" id="ingredientName" placeholder="ingredientName">
		<br>
		Unit of Measurement:<input required name="measureUnit" id="measureUnit" placeholder="measureUnit">
		<br>
		Quantity:<input required name="ingredientQuantity" id="ingredientQuantity" placeholder="ingredientQuantity">
		<br><br>
		<button>Add ingredient</button>
	</form>
	`;

	$('#recipe-detail').html(html);
	
}




//Create recipe
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

//delete recipe
$(document).on('submit', '.delete-recipe-form', function (e) {
	e.preventDefault();
	
	//this crafts our own http request of delete
	$.ajax(this.action, { type: 'DELETE' }) //inline object type: DELETE; if we did a PUT, we'd need a payload
	.done(() => {
		$(this) //form element
			.closest('li') //traverse up html tree to find closest li tag
			.remove();
	})
	.fail(error => console.error(error));
});



//Create instruction
$(document).on('submit', '#create-instruction-form', function (e) {
	e.preventDefault();
	//create payload object
	let payload = {
		stepNumber: $('#stepNumber').val(),
		instructionText: $('#instructionText').val()
			
	};
	
	//create ajaxArgs object
	let ajaxArguments = {
		type: 'POST',
		data: JSON.stringify(payload),
		contentType: 'application/json'		
	};
	
	
	$.ajax(this.action, ajaxArguments)
	//equivalent to below function declaration .done((card)) => 
    .done(function (instruction) {
    	fillInDetails(instruction);
      console.log(instruction);
    })
  //arrow function syntax
    .fail(error => console.error(error)); 
});

//delete instruction
$(document).on('submit', '.delete-instruction-form', function (e) {
	e.preventDefault();
	
	//this crafts our own http request of delete
	$.ajax(this.action, { type: 'DELETE' }) //inline object type: DELETE; if we did a PUT, we'd need a payload
	.done(() => {
		$(this) //form element
			.closest('li') //traverse up html tree to find closest li tag
			.remove();
	})
	.fail(error => console.error(error));
});

//Create ingredient
$(document).on('submit', '#create-ingredient-form', function (e) {
	e.preventDefault();
	//create payload object
	let payload = {
		ingredientName: $('#ingredientName').val(),
		measureUnit: $('#measureUnit').val(),
		ingredientQuantity: $('#ingredientQuantity').val()
			
	};
	
	//create ajaxArgs object
	let ajaxArguments = {
		type: 'POST',
		data: JSON.stringify(payload),
		contentType: 'application/json'		
	};
	
	
	$.ajax(this.action, ajaxArguments)
    .done(function (ingredient) {
    	fillInDetails(ingredient);
      console.log(ingredient);
    })
  //arrow function syntax
    .fail(error => console.error(error)); 
});

		

//show details when clicking on recipe link
$(document).on('click', 'a[data-recipe-id]', function (e) {
	e.preventDefault(); 
	const recipeId = $(this).data('recipeId');
	
	
	$.getJSON(baseurl + '/' + recipeId, function(data) {
		console.log('Data for', recipeId, 'is', data);
		data.ingredients = data.ingredients || '';
		data.instructions = data.instructions || '';
		
	fillInDetails(data);
				
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
