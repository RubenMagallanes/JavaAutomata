function iniMainMenu(){
	var buttonGroup = d3.select('#menu')
		.append('div')
		.attr('class', 'btn-group-vertical')
		.attr('role', 'group')
		.attr('aria-label', 'MAIN MENU');

	buttonGroup.append('button')
		.attr('type', 'button')
		.attr('class', 'btn btn-default')
		.text('Form for Jar ....')
		.on('click', function () {
			iniLoadingPannel();
		});
}