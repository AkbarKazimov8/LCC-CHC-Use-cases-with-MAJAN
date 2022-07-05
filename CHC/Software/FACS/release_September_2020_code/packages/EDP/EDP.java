package EDP;


public class EDP {
	public static void main(String[] args){
		int nbAgents=21;
		int iteration=1;
		System.out.println("aa");
		Noeud tete = new Noeud(0,nbAgents);
		tete.coalitions[0]=new Coalition(21);
		System.out.println("bb");
		Filee file = new Filee(tete);
		Filee queue = file;
		
		Filee fils = null;
		fils = file.tete.getFilsRacine();
		if(fils!=null){
			queue.suivant=fils;
			queue=file.tete.getQueue();
		}
		file=file.suivant;
		System.out.println(iteration);
		iteration++;
		
		while(file!=null){
			fils = file.tete.getFils();
			if(fils!=null){
				queue.suivant=fils;
				queue=file.tete.getQueue();
			}
			file=file.suivant;
			System.out.println(iteration);
			iteration++;
		}
		System.out.println("nb!!! "+(Noeud.nbCoalitionsDiff+1));
		for(int i=0; i<Noeud.nbCoalitionsDiff;i++){
			System.out.println(Noeud.listeCoalitions[i]);
		}
	}
}
