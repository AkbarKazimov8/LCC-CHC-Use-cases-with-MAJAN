package chc.problem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dbscan.Gui;



public class CHCSimilaritiesFinder {
	static double[][] dst = new double[][] {
	//   0  1      2      3       4      5      6      7      8      9 
		{0, 4.472, 7.071, 4.242, 7.211, 4.242, 4.472, 4.123, 6.082, 8.062},
		{4.472, 0, 4.242, 5.099, 2.828, 1.414, 6.324, 3.605, 2.236, 6.708},
		{7.071, 4.242, 0, 4.472, 5.099, 5.656, 5.830, 7.810, 2.236, 3.0},
		{4.242, 5.099, 4.472, 0, 7.615, 6.0, 1.414, 7.280, 5.0, 4.123 },
		{7.211, 2.828, 5.099, 7.615, 0, 3.162, 8.944, 5.0, 3.0, 8.062},
		{4.242, 1.414, 5.656, 6.0, 3.162, 0, 7.071, 2.236, 3.605, 8.062},
		{4.472, 6.324, 5.830, 1.414, 8.944, 7.071, 0, 8.062, 6.403, 5.0},
		{4.123, 3.605, 7.810, 7.280, 5.0, 2.236, 8.062, 0, 5.830, 10},
		{6.082, 2.236, 2.236, 5.0, 3.0, 3.605, 6.403, 5.830, 0, 5.099},
		{8.062, 6.708, 3.0, 4.123, 8.062, 8.062, 5.0, 10, 5.099, 0}
	};
	
	 // result: 0,3,3,2,3,3,2,3,3,3
	
	static double[][] dst2 = new double[][] {
		{0, 3.063, 1.806, 1.851, 2.834, 1.940, 1.510, 2.834, 4.131, 1.445},
		{3.063, 0, 5.483, 1.805, 2.624, 1.076, 2.690, 6.483, 5.781, 1.670},
		{1.806, 5.483, 0, 2.405, 1.363, 0.822, 5.848, 3.322, 6.955, 5.728},
		{1.851, 1.805, 2.405, 0, 2.468, 0.522, 0.559, 2.255, 2.255, 1.851},
		{2.834, 2.624, 1.363, 2.468, 0, 0.972, 5.086, 3.862, 3.194, 4.729},
		{1.940, 1.076, 0.822, 0.522, 0.972, 0, 0.736, 0.833, 1.634, 2.367},
		{1.510, 2.690, 5.848, 0.559, 5.086, 0.736, 0, 6.973, 9.343, 0.562},
		{2.834, 6.483, 3.322, 2.255, 3.862, 0.833, 6.973, 0, 6.471, 3.881},
		{4.131, 5.781, 6.955, 2.255, 3.194, 1.634, 9.343, 6.471, 0, 5.816},
		{1.445, 1.670, 5.728, 1.851, 4.729, 2.367, 0.562, 3.881, 5.816, 0}
	};
	
	// result: 0,0,0,0,0,0,0,0,0,0
	
	private final static double unmatchDistanceScore = 1;
	private final static double matchDistanceScore = 0.00;

	public static void main(String[] args) {
		

		String inputPath = "C:\\Users\\akka02\\Desktop\\Master-Thesis\\Software\\EclipseWorkspace"
				+ "\\HDBSCANStar_FRAMEWORK_JAVA_Code_with_Visualization\\HDBSCAN_Star\\Input-Output\\input2.txt";

		CHCInput chcInput=new CHCInput();
		double[][] distanceScores = null;
		try {
			List<Agent> agents = chcInput.readInputFromFile(inputPath);
			//double[][] asymmetricCompatibilityScores = new double[agents.size()][agents.size()];
			distanceScores = new double[agents.size()][agents.size()];

			CHCSimilaritiesFinder chcSimilaritiesFinder=new CHCSimilaritiesFinder();
			int k = 0;
			for (int i = 0; i < agents.size(); i++) {
				for (int j = i; j < agents.size(); j++) {
					if(i==j) {
						
						distanceScores[agents.get(i).getId()-1][agents.get(j).getId()-1] = 0;
					}else {
						double distanceScore1 = chcSimilaritiesFinder.computeDistanceScore(agents.get(i), agents.get(j));
						double distanceScore2 = chcSimilaritiesFinder.computeDistanceScore(agents.get(j), agents.get(i)); 
						double reciprocalScore = chcSimilaritiesFinder.computeReciprocalScore(
								distanceScore1, 
								distanceScore2);
						//reciprocalScore = reciprocalScore*100;
						//k++;
					//	if(k%3==0) {
						//	reciprocalScore=15;
					//	}
					//	if(k%5==0) {
					//		reciprocalScore=0;
					//	}
						
						System.out.println((agents.get(i).getId())+" vs "+(agents.get(j).getId())+": " + distanceScore1);
						System.out.println((agents.get(j).getId())+" vs "+(agents.get(i).getId())+": " + distanceScore2);
						System.out.println((agents.get(i).getId())+" vs "+(agents.get(j).getId())+" r score: " +  reciprocalScore);
						distanceScores[agents.get(i).getId()-1][agents.get(j).getId()-1]=reciprocalScore;
						distanceScores[agents.get(j).getId()-1][agents.get(i).getId()-1]=reciprocalScore;
						
					}
				}	
			}
			
			//chcSimilaritiesFinder.printCompatibilityScores(compatibilityScores);
			/*for(int i=0;i<agents.size();i++) {
				for(int j=0;j<agents.size();j++) {
					if(i==j) {
						asymmetricCompatibilityScores[agents.get(i).getId()-1][agents.get(j).getId()-1]=1;
					}else {
						asymmetricCompatibilityScores[agents.get(i).getId()-1][agents.get(j).getId()-1]=
								chcSimilaritiesFinder.computeCompatibilityScore(agents.get(i), agents.get(j));
					}
					
				}
			}*/
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println("*********************************");
		System.out.println("{");
		for (int i = 0; i < distanceScores.length; i++) {
			System.out.print("{");
			for (int j = 0; j < distanceScores.length; j++) {
				System.out.print(distanceScores[i][j]+", ");
			}
			System.out.println("},");
		}
		System.out.println("}");
		System.out.println("*********************************");
		*/
		Gui gui=new Gui();
		gui.execute(distanceScores, 2, unmatchDistanceScore*2);
		
		//HDBSCANStarRunner hdbscanStarRunner = new HDBSCANStarRunner();
		System.out.println(distanceScores.length);
		
		//int[] clusetring = hdbscanStarRunner.execute(distanceScores, null, 1, 3);
		System.out.println("--------------final-------");
		Map<Integer, Integer> clusters = new HashMap<>();
		//for (int i = 0; i < clusetring.length; i++) {
//			System.out.println("agent"+(i+1)+" is in cluster #"+clusetring[i]);
//			if(clusters.containsKey(clusetring[i])) {
//				clusters.replace(clusetring[i], clusters.get(clusetring[i])+1);
//			}else {
//				clusters.put(clusetring[i], 1);
//			}
	//	}
		System.out.println("--------------final-------");
		
		for (Entry<Integer, Integer> entry : clusters.entrySet()) {
			System.out.println("Cluster#"+entry.getKey() + " has "+entry.getValue() + " elements.");
		}
		System.out.println("There are "+clusters.size() + " clusters");


		/*try {
			Utils.writeRandomCHCInputToFile(500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	/*27-2
agent1 is in cluster #3
agent2 is in cluster #0
agent3 is in cluster #0
agent4 is in cluster #2
agent5 is in cluster #0
agent6 is in cluster #2
agent7 is in cluster #3
agent8 is in cluster #0
agent9 is in cluster #0
agent10 is in cluster #3
agent11 is in cluster #3
agent12 is in cluster #3
--------------final-------
Cluster#0 has 5 elements.
Cluster#2 has 2 elements.
Cluster#3 has 5 elements.
There are 3 clusters
	*/
		/*
		 * 1. Read input and create agents
		 * 2. Compute Assymetric Similarity Matrix
		 * 3. Compute Symmetric Similarity Matrix
		 * */
	}
	private void printDistanceScores(double[][] distanceScores) {
		for (int i = 0; i < distanceScores.length; i++) {
			for (int j = 0; j < distanceScores.length; j++) {
				System.out.println("agent"+(i+1)+" vs agent"+(j+1)+" score:"+distanceScores[i][j]);
			}
		}
	}
	private double computeReciprocalScore(double distanceScore1, double distanceScore2) {
		
		double	reciprocalScore = 2/((1/distanceScore1)+(1/distanceScore2));

		/*double reciprocalScore = 0;
		
		
		if(compatibilityScore1>0 && compatibilityScore2>0) {
			reciprocalScore = 2/((1/compatibilityScore1)+(1/compatibilityScore2));
		}
		
		*/
		return reciprocalScore;
	}
	
	private double computeDistanceScore(Agent subjectAgent, Agent objectAgent) {
		// match each preference attribute of subjectAgent with the corresponding attribute of objectAgent
		// 
		double distanceScore=0.000001;
		System.out.println("------------Agent"+subjectAgent.getId()+" vs Agent"+objectAgent.getId()+"---------------");
		double temp = computeAgePrefSimilarity(subjectAgent, objectAgent)*CHCInput.ageWeight;
		//System.out.println("Age:"+temp);
		distanceScore+= temp;
		temp=computeGenderPrefSimilarity(subjectAgent, objectAgent)*CHCInput.genderWeight;
		//System.out.println("gender:"+temp);
		distanceScore+= temp;
		temp=computeFamilyPrefSimilarity(subjectAgent, objectAgent)*CHCInput.familyWeight;
		//System.out.println("family:"+temp);
		distanceScore+= temp;
		temp = computeNationPrefSimilarity(subjectAgent, objectAgent)*CHCInput.nationWeight;
		//System.out.println("Nation:"+temp);
		distanceScore+= temp;
		temp=computeReligionPrefSimilarity(subjectAgent, objectAgent)*CHCInput.religionWeight;
		//System.out.println("Religion:"+temp);
		distanceScore+= temp;
		temp = computeEthnicPrefSimilarity(subjectAgent, objectAgent)*CHCInput.ethnicWeight;
		//System.out.println("Ethnic:"+temp);
		distanceScore+= temp;
		temp = computeLocationPrefSimilarity(subjectAgent, objectAgent)*CHCInput.locationWeight;
		//System.out.println("Location:"+temp);
		distanceScore+= temp;
		temp = computeAccessPrefSimilarity(subjectAgent, objectAgent)*CHCInput.accessWeight;
		//System.out.println("Access:"+temp);
		distanceScore+= temp;
		temp=computeRentPeriodPrefSimilarity(subjectAgent, objectAgent)*CHCInput.rentPeriodWeight;
		System.out.println("Rent:"+temp);
		distanceScore+= temp;
		temp=computeShareWithPrefSimilarity(subjectAgent, objectAgent)*CHCInput.shareWithWeight;
		System.out.println("Share:"+temp);
		distanceScore+= temp;
		
		// there are 10 attributes, so divide it with 10
		distanceScore = distanceScore/10;
		
		return distanceScore;
	}
	
	
	private double computeAgePrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		/*// 0 means "not similar", 1 means "similar"
		int similarityScore = 0;
		
		if(subjectAgent.isAgePrefDontMind()) {
			similarityScore=1;
			return similarityScore;
		}
		List<Range> ageRanges = subjectAgent.getAgePrefList();
		for (Range range : ageRanges) {
			if(objectAgent.getAge()>=range.getLowerBound() && objectAgent.getAge()<=range.getHigherBound()) {
				similarityScore=1;
				return similarityScore;
			}
		}
		
		
		return similarityScore;*/
		
		double distanceScore = unmatchDistanceScore;
		
		if(subjectAgent.isAgePrefDontMind()) {
			distanceScore=matchDistanceScore;
			return distanceScore;
		}
		List<Range> ageRanges = subjectAgent.getAgePrefList();
		for (Range range : ageRanges) {
			if(objectAgent.getAge()>=range.getLowerBound() && objectAgent.getAge()<=range.getHigherBound()) {
				distanceScore=matchDistanceScore;
				return distanceScore;
			}
		}
		
		
		return distanceScore;
		
	}

	private double computeGenderPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		/*// 0 means "not similar", 1 means "similar"
		int similarityScore = 0;
		if(subjectAgent.isGenderPrefDontMind()) {
			similarityScore=1;
			return similarityScore;
		}
		
		if(subjectAgent.getGenderPref().equals(objectAgent.getGender())) {
			similarityScore=1;
			return similarityScore;
		}
		
		return similarityScore;
		*/
		
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isGenderPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		if(subjectAgent.getGenderPref().equals(objectAgent.getGender())) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		return similarityScore;
		
		
	}
	
	private double computeFamilyPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isFamilyPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		List<String> familyPrefs = subjectAgent.getFamilyPrefList();
		for (String familyPref : familyPrefs) {
			//System.out.println("subFamPr:"+familyPref + " objFam:"+objectAgent.getFamily());
			//System.out.println(familyPref.equals(objectAgent.getFamily()));
			if(objectAgent.getFamily().equals(familyPref)) {
			//	System.out.println("girdi");
				similarityScore=matchDistanceScore;
				return similarityScore;
			}
		}
		
		return similarityScore;
		
	}
	
	private double computeNationPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isNationPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		if(subjectAgent.getNationPref().equals(CHCInput.same) && 
				subjectAgent.getNationality().equals(objectAgent.getNationality())) {
			similarityScore=matchDistanceScore;
			return similarityScore;			
		}
		if(subjectAgent.getNationPref().equals(CHCInput.mixed) && 
				!subjectAgent.getNationality().equals(objectAgent.getNationality())) {
			similarityScore=matchDistanceScore;
			return similarityScore;	
		}
		
		
		return similarityScore;
		
	}
	
	private double computeReligionPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isReligionPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		if(subjectAgent.getReligionPref().equals(CHCInput.same) && 
				subjectAgent.getReligion().equals(objectAgent.getReligion())) {
			similarityScore=matchDistanceScore;
			return similarityScore;			
		}
		if(subjectAgent.getReligionPref().equals(CHCInput.mixed) && 
				!subjectAgent.getReligion().equals(objectAgent.getReligion())) {
			similarityScore=matchDistanceScore;
			return similarityScore;	
		}
		
		return similarityScore;
		
	}
	
	private double computeEthnicPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isEthnicPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		if(subjectAgent.getEthnicPref().equals(CHCInput.same) && 
				subjectAgent.getEthnicity().equals(objectAgent.getEthnicity())) {
			similarityScore=matchDistanceScore;
			return similarityScore;			
		}
		if(subjectAgent.getEthnicPref().equals(CHCInput.mixed) && 
				!subjectAgent.getEthnicity().equals(objectAgent.getEthnicity())) {
			similarityScore=matchDistanceScore;
			return similarityScore;	
		}
		
		return similarityScore;
		
	}
	
	private double computeLocationPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isLocationPrefDontMind() || objectAgent.isLocationPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		

		List<String> subjectLocPrefs = subjectAgent.getLocationPrefList();
		List<String> objectLocPrefs = objectAgent.getLocationPrefList();
		
		for (String subjectLocPref : subjectLocPrefs) {
			for (String objectLocPref : objectLocPrefs) {
				if(subjectLocPref.equals(objectLocPref)) {
					similarityScore=matchDistanceScore;
					return similarityScore;
				}
			}
		}
		
		return similarityScore;
		
	}
	
	private double computeAccessPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double similarityScore = unmatchDistanceScore;
		if(subjectAgent.isAccessibilityPrefDontMind() || objectAgent.isAccessibilityPrefDontMind()) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}
		
		if(subjectAgent.getAccessibilityPref().equals(objectAgent.getAccessibilityPref())) {
			similarityScore=matchDistanceScore;
			return similarityScore;
		}

		return similarityScore;
		
	}
	
	private double computeRentPeriodPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double distanceScore = unmatchDistanceScore;
		if(subjectAgent.isRentalPeriodPrefDontMind() || objectAgent.isRentalPeriodPrefDontMind()) {
			distanceScore=matchDistanceScore;
			return distanceScore;
		}
		
		Range subjectRentPeriodRange = subjectAgent.getRentalPeriodPref();
		Range objectRentPeriodRange = objectAgent.getRentalPeriodPref();
		
		//System.out.println("**range**");
		//System.out.println("sbjLower:"+subjectRentPeriodRange.getLowerBound() + 
		//		" sbjHgh:"+subjectRentPeriodRange.getHigherBound() + " objLwr:"+objectRentPeriodRange.getLowerBound() + 
		//		" objHgr:" + objectRentPeriodRange.getHigherBound());
		if(subjectRentPeriodRange.getLowerBound()>objectRentPeriodRange.getHigherBound() ||
				objectRentPeriodRange.getLowerBound() > subjectRentPeriodRange.getHigherBound()) {
			
			// they dont overlap, so return 0
			return distanceScore;
		}
		
		// find the amount of overlap and total period
		long overlap = Math.min(subjectRentPeriodRange.getHigherBound(), objectRentPeriodRange.getHigherBound()) -
				Math.max(subjectRentPeriodRange.getLowerBound(), objectRentPeriodRange.getLowerBound()) + 1;
		long total = Math.max(subjectRentPeriodRange.getHigherBound(), objectRentPeriodRange.getHigherBound()) - 
				Math.min(subjectRentPeriodRange.getLowerBound(), objectRentPeriodRange.getLowerBound()) + 1;
		//System.out.println("over:"+overlap);
		//System.out.println("total:"+total);
//		similarityScore=overlap/total;
		
		distanceScore = (float) (total-overlap)/total;
		
		
		return distanceScore;
		
	}
	
	private double computeShareWithPrefSimilarity(Agent subjectAgent, Agent objectAgent) {
		// 0 means "not similar", 1 means "similar"
		double  distanceScore = unmatchDistanceScore;
		if(subjectAgent.isShareWithPrefDontMind() || objectAgent.isShareWithPrefDontMind()) {
			distanceScore = matchDistanceScore;
			return distanceScore ;
		}
		
		
		Range subjectShareWithRange = subjectAgent.getShareWithPref();
		Range objectShareWithRange = objectAgent.getShareWithPref();
		
		
		if(subjectShareWithRange.getLowerBound()>objectShareWithRange.getHigherBound() ||
				objectShareWithRange.getLowerBound() > subjectShareWithRange.getHigherBound()) {
			
			// they dont overlap, so return 0
			return distanceScore ;
		}
		
		// find the amount of overlap and total period
		long overlap = Math.min(subjectShareWithRange.getHigherBound(), objectShareWithRange.getHigherBound()) -
				Math.max(subjectShareWithRange.getLowerBound(), objectShareWithRange.getLowerBound()) + 1;
		
		long total = Math.max(subjectShareWithRange.getHigherBound(), objectShareWithRange.getHigherBound()) - 
				Math.min(subjectShareWithRange.getLowerBound(), objectShareWithRange.getLowerBound()) + 1;

		distanceScore = (float) (total-overlap)/total;
		
		return distanceScore ;
	}
}
