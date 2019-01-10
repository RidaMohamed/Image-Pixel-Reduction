public class MainClass {

    public static void main(String[] args){

        SeamCarving seamCarving = new SeamCarving();
        Functions functions = new Functions();
        int [][] image = seamCarving.readpgm("ex1.pgm");
        functions.writepgm(image , "src/ex4.pgm");
        int [][] image2 = functions.interest(image);
        int [][] image3 = new int[3][4];
        for (int i = 0 ; i< 3 ; i ++ ){
            for (int j =0 ; j<4 ; j++){
                image3[i][j] = 5;
            }
        }
        Graph graph = functions.tograph(image3);
        graph.writeFile("src/graph_ex4.dot");
        DFS.testGraph();


    }

}
