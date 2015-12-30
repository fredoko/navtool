package org.ffo.sail.windtool;

public interface Interpolable {
	
	double getValue();
	
	double getInterpolation(Interpolable autreBorne);

}
