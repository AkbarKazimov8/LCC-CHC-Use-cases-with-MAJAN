/**
 * The FACS solver.
 */
package facsSolver;

import mainSolver.Result;
import inputOutput.*;

import java.util.Arrays;

import general.Combinations;
import general.General;
import general.SubsetEnumerator;

public class FACS {
	private double[] initialCoalitionValues;//FACS needs to work on the initial values of the coalitions, not the updated ones
	
    private double[] f;
    
    private Input input;
    
    private Result result;
    
    int nbCS = 0;
    
    public FACS( Input input, Result result ){
    	this.input = input;
    	this.result = result;
    }
    
    //*********************************************************************************************************
    
    public void set_f( int index, double value ){
    	if( input.solverName == SolverNames.ODPIP )
    		result.idpSolver_whenRunning_ODPIP.updateValueOfBestPartitionFound( index, value);
    	else    		
    		f[ index ] = value;
    }
    public double get_f( int index ){
    	if( input.solverName == SolverNames.ODPIP )
    		return result.idpSolver_whenRunning_ODPIP.getValueOfBestPartitionFound( index );
    	else
    		return f[ index ];
    }
    
    //*********************************************************************************************************
    
	
	
	
    //*********************************************************************************************************

	/**
     * Run FACS.
     */
	int nbPermutations;
    public void runFACS()
    {
		f = new double[ input.coalitionValues.length ];
		for(int i=0; i<f.length; i++){
			set_f( i, input.coalitionValues[i] );
		}	
    	initialCoalitionValues = f;
    	int numOfAgents = input.numOfAgents;
    	set_f(0,0); //just in case, set the value of the empty set to 0

    	//initialization
    	long[] requiredTimeForEachSize = new long[ numOfAgents+1 ];
    	requiredTimeForEachSize[1] = 0;
    	long[] startTimeForEachSize = new long[ numOfAgents+1 ];
    	int grandCoalition = (1 << numOfAgents) - 1;
    	int numOfCoalitions = 1 << numOfAgents;
    	int bestHalfOfGrandCoalition=-1;
        long startTime = System.currentTimeMillis();
        result.set_dpMaxSizeThatWasComputedSoFar(1);

        int numOfAgents2 = numOfAgents/2 /*+ numOfAgents%2*/;
        
        result.dpTimeForEachSize = requiredTimeForEachSize;
        
        double bestValue = -1;
		int[] bestCoalitionStructure = new int[1];
		bestCoalitionStructure[0] = (int) (Math.pow(2, numOfAgents)-1);
        if(true){
                		
    		int nbAgents2=numOfAgents;
    		int iteration2=1;
    		//System.out.println("aa");
    		EDP.Noeud tete2 = new EDP.Noeud(0,nbAgents2);
    		tete2.coalitions[0]=new EDP.Coalition(nbAgents2);
    		//System.out.println("bb");
    		EDP.Filee file2 = new EDP.Filee(tete2);
    		EDP.Filee queue2 = file2;
    		
    		EDP.Filee fils2 = null;
    		fils2 = file2.tete.getFilsRacine();
    		if(fils2!=null){
    			queue2.suivant=fils2;
    			queue2=file2.tete.getQueue();
    		}
    		file2=file2.suivant;
    		
    		iteration2++;
    		
    		while(file2!=null){
    			fils2 = file2.tete.getFils();
    			if(fils2!=null){
    				queue2.suivant=fils2;
    				queue2=file2.tete.getQueue();
    			}
    			file2=file2.suivant;
    			iteration2++;
    		}
    		
    		int listeNoeudsOublies[][] = EDP.Noeud.listeCoalitionsInt;
    		int nbNoeudsOublies = EDP.Noeud.nbCoalitionsDiff;
    		
    		startTime = System.currentTimeMillis();
    		
    		int niveau=2, iParcours=0;
    		int nbAgentsMoitie = nbAgents2;
    		bestValue = -1;
    		bestCoalitionStructure = new int[1];
    		bestCoalitionStructure[0] = (int) (Math.pow(2, numOfAgents)-1);
    		
    		while(niveau < nbAgentsMoitie && iParcours< nbNoeudsOublies){
    			if(niveau<6) {
        			if(listeNoeudsOublies[iParcours].length==niveau){
        				
        				int[] tabPermute = new int[niveau];
        				for(int i=0; i<niveau; i++){
        					tabPermute[i]=i;
        				}
        				int fact = 1;
        			    for (int i = 2; i <= niveau; i++) {
        			        fact = fact * i;
        			    }
        				int nLigne=0;
        				int allPossibilities[][] = new int[fact][niveau];
        				int inter;
        				FileePermute tetePermute=new FileePermute(new int[3],4);//il sera enlvé de toutes facons
        				FileePermute queuePermute=tetePermute;
        				for(int i=0; i< tabPermute.length; i++){
        					int[] tab2=new int[tabPermute.length];
        					for(int j=0; j<tabPermute.length; j++){
        						tab2[j]=tabPermute[j];
        					}
        					inter= tab2[0];
        					tab2[0]=tab2[i];
        					tab2[i]=inter;
        					queuePermute.suivant=new FileePermute(tab2, 1);
        					queuePermute=queuePermute.suivant;
        				}
        				tetePermute=tetePermute.suivant;
        				int nbComb=0;
        				while(tetePermute!=null){
        					FileePermute tete2Permute=tetePermute.suivant;
        					if(tetePermute.ind==tabPermute.length-1){
        						allPossibilities[nLigne]=tetePermute.tete;
        						nLigne++;
        						nbComb++;
        					}
        					else{
        						for(int i=tetePermute.ind; i< tabPermute.length; i++){
        							int[] tab2=new int[tabPermute.length];
        							for(int j=0; j<tabPermute.length; j++){
        								tab2[j]=tetePermute.tete[j];
        							}
        							inter= tab2[tetePermute.ind];
        							tab2[tetePermute.ind]=tab2[i];
        							tab2[i]=inter;
        							FileePermute newTetePermute = new FileePermute(tab2, tetePermute.ind+1);
        							newTetePermute.suivant=tete2Permute;
        							tete2Permute=newTetePermute;
        							
        						}
        						
        					}
        					tetePermute=tete2Permute;
        				}
        				for(int i=iParcours; i< nbNoeudsOublies; i++){
        					if(listeNoeudsOublies[i].length==niveau){
        						iParcours++;
        						
        						int allPossibilitiesGroups[][] = new int[fact][nbAgents2];
        						
        						//Position the codes
        						for(int l=0; l<nLigne; l++){
        							int m=0;
        							for(int j=0; j<allPossibilities[l].length; j++){
        								for(int k=0; k<listeNoeudsOublies[i][allPossibilities[l][j]]; k++){
        									allPossibilitiesGroups[l][m]=allPossibilities[l][j];
        									m++;
        								}
        							}
        						}
        						//Start browsing
        						
        						for(int n=0; n<nLigne; n++){
        							for(int j=0; j<nbAgents2; j++){
        								for(int k=j+1; k<nbAgents2; k++){
        									int tab2[] = new int[nbAgents2];
        									for(int l=0; l<nbAgents2; l++) {
        										tab2[l] = allPossibilitiesGroups[n][l];
        									}
        									if(tab2[k]!=tab2[j]){
        										int interm = tab2[k];
        										tab2[k] = tab2[j];
        										tab2[j] = interm;
        										int adresse[]= new int[niveau];
        										
        										for(int l=0; l<tab2.length; l++){
        											adresse[tab2[l]]+= 1 << l;
        										}
        										double curValue = -1;
        										for(int l=0; l<adresse.length; l++){
        											curValue+= initialCoalitionValues[adresse[l]];
        										}
        										nbCS++;
        										if(bestValue < curValue){
        											bestValue = curValue;
        											bestCoalitionStructure = new int[adresse.length];
        											for(int l=0; l<adresse.length; l++){
        												bestCoalitionStructure[l] = adresse[l];
        											}
        										}
        									}
        									
        								}
        							}
        							
        						}
        					}
        					else{
        						break;
        					}
        				}
        			}
        			niveau++;
        			
        		
    			}
    			else {

        			if(listeNoeudsOublies[iParcours].length==niveau){
        				int[] tabPermute = new int[niveau];
        				
        				for(int i=0; i<niveau; i++){
        					tabPermute[i]=i;
        				}
        				int nLigne=2;
        				int allPossibilities[][] = new int[2][niveau];
        				for(int i=0; i<niveau; i++) {
        					allPossibilities[0][i] = i;
        					allPossibilities[1][i] = niveau-1-i;
        				}
        				
        				for(int i=iParcours; i< nbNoeudsOublies; i++){
        					if(listeNoeudsOublies[i].length==niveau){
        						iParcours++;
        						
        						//int allPossibilitiesGroups[][] = new int[fact][nbAgents2];
        						int allPossibilitiesGroups[][] = new int[2][nbAgents2];
        						
        						//position the codes
        						for(int l=0; l<nLigne; l++){
        							int m=0;
        							for(int j=0; j<allPossibilities[l].length; j++){
        								for(int k=0; k<listeNoeudsOublies[i][allPossibilities[l][j]]; k++){
        									allPossibilitiesGroups[l][m]=allPossibilities[l][j];
        									m++;
        								}
        							}
        						}
        						//Start browsing
        						
        						for(int n=0; n<nLigne; n++){
        							for(int j=0; j<nbAgents2; j++){
        								for(int k=j+1; k<nbAgents2; k++){
        									int tab2[] = new int[nbAgents2];
        									for(int l=0; l<nbAgents2; l++) {
        										tab2[l] = allPossibilitiesGroups[n][l];
        									}
        									if(tab2[k]!=tab2[j]){
        										int interm = tab2[k];
        										tab2[k] = tab2[j];
        										tab2[j] = interm;
        										int adresse[]= new int[niveau];
        										
        										for(int l=0; l<tab2.length; l++){
        											adresse[tab2[l]]+= 1 << l;
        										}
        										double curValue = -1;
        										for(int l=0; l<adresse.length; l++){
        											curValue+= initialCoalitionValues[adresse[l]];
        										}
        										nbCS++;
        										if(bestValue < curValue){
        											bestValue = curValue;
        											bestCoalitionStructure = new int[adresse.length];
        											for(int l=0; l<adresse.length; l++){
        												bestCoalitionStructure[l] = adresse[l];
        											}
        										}
        									}
        									
        								}
        							}
        							
        						}
        					}
        					else{
        						break;
        					}
        				}
        			}
        			niveau++;
        			
        		
    			}
    		}
            
        }

        result.dpTime = System.currentTimeMillis() - startTime;
        //Set the final result
        System.out.println("best CS : "+Arrays.toString(bestCoalitionStructure));
        System.out.println("The total run time of "+"FACS"+" (in milliseconds): "+result.dpTime);
        System.out.println("The value of the coalition structure is: "+bestValue);
        
        General.createFolder( "../../CSGtemp" );
        String filePathAndName = "../../CSGtemp";
		filePathAndName += "/"+"results_"+numOfAgents+".txt";
		StringBuffer tempStringBuffer = new StringBuffer();
		General.printToFile( filePathAndName, "-"+input.valueDistribution+bestValue+"_"+result.dpTime+";\n", false);
		tempStringBuffer.setLength(0);
    	result.updateDPSolution2( bestCoalitionStructure , bestValue );
        result.set_bestSolutionFoundByPart2();
        System.out.println("-------------------------------------------------------");
    }
    
    
    
    //*********************************************************************************************************
    
    
}