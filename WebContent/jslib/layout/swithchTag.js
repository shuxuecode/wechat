function leftmenu_switchTag(tag, theUrl) {
	var obj = document.getElementById(tag);
	var lis = document.getElementById(tag).parentNode.getElementsByTagName('li');
    var iframe = document.getElementById("iFrame1");
    iframe.src = theUrl;
    for (var i = 0; i < lis.length; i++) {
    	lis[i].getElementsByTagName("a")[0].className = "";
    }
    obj.getElementsByTagName("a")[0].className = "selectli";
}
