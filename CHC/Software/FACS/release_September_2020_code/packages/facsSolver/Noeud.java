package facsSolver;


public class Noeud {
	int nbAgents;
	int level;
	int nbCoalitions;
	Coalition[] coalitions;
	int nbMax;
	Filee file;
	Filee queue;
	public static String[] listeCoalitions=new String[20000];
	public static int[][] listeCoalitionsInt=new int[20000][];
	public static int[] listeNbCoalitions= new int[20000];
	public static int nbCoalitionsDiff=0;
	
	public Noeud(int level, int nbAgents){
		this.nbAgents=nbAgents;
		this.level = level;
		this.nbCoalitions=level+1;
		this.coalitions=new Coalition[nbCoalitions];
		level=0;
		if((nbAgents-level)%2==0){
			this.nbMax=(nbAgents-level)/2;
		}
		else{
			this.nbMax=((nbAgents-level)/2);
		}
	}
	public Filee getFilsRacine(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
					int moitie = coalitions[i].nbElts/2;
					for(int j=1; j<=moitie; j++){
						//System.out.println("113");
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						//pour trier : mais pour rien par la suite
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<nbCoalitionsDiff && trouve){
							if(str.equals(listeCoalitions[s])){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							Noeud.listeCoalitionsInt[nbCoalitionsDiff]= new int[nouv.nbCoalitions];
							
							for(int l=0; l<nouv.nbCoalitions; l++){
								Noeud.listeCoalitionsInt[nbCoalitionsDiff][l]=nouv.coalitions[l].nbElts;
							}
							Noeud.listeNbCoalitions[nbCoalitionsDiff]=nouv.nbCoalitions;
							nbCoalitionsDiff++;
							//System.out.println(str);
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
						
					}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFils(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							//System.out.println(str);
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else {
					int moitie = coalitions[i].nbElts/2;
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(moitie);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-moitie);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					
				}
			}
		}
		
		
		return file.suivant;
		
	}
	
	public Filee getFils2(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else {
					int moitie = coalitions[i].nbElts/2;
					for(int j=moitie/2+1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFils6(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							Noeud.listeCoalitionsInt[nbCoalitionsDiff]= new int[nouv.nbCoalitions];
							for(int l=0; l<nouv.nbCoalitions; l++){
								Noeud.listeCoalitionsInt[nbCoalitionsDiff][l]=nouv.coalitions[l].nbElts;
							}
							Noeud.listeNbCoalitions[nbCoalitionsDiff]=nouv.nbCoalitions;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else {
					int moitie = coalitions[i].nbElts/2;
					
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(moitie);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-moitie);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							Noeud.listeCoalitionsInt[nbCoalitionsDiff]= new int[nouv.nbCoalitions];
							for(int l=0; l<nouv.nbCoalitions; l++){
								Noeud.listeCoalitionsInt[nbCoalitionsDiff][l]=nouv.coalitions[l].nbElts;
							}
							Noeud.listeNbCoalitions[nbCoalitionsDiff]=nouv.nbCoalitions;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFils3(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					int quart = moitie/2+1;
					int limite = coalitions[i].nbElts*2/3;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else if (coalitions[i].nbElts%2==0) {
					int moitie = coalitions[i].nbElts/2;
					int sixieme = coalitions[i].nbElts/6;
					if(coalitions[i].nbElts%6!=0){
						sixieme+=1;
					}
					int limite = coalitions[i].nbElts*2/3;
					for(int j=sixieme; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFils5(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					int quart = moitie/2+1;
					int limite = coalitions[i].nbElts*2/3;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							Noeud.listeCoalitionsInt[nbCoalitionsDiff]= new int[nouv.nbCoalitions];
							
							for(int l=0; l<nouv.nbCoalitions; l++){
								Noeud.listeCoalitionsInt[nbCoalitionsDiff][l]=nouv.coalitions[l].nbElts;
							}
							Noeud.listeNbCoalitions[nbCoalitionsDiff]=nouv.nbCoalitions;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else if (coalitions[i].nbElts%2==0) {
					int moitie = coalitions[i].nbElts/2;
					int sixieme = coalitions[i].nbElts/6;
					int limite = coalitions[i].nbElts*2/3;
					for(int j=sixieme; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							Noeud.listeCoalitionsInt[nbCoalitionsDiff]= new int[nouv.nbCoalitions];
							for(int l=0; l<nouv.nbCoalitions; l++){
								Noeud.listeCoalitionsInt[nbCoalitionsDiff][l]=nouv.coalitions[l].nbElts;
							}
							Noeud.listeNbCoalitions[nbCoalitionsDiff]=nouv.nbCoalitions;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFils4(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					int quart = moitie/2+1;
					int limite = coalitions[i].nbElts*2/3;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
				else {
					int moitie = coalitions[i].nbElts/2;
					int sixieme = coalitions[i].nbElts/6;
					int quart = coalitions[i].nbElts/4+1;
					int limite = coalitions[i].nbElts*2/3;
					for(int j=1; j<=quart; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFilsHDP(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					int moitie = coalitions[i].nbElts/2;
					for(int j=1; j<=moitie; j++){
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(j);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					}
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getFilsEDPmoitie(){
		file=new Filee(new Noeud(0,10));
		queue=file;
		for(int i=0;i<nbCoalitions; i++){
			if(coalitions[i].nbElts>1){
				if(coalitions[i].nbElts<=nbMax){
					if(coalitions[i].nbElts%2==0){
						int moitie = coalitions[i].nbElts/2;
						for(int j=1; j<=moitie; j++){
							Noeud nouv = new Noeud(this.level+1, this.nbAgents);
							nouv.coalitions[0]=new Coalition(j);
							nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-j);
							int ind=2;
							for(int k=0; k<i; k++){
								nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
								ind++;
							}
							for(int k=i+1; k<nbCoalitions; k++){
								nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
								ind++;
							}
							for(int k=0; k<nouv.nbCoalitions;k++){
								for(int l=k+1; l< nouv.nbCoalitions;l++){
									if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
										Coalition inter=nouv.coalitions[k];
										nouv.coalitions[k]=nouv.coalitions[l];
										nouv.coalitions[l]=inter;
									}
								}
							}
							String str="";
							for(int k=0; k<nouv.nbCoalitions;k++){
								str=str+nouv.coalitions[k].nbElts+", ";
							}
							int s=0;
							boolean trouve=true;
							while(s<(nbCoalitionsDiff) && trouve){
								if(listeCoalitions[s].equals(str)){
									trouve=false;
								}
								s++;
							}
							if(trouve){
								Noeud.listeCoalitions[nbCoalitionsDiff]=str;
								nbCoalitionsDiff++;
								queue.suivant=new Filee(nouv);
								queue=queue.suivant;
							}
						}
					}
					
				}
				else {
					int moitie = coalitions[i].nbElts/2;
						Noeud nouv = new Noeud(this.level+1, this.nbAgents);
						nouv.coalitions[0]=new Coalition(moitie);
						nouv.coalitions[1]=new Coalition(coalitions[i].nbElts-moitie);
						int ind=2;
						for(int k=0; k<i; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=i+1; k<nbCoalitions; k++){
							nouv.coalitions[ind]=new Coalition(coalitions[k].nbElts);
							ind++;
						}
						for(int k=0; k<nouv.nbCoalitions;k++){
							for(int l=k+1; l< nouv.nbCoalitions;l++){
								if(nouv.coalitions[k].nbElts>nouv.coalitions[l].nbElts){
									Coalition inter=nouv.coalitions[k];
									nouv.coalitions[k]=nouv.coalitions[l];
									nouv.coalitions[l]=inter;
								}
							}
						}
						String str="";
						for(int k=0; k<nouv.nbCoalitions;k++){
							str=str+nouv.coalitions[k].nbElts+", ";
						}
						int s=0;
						boolean trouve=true;
						while(s<(nbCoalitionsDiff) && trouve){
							if(listeCoalitions[s].equals(str)){
								trouve=false;
							}
							s++;
						}
						if(trouve){
							Noeud.listeCoalitions[nbCoalitionsDiff]=str;
							nbCoalitionsDiff++;
							queue.suivant=new Filee(nouv);
							queue=queue.suivant;
						}
					
				}
			}
		}
		
		
		return file.suivant;
		
	}
	public Filee getQueue(){
		if(file==null){
			return null;
		}
		else{
			return queue;
		}
	}
}
