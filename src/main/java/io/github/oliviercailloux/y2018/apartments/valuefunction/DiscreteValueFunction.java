package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which enables the user to get the subjective value of a String given in argument.
 *
 */
public class DiscreteValueFunction<T> implements PartialValueFunction<T> {

	private Map<T, Double> subjective;
	private final static Logger LOGGER = LoggerFactory.getLogger(DiscreteValueFunction.class);

	/**
	 * Create a map with 3 strings which are associated to 3 subjective values.
	 * @param s1 first string which corresponds to the subjective value 0
	 * @param s2 second string which corresponds to the subjective value 0.5
	 * @param s3 third string which corresponds to the subjective value 1
	 */
	public DiscreteValueFunction(T s1, T s2, T s3) {
		if (s1 == s2 || s1 == s3 || s2 == s3) {
			LOGGER.error("The strings in input has to be different in the Discrete3ValueFunction class. The Map has not been set with success.");
			throw new IllegalArgumentException("The strings has to be different.");
		}
		subjective = new HashMap<>();
		subjective.put(s1, 0.0);
		subjective.put(s2, 0.5);
		subjective.put(s3, 1.0);
		LOGGER.info("The Map with the three strings has been set with success in the Discrete3ValueFunction class.");
	}

	/**
	 * Create a map with 2 strings which are associated to 2 subjective values.
	 * @param s1 first string which corresponds to the subjective value 0
	 * @param s2 third string which corresponds to the subjective value 1
	 */
	public DiscreteValueFunction(T s1, T s2) {
		if (s1 == s2) {
			LOGGER.error("The strings in input has to be different in the DiscreteValueFunction class. The Map has not been set with success.");
			throw new IllegalArgumentException("The strings has to be different.");
		}
		subjective = new HashMap<>();
		subjective.put(s1, 0.0);
		subjective.put(s2, 1.0);
		LOGGER.info("The Map with the two strings has been set with success in the DiscreteValueFunction class.");
	}
	/*
	public DiscreteValueFunction(Map<T, Double> subjective) {
		
		for ( int i = 0 ; i < subjective.size() ; i++) {
			subjective.forEach(action);
		}
	}
	*/
	@Override
	public Double apply(T objectiveData) {
		return subjective.get(objectiveData);
	}

	@Override
	public double getSubjectiveValue(T objectiveData) throws IllegalArgumentException {
		return subjective.get(objectiveData);
	}


}
