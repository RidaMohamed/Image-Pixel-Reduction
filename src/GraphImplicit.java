import java.util.ArrayList;

class GraphImplicit extends Graph{
    int N;
	int [][] table ;
	int w ;
	int h ;


	GraphImplicit(int N){
	this.N = N;
    }

	GraphImplicit(int[] [] interest, int w, int h){

		this.table = interest ;
		this.w = w ;
		this.h = h ;
	}

    public int vertices(){
	return N;
    }
    
    public Iterable<Edge> next(int v)
	 {
	 	//trouver la ligne et la colonne de sommet v
		 // trouver la ligne
		 int ligne =  i-1;
		 int col   =  v-(ligne*w)-1;

	 	//Ajouter les arreetes
	     ArrayList<Edge> edges = new ArrayList();
	     if (col == 0 ){
			 edges.add(new Edge(v,v + w,table[ligne][col]));
			 edges.add(new Edge(v,v + w + 1,table[ligne][col]));
		 }else if (col == w-1){
			 edges.add(new Edge(v,v + w,table[ligne][col]));
			 edges.add(new Edge(v,v + w - 1,table[ligne][col]));
		 }else {
			 edges.add(new Edge(v,v + w,table[ligne][col]));
			 edges.add(new Edge(v,v + w + 1,table[ligne][col]));
			 edges.add(new Edge(v,v + w - 1,table[ligne][col]));
		 }

	     return edges;
		      
	 }

   public Iterable<Edge> prev(int v)
	 {

		 //trouver la ligne et la colonne de sommet v
		 // trouver la ligne
		 int ligne =  i-1;
		 int col   =  v-(ligne*w)-1;

		 //Ajouter les arreetes
		 ArrayList<Edge> edges = new ArrayList();
		 if (col == 0 ){
			 edges.add(new Edge(v - w,v ,table[ligne - 1][col]));
			 edges.add(new Edge(v - w + 1,v ,table[ligne - 1][col + 1]));
		 }else if (col == w-1){
			 edges.add(new Edge(v - w,v ,table[ligne - 1][col]));
			 edges.add(new Edge(v - w - 1,v ,table[ligne - 1][col - 1]));
		 }else {
			 edges.add(new Edge(v - w,v ,table[ligne - 1][col]));
			 edges.add(new Edge(v - w + 1,v ,table[ligne - 1][col + 1]));
			 edges.add(new Edge(v - w - 1,v ,table[ligne - 1][col - 1]));
		 }

		 return edges;
		      
	 }

    
}
