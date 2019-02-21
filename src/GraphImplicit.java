import java.util.ArrayList;

class GraphImplicit extends Graph{
    int N;
    
    GraphImplicit(int N){
	this.N = N;
    }

	GraphImplicit(int[] [] interest, int w, int h){

	}

    public int vertices(){
	return N;
    }
    
    public Iterable<Edge> next(int v)
	 {
	     ArrayList<Edge> edges = new ArrayList();
	     for (int i = v; i < N; i++)
		  edges.add(new Edge(v,i,i));
	     return edges;
		      
	 }

   public Iterable<Edge> prev(int v)
	 {
	     ArrayList<Edge> edges = new ArrayList();
	     for (int i = 0; i < v-1; i++)
		  edges.add(new Edge(i,v,v));
	     return edges;
		      
	 }

    
}
