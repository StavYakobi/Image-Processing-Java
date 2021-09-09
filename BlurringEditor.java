


/*
theprogram that takes two command-line arguments:
 a PPM file name, followed by a number ​N​ which represents the number
of successive blur operations that the program will execute and displays the result
*/
public class BlurringEditor {
    static public void main(String[] args){
         int[][][] picArr = ImageOps.read(args[0]);
         int N = Integer.parseInt(args[1]);
         for(int i = 0;i < N;i++){
           picArr = ImageOps.blurred(picArr);
         }
         ImageOps.show(picArr);
       
     }
}   
