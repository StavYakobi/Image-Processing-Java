

/*The program takes two command line arguments: 
the name of an input PPM file representing an image, 
and a number ​N representing a number of steps. and use functions to blurs the image and show it.
*/
public class BlurToDecay {
    static public void main(String[] args){
         int[][][] picArr = ImageOps.read(args[0]);
         int N = Integer.parseInt(args[1]);
         for(int i = 0;i < N;i++){
           ImageOps.edges(picArr);
           ImageOps.show(picArr);
           picArr = ImageOps.blurred(picArr);

         }
     }
}   
