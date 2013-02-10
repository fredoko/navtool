// constantes
const svgNS = 'http://www.w3.org/2000/svg';

var svgBlock;
var ensemble;

window.onload = function () {
	svgBlock = document.getElementById('svgBlock');
	ensemble = document.getElementById('ensemble');
	//svgBlock.addEventListener('click', myAlert, false);
	ensemble.addEventListener('mousedown', dragDown, false);
	ensemble.addEventListener('mouseup', dragUp, false);
	ensemble.addEventListener('mousemove', dragMove, false);
	ensemble.addEventListener('DOMMouseScroll', wheel, false);
	
	
	
}


function myAlert(){
	g = document.getElementById('mafleche');
	
	alert(g);
	}

function addFleche() {
	var angle = document.forms[0].angle.value
	var vitesse = document.forms[0].vitesse.value
	scale = vitesse * 1.5 / 100 + 0.5
	x = document.forms[0].x.value
	y = document.forms[0].y.value
	nouvelleFleche = document.getElementById('mafleche').cloneNode(true);
	//nouvelleFleche.setAttributeNS(null, 'x', document.forms[0].x.value);
	//nouvelleFleche.setAttributeNS(null, 'y', document.forms[0].y.value);
	nouvelleFleche.setAttributeNS(null, 'transform', "translate(" +  x + "," + y + ") rotate(" + angle + ") scale(" + scale + ") " ); //+ getMatrixString(ensemble)
	//ensemble2 = document.getElementById('ensemble');
	ensemble.appendChild(nouvelleFleche);
	
}

function drawEarth() {
	//alert("debut:" + landPath.length)	
	var newPath;
	for (i in landPath) {
		// on crée le nouvel élément
		newPath = document.createElementNS(svgNS, "path");
		newPath.setAttributeNS(null, 'd', landPath[i]);
		newPath.setAttributeNS(null, 'id', "land");
		log(i,0,landPath[i])
		ensemble.appendChild(newPath);
	}
	
}

var dragActivated = false;
var evtX = 0;
var evtY = 0;

function dragDown(evt) {
	if(dragActivated == false) {
		dragActivated = true;
		evtX = evt.clientX;
		evtY = evt.clientY;
		log(0,0,getMatrixString(ensemble));
	}
}

function dragUp(evt) {
	if (dragActivated) {
		dragActivated = false;
	}
}

function dragMove(evt) {
	if (dragActivated) {		
		dx = evt.clientX - evtX; //  + dragInitx  
		dy = evt.clientY - evtY; //  + dragInity
		log(dx,dy, getMatrixString(ensemble))
		CTM = ensemble.getScreenCTM();
		CTM.e += dx - 8 //pourquoi ce 8 !!!!?????!!!! 
		CTM.f += dy - 8 //pourquoi ce 8 !!!!?????!!!! la marge entre le cadre svg et la page, ils devraient s'annuler au dessus si c'est le cas.
		ensemble.setAttributeNS(null, 'transform', getMatrixS(CTM) ); //+ " translate(" + dx + "," + dy + ")"
		
		evtX = evt.clientX;
		evtY = evt.clientY;
	}
}
function getMatrixString(object) {
	CTM = object.getScreenCTM();
	return "matrix(" + CTM.a + "," + CTM.b + "," + CTM.c + "," + CTM.d + "," + CTM.e + "," + CTM.f + ")";
}
function getMatrixS(CTM) {
	return "matrix(" + CTM.a + "," + CTM.b + "," + CTM.c + "," + CTM.d + "," + CTM.e + "," + CTM.f + ")";
}

var scale = 1
function wheel(event) {
	var delta = event.detail/3
	scale = 1 + delta/10
	
	CTM = ensemble.getScreenCTM();
	decx = event.clientX * delta/10 ;
	decy = event.clientY * delta/10 ;
	//log(delta,scale,"")
	ensemble.setAttributeNS(null, 'transform', getMatrixString(ensemble) + " scale(" + scale + ")");
	//décalage jusqu'au pointer, scale et recalge initial
	
	//ensemble.setAttributeNS(null, 'transform', getMatrixString(ensemble) + "translate(-" + decx + ",-" + decy + ") scale(" + scale + ")" );
		
	
	log(0,0,CTM.a + "," + CTM.b + "," + CTM.c + "," + CTM.d + "," + CTM.e + "," + CTM.f)
}

function log(x,y,txt) {
	document.forms[0].x.value = x
	document.forms[0].y.value = y
	document.forms[0].log.value = txt
}