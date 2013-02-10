
//lat et lon sont exprimés en radians
function PointGps(lat, lon) { 
    this.lat = lat; 
    this.lon = lon;

	this.setX = function (x) {this.lon = x};
	this.setY = function (y) {this.lat = y};
	this.getY = function () {return this.lat};
	this.getX = function () {return this.lon};
}

//Retourne un tableau de polygon  ou plutôt ligne brisées à la map des oceans
//le format est un tableau de tableau de PointGps.
function loadOceanMap(dataSet, functionVisible) {
	var oceanPolygons = []
	var polygon = []
	var pt;
	for (i in dataSet) {
		polygon = []
		for ( j in dataSet[i]) {
			//pt = new PointGps(dataSet[i][j][0],dataSet[i][j][1])
			pt = new PointGps(dataSet[i][j][1],dataSet[i][j][0])
			polygon.push(pt)
		}
		oceanPolygons.push(polygon)
	}
	return oceanPolygons;
}


