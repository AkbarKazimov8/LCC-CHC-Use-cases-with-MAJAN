package chc.problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

	public static final String dateFormat1 = "d/M/yyyy";
	public static final String dateFormat2 = "MMMM d, yyyy";
	
	public static  final List<String> genderList = new ArrayList<String>(Arrays.asList("female", "male", "other"));
	public static  final List<String> familyList = new ArrayList<String>(Arrays.asList("single_man", "single_woman",
			"nuclear", "single_parent_mother", "single_parent_father", "extended"));
	public static  final List<String> nationalityList = new ArrayList<String>(Arrays.asList("nation1", "nation2",
			"nation3", "nation4", "nation5", "nation6", "nation7"));
	public static  final List<String> religionList = new ArrayList<String>(Arrays.asList("relig1", "relig2",
			"relig3", "relig4", "relig5", "relig6", "relig7"));
	public static  final List<String> ethnicList = new ArrayList<String>(Arrays.asList("ethnic1", "ethnic2",
			"ethnic3", "ethnic4", "ethnic5", "ethnic6", "ethnic7"));
	public static  final List<String> agePrefList = new ArrayList<String>(Arrays.asList("dont mind", "18-25", "26-33",
			"34-43", "44-50", "51-65", "65-120", "44-50, 51-65", "51-65, 65-120", "18-25, 26-33,34-43",
			"18-25, 26-33"));
	public static  final List<String> genderPrefList = new ArrayList<String>(Arrays.asList("dont mind", "female", "male", "other"));
	public static  final List<String> familyPrefList = new ArrayList<String>(Arrays.asList("dont mind", "single_man", "single_woman",
			"nuclear", "single_parent_mother", "single_parent_father", "extended"));
	public static  final List<String> nationPrefList = new ArrayList<String>(Arrays.asList("dont mind", "nation1", "nation2",
			"nation3", "nation4", "nation5", "nation6", "nation7"));
	public static  final List<String> religionPrefList = new ArrayList<String>(Arrays.asList("dont mind", "relig1", "relig2",
			"relig3", "relig4", "relig5", "relig6", "relig7"));
	public static  final List<String> ethnicPrefList = new ArrayList<String>(Arrays.asList("dont mind", "ethnic1", "ethnic2",
			"ethnic3", "ethnic4", "ethnic5", "ethnic6", "ethnic7"));
	public static  final List<String> locationPrefList = new ArrayList<String>(Arrays.asList("dont mind", "L1", "L2",
			"L3", "L4", "L5", "L6", "L7", "L1, L2, L3", "L2, L3", "L3, L5", "L4, L2, L7", "L6, L2", "L7, L6, L5"));
	public static  final List<String> accessPrefList = new ArrayList<String>(Arrays.asList("dont mind", "yes", "no"));
	public static  final List<String> rentalPrefList = new ArrayList<String>(Arrays.asList("dont mind", "1/1/2021, 1/7/2021", 
			"1/5/2021, 1/5/2022", "1/1/2021, 1/7/2022", "1/3/2021, 1/7/2022", " 1/1/2021, 1/7/2022", "1/1/2021, 1/4/2021",
			"15/3/2021, 1/4/2022", "1/9/2021, 1/12/2021", "3/7/2021, 12/2/2022", "23/4/2020, 17/5/2021"));
	public static  final List<String> shareWithPrefList = new ArrayList<String>(Arrays.asList("dont mind", "1", "2",
			"3", "4", "5", "1,3", "2,4", "1,5", "2,7","3,4", "1,4", "1,2", "2,3","3,5"));
	
	
	
	public static void writeRandomCHCInputToFile(int agentNumber) throws IOException {
		String path = "C:\\Users\\akka02\\Desktop\\Master-Thesis\\Software\\EclipseWorkspace\\HDBSCANStar_FRAMEWORK_JAVA_Code_with_Visualization\\HDBSCAN_Star\\Input-Output\\input2.txt";
		
		FileWriter fileWriter=new FileWriter(new File(path));
		PrintWriter printWriter=new PrintWriter(fileWriter);
		
		ThreadLocalRandom rand= ThreadLocalRandom.current();
		for (int i = 0; i < agentNumber; i++) {
			printWriter.append((i+1)+" |");
			printWriter.append(rand.nextInt(18, 75) + "|");
			printWriter.append(genderList.get(rand.nextInt(0, genderList.size()))+ " |");
			printWriter.append(familyList.get(rand.nextInt(0, familyList.size()))+ " |");
			printWriter.append(nationalityList.get(rand.nextInt(0, nationalityList.size()))+ " |");
			printWriter.append(religionList.get(rand.nextInt(0, religionList.size()))+ " |");
			printWriter.append(ethnicList.get(rand.nextInt(0, ethnicList.size()))+ " |");
			printWriter.append(agePrefList.get(rand.nextInt(0, agePrefList.size()))+ " |");
			printWriter.append(genderPrefList.get(rand.nextInt(0, genderPrefList.size()))+ " |");
			printWriter.append(familyPrefList.get(rand.nextInt(0, familyPrefList.size()))+ " |");
			printWriter.append(nationPrefList.get(rand.nextInt(0, nationPrefList.size()))+ " |");
			printWriter.append(religionPrefList.get(rand.nextInt(0, religionPrefList.size()))+ " |");
			printWriter.append(ethnicPrefList.get(rand.nextInt(0, ethnicPrefList.size()))+ " |");
			printWriter.append(locationPrefList.get(rand.nextInt(0, locationPrefList.size()))+ " |");
			printWriter.append(accessPrefList.get(rand.nextInt(0, accessPrefList.size()))+ " |");
			printWriter.append(rentalPrefList.get(rand.nextInt(0, rentalPrefList.size()))+ " |");
			printWriter.append(shareWithPrefList.get(rand.nextInt(0, shareWithPrefList.size()))+ " |");
			printWriter.println();
		}
		
		printWriter.close();
		
	}
	
	public static int getRandomInt(int min, int max) {
		return 0;
	}
	
	public static LocalDate convertStringToDate(String input, String dateFormatPattern) {
		DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern(dateFormatPattern, Locale.ENGLISH);
		return LocalDate.parse(input,dateTimeFormatter);
	}
	
	public static long convertDateToMillis(LocalDate date) {
		Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
		long timeInMls = instant.toEpochMilli();
	
		return timeInMls;
	}
	
}
