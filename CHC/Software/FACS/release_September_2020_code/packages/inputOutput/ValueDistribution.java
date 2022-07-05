package inputOutput;

public enum ValueDistribution {

	UNIFORM,
	
	NORMAL,
	
	EXPONENTIAL,
	
	BETA,
	
	GAMMA,
	
	NDCS,
	
	AGENTBASEDUNIFORM,
	
	AGENTBASEDNORMAL,
	
	MODIFIEDUNIFORM,
	
	MODIFIEDNORMAL,
	
	UNKNOWN,
	
	CHISQUARE,
	
	WEIBULL,
	
	F,
	
	CAUCHY,
	
	T,
	
	Zipf,
	
	Pascal;
	
	public static String toString( ValueDistribution valueDistribution ){
		if( valueDistribution == UNIFORM ) return "Uniform";
		if( valueDistribution == NORMAL ) return "Normal";
		if( valueDistribution == EXPONENTIAL ) return "Exponential";
		if( valueDistribution == BETA ) return "Beta";
		if( valueDistribution == GAMMA ) return "Gamma";
		if( valueDistribution == NDCS ) return "NDCS";
		if( valueDistribution == AGENTBASEDUNIFORM ) return "Agent-based Uniform";
		if( valueDistribution == AGENTBASEDNORMAL ) return "Agent-based Normal";
		if( valueDistribution == MODIFIEDUNIFORM ) return "Modified Unifrom";
		if( valueDistribution == MODIFIEDNORMAL ) return "Modified Normal";
		if( valueDistribution == CHISQUARE ) return "Chi-square";
		if( valueDistribution == WEIBULL ) return "Weibull";
		if( valueDistribution == F ) return "F";
		if( valueDistribution == CAUCHY ) return "Cauchy";
		if( valueDistribution == UNKNOWN ) return "Unknown";
		return "";
	}
}
