package chc.problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CHCInput {
	private final static String[] dontMinds = new String[] {"don't mind", "dont mind", "dontmind", "don'tmind"};
	
	public final static double 
	ageWeight = 1,
	genderWeight = 2, 
	familyWeight = 6, 
	nationWeight = 3,
	religionWeight = 5, 
	ethnicWeight = 4, 
	locationWeight = 9, 
	accessWeight = 8, 
	rentPeriodWeight = 10,
	shareWithWeight = 7;
	
	public static final String 
	same = "same",
	mixed = "mixed";
	
	
	public List<Agent> readInputFromFile(String inputPath) throws FileNotFoundException {
		List<Agent> agentList = new ArrayList();
		
		BufferedReader reader = new BufferedReader(new FileReader(inputPath));
		Iterator<String> linesIter = reader.lines().iterator();
		while(linesIter.hasNext()) {
			String line=linesIter.next();

			if(line==null || line.trim().equals("") || line.trim().isEmpty() || line.trim().startsWith("//")) {
				continue;
			}else if(Character.isDigit(line.trim().charAt(0))) {
				agentList.add(createAgent(line));
			}
		}
		return agentList;
	}
	
	private Agent createAgent(String tcnInfo) {
		Agent agent=new Agent();
		String[] values = tcnInfo.trim().split("\\|");
		
		//int id,gender,nationality,attendance,genderPref,nationPref;
		//double CPL,genderWeight,nationWeight;
		
		// + <agent_id:{int}> | 
		// + <age:{int}> | 
		// + <gender:{male, female, other}> | 
		// + <family:{single_man, single_woman, nuclear, single_parent_mother, single_parent_father, extended}> |
		// + <nationality: {String}> | 
		// + <religion: {String}> | 
		// + <ethinicity: {String}> |
		// + <age_pref:{don't mind, (1 or multiple of 18-25, 26-33, 34-43, 44-50, 51-65, 65-120)}> |
		// + <gender_pref:{don't mind, male, female, other}> |
		// + <family_pref:{don't mind, single_man, single_woman, nuclear, single_parent_mother, single_parent_father, extended}> |
		// + <nation_pref:{don't mind, mixed, same}> |
		// + <religion_pref:{don't mind, mixed, same}> |
		// + <ethnic_pref:{don't mind, mixed, same}> |
		// + <location_pref:{don't mind, (String)}> |
		// + <access_pref:{dont'mind, yes}> |
		// + <rental_pref:{dont'mind, (range from-to: dd/mm/yyyy, dd/mm/yyyy)} | 
		// + <shareWith_pref:{don't mind, (range from-to: int, int}>
		
		agent.setId(Integer.valueOf(values[0].trim()));
		agent.setAge(Integer.valueOf(values[1].trim()));
		agent.setGender(values[2].trim());
		agent.setFamily(values[3].trim());
		agent.setNationality(values[4].trim());
		agent.setReligion(values[5].trim());
		agent.setEthnicity(values[6].trim());
		agent = setAgePreference(agent ,values[7].trim());
		agent = setGenderPreference(agent, values[8].trim());
		agent = setFamilyPreference(agent, values[9].trim());
		agent = setNationPreference(agent, values[10].trim());
		agent = setReligionPreference(agent, values[11].trim());
		agent = setEthnicPreference(agent, values[12].trim());
		agent = setLocationPreference(agent, values[13].trim());
		agent = setAccessibilityPreference(agent, values[14].trim());
		agent = setRentalPeriodPreference(agent, values[15].trim());
		agent = setShareWithPreference(agent, values[16].trim());
		
		return agent;
	}
	
	private Agent setAgePreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setAgePrefDontMind(true);
				return agent;
			}
		}
		
		// If not don't mind, then read the age range(s) and set them as the age preference
		String[] ageRanges = preference.split(",");
		List<Range> ageRangeList = new ArrayList();
		
		for (String ageRange : ageRanges) {
			Range rangeIns=new Range();
			String[] bounds = ageRange.split("-");
			rangeIns.setLowerBound(Integer.valueOf(bounds[0].trim()));
			rangeIns.setHigherBound(Integer.valueOf(bounds[1].trim()));
			ageRangeList.add(rangeIns);
		}
		
		agent.setAgePrefList(ageRangeList);
		
		return agent;
	}
	
	private Agent setGenderPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setGenderPrefDontMind(true);
				return agent;
			}
		}
		
		agent.setGenderPref(preference);
		
		return agent;	
	}
	
	private Agent setFamilyPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setFamilyPrefDontMind(true);
				return agent;
			}
		}
		
		String[] familyPreferences = preference.split(",");
		List<String> familyPreferenceList = new ArrayList();
		
		for (String familyPreference : familyPreferences) {
			familyPreferenceList.add(familyPreference.trim());
		}
		agent.setFamilyPrefList(familyPreferenceList);
		

		return agent;	
	}
	
	private Agent setNationPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setNationPrefDontMind(true);
				return agent;
			}
		}
		agent.setNationPref(preference);
				
		return agent;	
	}
	
	private Agent setReligionPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setReligionPrefDontMind(true);
				return agent;
			}
		}
		agent.setReligionPref(preference);
				
		return agent;	
	}
	
	private Agent setEthnicPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setEthnicPrefDontMind(true);
				return agent;
			}
		}
		agent.setEthnicPref(preference);
				
		return agent;	
	}
	
	private Agent setLocationPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setLocationPrefDontMind(true);
				return agent;
			}
		}
		
		String[] locationPreferences = preference.split(",");
		List<String> locationPreferenceList = new ArrayList();
		
		for (String locationPreference : locationPreferences) {
			locationPreferenceList.add(locationPreference.trim());
		}
		agent.setLocationPrefList(locationPreferenceList);
		

		return agent;	
	}
	
	private Agent setAccessibilityPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setAccessibilityPrefDontMind(true);
				return agent;
			}
		}
		agent.setAccessibilityPref(preference);
				
		return agent;	
	}
	
	private Agent setRentalPeriodPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setRentalPeriodPrefDontMind(true);
				return agent;
			}
		}
		
		// If not "don't mind", then read the rental period range and set it as the preference
		String[] rentalPeriodBounds = preference.split(",");
		Range rentalPeriodRange = new Range();
		
		long lowerBoundInMillis = Utils.convertDateToMillis(Utils.convertStringToDate(rentalPeriodBounds[0].trim(), 
				Utils.dateFormat1));
		long higherBoundInMillis = Utils.convertDateToMillis(Utils.convertStringToDate(rentalPeriodBounds[1].trim(), 
				Utils.dateFormat1));

		rentalPeriodRange.setLowerBound(lowerBoundInMillis);
		rentalPeriodRange.setHigherBound(higherBoundInMillis);
		agent.setRentalPeriodPref(rentalPeriodRange);
		
		
		return agent;
	}

	private Agent setShareWithPreference(Agent agent, String preference) {
		preference=preference.trim();
		// Set true if it is don't mind
		for (String dontMind: dontMinds) {
			if(preference.equals(dontMind)) {
				agent.setShareWithPrefDontMind(true);
				return agent;
			}
		}
		
		// If not "don't mind", then read the "share with" range and set it as the preference
		String[] shareWithBounds = preference.split(",");
		Range shareWithRange = new Range();

		if (shareWithBounds.length==1) {
			shareWithRange.setLowerBound(Integer.valueOf(shareWithBounds[0].trim()));
			shareWithRange.setHigherBound(Integer.valueOf(shareWithBounds[0].trim()));			
		}else {
			shareWithRange.setLowerBound(Integer.valueOf(shareWithBounds[0].trim()));
			shareWithRange.setHigherBound(Integer.valueOf(shareWithBounds[1].trim()));
		}
		
		agent.setShareWithPref(shareWithRange);
		
		
		return agent;
	}

	public void printAgents(List<Agent> agents) {
		for (Agent agent : agents) {
			System.out.println("id:"+agent.getId());
			System.out.println("age:"+agent.getAge());
			System.out.println("gender:"+agent.getGender());
			System.out.println("family:"+agent.getFamily());
			
			System.out.println("nation:"+agent.getNationality());
			
			System.out.println("religion:"+agent.getReligion());
			
			System.out.println("ethnic:"+agent.getEthnicity());
			
			System.out.println("age_pref_dm:"+agent.isAgePrefDontMind());
			if(null!=agent.getAgePrefList()) {
				List<Range> ageRangePref= agent.getAgePrefList();
				for (Range ageRange : ageRangePref) {
					System.out.println("lower:"+ageRange.getLowerBound());
					System.out.println("higher:"+ageRange.getHigherBound());
				}					
			}
			
			System.out.println("gender_pref_dm:"+agent.isGenderPrefDontMind());
			if(null!=agent.getGenderPref())
			System.out.println("gender_pref:"+agent.getGenderPref());
			
			System.out.println("family_pref_dm:"+agent.isFamilyPrefDontMind());
			if(null!=agent.getFamilyPrefList()) {
				List<String> familyPrefs = agent.getFamilyPrefList();
				for (String familyPref : familyPrefs) {
					System.out.println("family_pref:"+familyPref);
				}				
			}
			
			System.out.println("nation_pref_dm:"+agent.isNationPrefDontMind());
			if(null!=agent.getNationPref())
			System.out.println("nation_pref:"+agent.getNationPref());
			
			System.out.println("religion_pref_dm:"+agent.isReligionPrefDontMind());
			if(null!=agent.getReligionPref())
			System.out.println("religion_pref:"+agent.getReligionPref());
			
			System.out.println("ethnic_pref_dm:"+agent.isEthnicPrefDontMind());
			if(null!=agent.getEthnicPref())
			System.out.println("ethnic_pref:"+agent.getEthnicPref());
			
			System.out.println("location_pref_dm:"+agent.isLocationPrefDontMind());
			if(null!=agent.getLocationPrefList()) {
				List<String> locationPrefs = agent.getLocationPrefList();
				for (String locPref : locationPrefs) {
					System.out.println("location_pref:"+locPref);
					}
			}
			System.out.println("access_pref_dm:"+agent.isAccessibilityPrefDontMind());
			if(null!=agent.getAccessibilityPref())
			System.out.println("access_pref:"+agent.getAccessibilityPref());

			System.out.println("rentPeriod_pref_dm:"+agent.isRentalPeriodPrefDontMind());
			if(null!=agent.getRentalPeriodPref()) {
				Range rentPeriod = agent.getRentalPeriodPref();
				System.out.println("lower:"+rentPeriod.getLowerBound());
				System.out.println("higher:"+rentPeriod.getHigherBound());
			}
			System.out.println("shareWith_pref_dm:"+agent.isShareWithPrefDontMind());
			if(null!=agent.getShareWithPref()) {
				Range shareWithPeriod = agent.getShareWithPref();
				System.out.println("lower:"+shareWithPeriod.getLowerBound());
				System.out.println("higher:"+shareWithPeriod.getHigherBound());
			}
		}
	}
}
