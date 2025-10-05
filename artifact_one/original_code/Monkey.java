package grazioso;

public class Monkey extends RescueAnimal {

    // Instance variable
    private String species;
    private String height;
    private String bodyLength;
    private String tailLength;

    // Constructor
    public Monkey(String name, String species, String gender, String age,
    String weight, String acquisitionDate, String acquisitionCountry,
	String trainingStatus, boolean reserved, String inServiceCountry,
	String height, String bodyLength, String tailLength) {
        setName(name);
        setSpecies(species);
        setGender(gender);
        setAge(age);
        setWeight(weight);
        setAcquisitionDate(acquisitionDate);
        setAcquisitionLocation(acquisitionCountry);
        setTrainingStatus(trainingStatus);
        setReserved(reserved);
        setInServiceCountry(inServiceCountry);
        setHeight(height);
        setBodyLength(bodyLength);
        setTailLength(tailLength);
    }
    //tail length, height, body length, and species.
    // Accessor Method
    public String getSpecies() {
        return species;
    }
    
    public String getHeight() {
    	return height;
    }
    
    public String getBodyLength() {
    	return bodyLength;
    }
    
    public String getTailLength() {
    	return tailLength;
    }
    

    // Mutator Method
    public void setSpecies(String monkeySpecies) {
    	species = monkeySpecies;
    }
    
    public void setHeight(String height) {
    	this.height = height;
    }
    
    public void setBodyLength(String bodyLength) {
    	this.bodyLength = bodyLength;
    }
    
    public void setTailLength(String tailLength) {
    	this.tailLength = tailLength;
    }

}