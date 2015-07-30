function iniPannel () {
	var pannel = d3.select('#pannel');
}

function iniLoadingPannel() {

	var form = d3.select('#pannel')
		.append('div')
		.attr('id', 'form');

	var choicePoint = form.append('div')
		.attr('id', 'file-choice-point');

	choicePoint.append('button')
		.attr('type', 'button')
		.attr('class', 'btn btn-default')
		.text('Test')
		.on('click', function () {
			invokeJavaMethod('main.UI.JarFileChooser', 'chooseAndLoad', null);
		});
}