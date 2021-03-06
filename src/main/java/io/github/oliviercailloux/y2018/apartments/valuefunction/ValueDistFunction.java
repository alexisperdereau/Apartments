package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.model.LatLng;

import io.github.oliviercailloux.y2018.apartments.distance.DistanceSubway;

/**
 * This class enables the user to calculate the utility of a location by linear interpolation,
 * to have the maximum duration between the interest places.
 */
public class ValueDistFunction implements PartialValueFunction<LatLng> {
	
	private Map<LatLng, Double> interestlocation;
	private LatLng appartlocation;
	private double maxDuration;
	private String apiKey;
	private final static Logger LOGGER = LoggerFactory.getLogger(ValueDistFunction.class);
	
	
	/**
	 * Initializes the different variables of the ValueDistFunction class.
	 * @param appartlocation Object LatLng which represents the apartment location.
	 */
	public ValueDistFunction(LatLng appartlocation, String apiKey){
		interestlocation = new HashMap<>();
		this.appartlocation = appartlocation;
		this.apiKey = apiKey;
		maxDuration = 0;
	}
	
	/**
	 * Add the apartment location and its utility to the HashMap and update the variable maxDuration.
	 * @param interest Object LatLng of an interest place of the user.
	 * @throws Exception 
	 */
	public void addInterestLocation(LatLng interest) throws Exception {
		double currentdistance = calculateDistanceLocation(interest);
		if (currentdistance > maxDuration)
			maxDuration = currentdistance;
		double utility = 1-setUtility(currentdistance);
		interestlocation.put(interest, utility);
		LOGGER.info("The interest location ("+interest+") with the utility "+utility+" has been had with success in the Map.");
	}
	
	/**
	 * 
	 * @return a double which corresponds to the maximum of the duration between an interest place and the apartment.
	 */
	public double getMaxDuration() {		
		return maxDuration;
	}
	
	/**
	 * 
	 * @param interest
	 * @return double number  which corresponds to the distance (seconds) between the Location appartocation and the Location interest in parameter.
	 * @throws Exception 
	 */
	public double calculateDistanceLocation(LatLng interest) throws Exception {
		DistanceSubway dist = new DistanceSubway(interest,appartlocation,apiKey);
		double currentdistance = dist.calculateDistanceAddress(DistanceMode.COORDINATE);
		LOGGER.info("The distance between "+interest+" and "+appartlocation+" has been calculated and is equal to "+ currentdistance);
		return currentdistance;

	}
	
	/**
	 * 
	 * @param currentdistance double distance in seconds.
	 * @return a double corresponding to the utility of the distance.
	 */
	public double setUtility(double currentdistance) {
		LinearValueFunction f = new LinearValueFunction(0,36000);
		return f.getSubjectiveValue(currentdistance);
	}

	@Override
	public double getSubjectiveValue(LatLng objectiveData) {
		if (interestlocation.containsKey(objectiveData)==false) {
			LOGGER.error("Impossible to return the subjective value of the key "+objectiveData+" because the map doestn't contain this key.");
			throw new IllegalArgumentException("The map doestn't contain the key "+objectiveData);
		}
		return interestlocation.get(objectiveData);
	}
	
	@Override
	public Double apply(LatLng objectiveData) {
		return getSubjectiveValue(objectiveData);
	}

}
