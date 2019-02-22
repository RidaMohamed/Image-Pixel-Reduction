
import javax.swing.colorchooser.ColorChooserComponentFactory;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class Functions {

    private static final int maxval = 255;
    private static final String MAGIC = "P2";
    ArrayList arrayListVisited = new ArrayList();
    ArrayList<Integer> arrayListOrdrePrefixe = new ArrayList<Integer>();
    Stack<Integer> stack = new Stack<Integer>();
    Stack<Iterator> stackNext = new Stack<Iterator>();
    int sommet ;
    int voisin ;
    Iterator<Edge> it ;


    /**
     * fonction pour ecrire un fichier en format PGM
     * @param image
     * @param filename
     */
    public void writepgm (int[][] image, String filename){

        try {
            //
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filename));
            bufferedOutputStream.write(MAGIC.getBytes());
            bufferedOutputStream.write("\n".getBytes());
            bufferedOutputStream.write(Integer.toString(image[0].length).getBytes());
            bufferedOutputStream.write(" ".getBytes());
            bufferedOutputStream.write(Integer.toString(image.length).getBytes());
            bufferedOutputStream.write("\n".getBytes());
            bufferedOutputStream.write(Integer.toString(maxval).getBytes());
            bufferedOutputStream.write("\n".getBytes());
            String str = " ";

            for (int i = 0; i < image.length; ++i) {
                for (int j = 0; j < image[0].length; ++j) {
                    final int p = image[i][j];
                    if (p < -1 || p > maxval)
                        throw new IOException("Pixel value " + p + " outside of range [0, " + maxval + "].");
                        bufferedOutputStream.write(Integer.toString(image[i][j]).getBytes());
                        bufferedOutputStream.write(" ".getBytes());

                }
                bufferedOutputStream.write("\n".getBytes());
            }
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * fonction qui retourne un tableau representant
     * d'une image donnee en parametre
     * @param image
     * @return
     */
    public int[][] interest (int[][] image){
        //exeption en cas d'image vide
        int[][] tab = new int[image.length][image[0].length];
        int i ;
        int j ;

        for (i=0; i<image.length; i++){
            for (j=0; j<image[0].length; j++){
                if (j==0){
                    tab[i][j] = Math.abs(image[i][j]-image[i][j+1]);
                }else if (j==image[0].length-1){
                    tab[i][j]  = Math.abs(image[i][j]-image[i][j-1]);
                }else {
                    tab[i][j]  = Math.abs(image[i][j]-(image[i][j-1]+image[i][j+1])/2);
                }
            }
        }

       return tab;
    }

    /**
     * fonction qui permets la creation d'un graph
     *
     * @param itr
     * @return
     */
    public Graph tograph(int[][] itr) {

        //Des changements en suivant la fonction d'energie

        Graph graph ;
        //Creation d'un graphe de n sommets + les 2 sommets A et P
        GraphArrayList graphArrayList = new GraphArrayList(itr.length*itr[0].length+2);
        Edge edge ;
        int i, j ;

        for (i=0; i<itr[0].length ; i ++){

            edge = new Edge(0,i+1, 0);
            graphArrayList.addEdge(edge);
            //lieer le sommet A au sommet-i- avec cette arrete
        }

        //sommet
        int u =1;

        //contunier la creation des arretes
        for (i=0 ; i<itr.length-1; i++){
            for (j=0; j<itr[0].length; j++){
                if (j==0){
                    edge = new Edge(u,u+itr[0].length, Math.abs(itr[i][j+1] - 0));
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length+1, Math.abs(itr[i][j+1]  - itr[i+1][j]));
                    graphArrayList.addEdge(edge);
                }else if (j==itr[0].length-1){
                    edge = new Edge(u,u+itr[0].length, Math.abs(0 - itr[i][j]));
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length-1, Math.abs(itr[i][j-1] - itr[i+1][j]));
                    graphArrayList.addEdge(edge);
                }else {
                    //fonvtion d'energie
                    edge = new Edge(u,u+itr[0].length, Math.abs(itr[i][j+1]-itr[i][j-1]));
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length+1, Math.abs(itr[i][j+1] - itr[i+1][j]));
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length-1, Math.abs(itr[i][j-1] - itr[i+1][j]));
                    graphArrayList.addEdge(edge);

                    /*edge = new Edge(u,u+itr[0].length, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length+1, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length-1, itr[i][j]);
                    graphArrayList.addEdge(edge);*/
                }
                u++;
            }
        }

        //lieer les derniers arretes avec un sommet P
        for (j=0 ; j<itr[0].length ; j++){
            //Adapter a la fonction d'energie
            if ( j == 0)
                edge = new Edge(u,  itr.length*itr[0].length+1,itr[itr.length-1][j+1]);
            else if (j == itr[0].length -1)
                edge = new Edge(u,  itr.length*itr[0].length+1, itr[itr.length-1][j-1]);
            else
                edge = new Edge(u,  itr.length*itr[0].length+1,
                        Math.abs(itr[itr.length-1][j+1] -itr[itr.length-1][j-1]));

            graphArrayList.addEdge(edge);
            u++;
            /*edge = new Edge(u,  itr.length*itr[0].length+1, itr[itr.length-1][j]);
            graphArrayList.addEdge(edge);
            u++;*/
        }

        return graphArrayList;
    }

    /**
     *
     * @param g
     * @return
     */
    public ArrayList<Integer> tritopo(Graph g){


        int index = 0 ;

        //La liste des sommets visites reste la meme
        arrayListVisited =  new ArrayList();
        arrayListOrdrePrefixe = new ArrayList<Integer>();

        for (int u = 0 ; u < g.vertices() ; u++)
            arrayListVisited.add(false) ;

        //Depart
        stack.push(0);
        stackNext.push(g.next(0).iterator());

        while (!stack.empty()){

            sommet = stack.peek();
            it = stackNext.peek();
            index = stack.size()-1;

            while (it.hasNext()){
                voisin = it.next().to;
                if (arrayListVisited.get(voisin).equals(false)){

                    //Changer l'etat vers visite
                    arrayListVisited.set(voisin , true);

                    //ajouter le voisin et ces voisins
                    stack.push(voisin);
                    stackNext.push( g.next(voisin).iterator());
                }
            }
            stack.remove(index);
            stackNext.remove(it);
            arrayListOrdrePrefixe.add(sommet);

        }


        return arrayListOrdrePrefixe;
    }

    /**
     * Fonction DFS de trie toplogie
     * @param i
     * @param g
     */
    public void dsf(int i , Graph g){
/*
        arrayListVisited.set(i , true);
        for (Edge e:g.next(i)){
            if (arrayListVisited.get(e.to).equals(false)){
                dsf(e.to , g);
            }
        }
        arrayListOrdrePrefixe.add(i);*/
    }

    /**
     * Fonction d'algorithme de Bellman
     * @param g
     * @param s
     * @param t
     * @param order
     * @return
     */
    public ArrayList Bellman(Graph g, int s,int t, ArrayList<Integer> order){


        ArrayList<Integer> arrayListCCM = new ArrayList<Integer>();
        int [] tableCCM  = new int[order.size()];
        int min;
        int prev = 0;


        for (int u = 0 ; u<order.size() ; u++){
            tableCCM[u] = Integer.MAX_VALUE;
        }


        tableCCM[0] = 0 ;

        for (int i = 1 ; i< tableCCM.length ; i++){
            min = Integer.MAX_VALUE ;
            for (Edge e :g.prev(i)) {
                if (min > tableCCM[e.from] + e.cost){
                    min = tableCCM[e.from] + e.cost;
                    tableCCM[i] = min;
                }
            }
        }

        arrayListCCM.add(t);
        //here i am
        while (true){
            if (s == t)
                break;
            min = Integer.MAX_VALUE;
            for (Edge e:g.prev(t)){
                if (min>= tableCCM[e.from]  ){
                    if (tableCCM[e.to] == tableCCM[e.from]+e.cost){
                    prev = e.from;
                    min = tableCCM[e.from];
                    }
                }
            }
            t = prev;
            arrayListCCM.add(prev);
        }

        return arrayListCCM;
    }

    public void comprreser(String nomImage){

        SeamCarving seamCarving = new SeamCarving();
        ArrayList<Integer> CCMarrayList = new ArrayList<Integer>();
        int [][] image= new int[0][];
        int [][] image2= new int[0][];
        int [][] table = new int[0][];
        int [][] table2 = new int[0][];
        int ligne ;
        int col;

        image = seamCarving.readpgm(nomImage);


        for (int p = 0 ; p<50 ;p++) {

            table = new int[image.length][image[0].length-1];
            image2 = this.interest(image);
            Graph graph = this.tograph(image2);
            ArrayList<Integer> tritopoArrayList = this.tritopo(graph);
            CCMarrayList = this.Bellman(graph,0 , tritopoArrayList.size()-1 ,tritopoArrayList);
            Collections.reverse(CCMarrayList);

            for (int i = 1 ; i<CCMarrayList.size()-1; i++){
                ligne =  i-1;
                col   =  CCMarrayList.get(i)-(ligne*image[0].length)-1;
                for (int j = col ;j<image[0].length-1; j++)
                    image[ligne][j] = image[ligne][j+1];
            }

            for (int i = 0 ; i<image.length; i++){
                for (int j = 0 ; j<image[0].length-1;j++){
                    table[i][j] = image[i][j];
                }
            }
            image = table;

        }
        //--------------------------------------------

      this.writepgm(table, "src/"+nomImage+"-RES.pgm");
    }

}
