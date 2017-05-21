package main.java.plugin;

import main.java.framework.api.Scope;
import main.java.framework.api.components.IComponent;
import main.java.parser.Disharmony;
import main.java.tresholds.IThresholds;

public class DisharmonyChecker {

	/**
	 * TODO
	 * @param disharmony
	 * @param component
	 * @param thresholds
	 * @return
	 */
	public static boolean checkDisharmony(Disharmony disharmony, IComponent component, IThresholds thresholds) {
		return (component.getScope().equals(Scope.valueOf(Integer.parseInt(disharmony.getScope())))) 
				? disharmony.disharmonyDetected(component.getMeasures(), thresholds) 
						: false;
	}
}
