package solveRandomProblems;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import inputOutput.Input;
import inputOutput.SolverNames;
import inputOutput.ValueDistribution;
import mainSolver.MainSolver;
import mainSolver.Result;

import java.awt.Dimension;

public class SolverRandomProblems
{
    boolean packFrame = false;

    /**
     * Construct and run the application.
     */
    public SolverRandomProblems(int numberOfAgents, int distribution)
    {       
        Input input = new Input();
        input.feasibleCoalitions = null;
        input.numOfAgents = numberOfAgents;
        input.folderInWhichCoalitionValuesAreStored = "../../CSGtemp";
        input.numOfRunningTimes = 1;
        input.orderIntegerPartitionsAscendingly = false;
        input.acceptableRatio = 100;
        
        switch (distribution) {
        case 1: input.valueDistribution = ValueDistribution.EXPONENTIAL;
        break;
        case 2: input.valueDistribution = ValueDistribution.AGENTBASEDNORMAL;
        break;
        case 3: input.valueDistribution = ValueDistribution.UNIFORM;
        break;
        case 4: input.valueDistribution = ValueDistribution.BETA;
        break;
        case 5: input.valueDistribution = ValueDistribution.AGENTBASEDUNIFORM;
        break;
        case 6: input.valueDistribution = ValueDistribution.NORMAL;
        break;
        case 7: input.valueDistribution = ValueDistribution.NDCS;
        break;
        case 8: input.valueDistribution = ValueDistribution.MODIFIEDNORMAL;
        break;
        case 9: input.valueDistribution = ValueDistribution.MODIFIEDUNIFORM;
        break;
        case 10: input.valueDistribution = ValueDistribution.F;
        break;
        case 11: input.valueDistribution = ValueDistribution.WEIBULL;
        break;
        case 12: input.valueDistribution = ValueDistribution.CHISQUARE;
        break;
        case 13: input.valueDistribution = ValueDistribution.GAMMA;
        break;
        case 14: input.valueDistribution = ValueDistribution.CAUCHY;
        break;
        case 15: input.valueDistribution = ValueDistribution.T;
        break;
        case 16: input.valueDistribution = ValueDistribution.Zipf;
        break;
        case 17: input.valueDistribution = ValueDistribution.Pascal;
        break;
        }
        
        
        
        input.solverName = SolverNames.FACS;
        
        input.printInterimResultsOfIPToFiles = false;
        input.storeCoalitionValuesInFile = true;
        input.readCoalitionValuesFromFile = false;
        input.printDetailsOfSubspaces = false;
        
        Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd (HH-mm-ss)" );
		input.outputFolder = "../../CSGtemp"+"/"+"Agents";
		//Generate new values
		input.generateCoalitionValues();					
		//Run the CSG algorithm(s)
		Result result = (new MainSolver()).solve( input );

		
        
    }


    //*****************************************************************************************************
    
    /**
     * Application entry point.
     */
    public static void main(String[] args) {
    	
    	int numberOfAgents = 20;
    	int distribution = 1;
    	if(args.length>0){
    		numberOfAgents = Integer.valueOf(args[0]);
    	}
    	if(args.length>1){
    		distribution = Integer.valueOf(args[1]);
    	}
    	new SolverRandomProblems(numberOfAgents, distribution);
    	
    }
}
