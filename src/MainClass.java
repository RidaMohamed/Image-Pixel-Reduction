
public class MainClass {

    public static void main(String[] args){
        long start = System.currentTimeMillis();



        if (args.length != 1) {
            System.err.println("Nombre incorrect d'arguments") ;
            System.err.println("\tjava -jar ImageCompress.jar <image.pgm>") ;
            System.exit(1) ;
        }
        Functions functions = new Functions();
        functions.comprreser(args[0]);

        long time = System.currentTimeMillis() - start;
        System.out.println(time);
    }

}
