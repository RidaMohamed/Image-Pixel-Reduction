
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Functions {

    private static final int maxval = 255;
    private static final String MAGIC = "P5";
    ArrayList<Integer> arrayListVisited = new ArrayList<Integer>();
    ArrayList<Integer> arrayListOrdrePrefixe = new ArrayList<Integer>();

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

            for (int i = 0; i < image.length; ++i) {
                for (int j = 0; j < image[0].length; ++j) {
                    final int p = image[i][j];
                    if (p < 0 || p > maxval)
                        throw new IOException("Pixel value " + p + " outside of range [0, " + maxval + "].");
                    bufferedOutputStream.write(image[i][j]);
                }
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
                    tab[i][j]  = Math.abs(image[i][j]-(image[i][j-1]+image[i][j+1]/2));
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

        Graph graph ;
        //Creation d'un graphe de n sommets + les 2 sommets A et P
        GraphArrayList graphArrayList = new GraphArrayList(itr.length*itr[0].length+2);
        Edge edge ;
        int i, j ;

        for (i=0; i<itr[0].length ; i ++){
            //creer le sommet A
            //creer un sommet-i-
            //creer un une arrete-i- avec un poids 0
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
                    edge = new Edge(u,u+itr[0].length, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length+1, itr[i][j]);
                    graphArrayList.addEdge(edge);
                }else if (j==itr[0].length-1){
                    edge = new Edge(u,u+itr[0].length, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length-1, itr[i][j]);
                    graphArrayList.addEdge(edge);
                }else {
                    edge = new Edge(u,u+itr[0].length, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length+1, itr[i][j]);
                    graphArrayList.addEdge(edge);
                    edge = new Edge(u,u+itr[0].length-1, itr[i][j]);
                    graphArrayList.addEdge(edge);
                }
                u++;
            }
        }

        //lieer les derniers arretes avec un sommet P
        for (j=0 ; j<itr[0].length ; j++){
            //creet l'arrete est la lieer au sommet-j-
            edge = new Edge(u,  itr.length*itr[0].length+1, itr[itr.length-1][j]);
            graphArrayList.addEdge(edge);
            u++;
        }

        return graphArrayList;
    }

    /**
     *
     * @param g
     * @return
     */
    public ArrayList<Integer> tritopo(Graph g){

        for (int i = 0; i<g.vertices(); i++){
            if (!arrayListVisited.contains(i)){
                dsf(i, g);
            }

        }

        Collections.reverse(arrayListOrdrePrefixe);
        return arrayListOrdrePrefixe;
    }

    /**
     * Fonction DFS de trie toplogie
     * @param i
     * @param g
     */
    public void dsf(int i , Graph g){

        arrayListVisited.add(i);
        for (Edge e:g.next(i)){
            if (!arrayListVisited.contains(e.to)){
                dsf(e.to , g);
            }
        }
        arrayListOrdrePrefixe.add(i);
    }

    /**
     *
     * @param g
     * @param s
     * @param t
     * @param order
     * @return
     */
    public ArrayList Bellman(Graph g, int s,int t, ArrayList order){

        return null;
    }
}
