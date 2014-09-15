/*===============*/
$( function() {

});

// ================ ===============
function allHide() {
	document.getElementById('a').style.display = 'none';
	document.getElementById('b').style.display = 'none';
	document.getElementById('c').style.display = 'none';
}

//================   ===============
function menuChange() {
	var x = document.getElementById("mySelect");
	if (x.options[0].selected == true) {
		allHide();
	}
	if (x.options[1].selected == true) {
		allHide();
		document.getElementById('a').style.display = 'block';
	}
	if (x.options[2].selected == true) {
		allHide();
		document.getElementById('b').style.display = 'block';
	}
	if (x.options[3].selected == true) {
		allHide();
		document.getElementById('c').style.display = 'block';
	}

}

//================   ===============
//================   ===============
//================   ===============