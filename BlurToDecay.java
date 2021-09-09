/*
Assignment number  : 5.4
File Name          : ​BlurToDecay.java
Name               : Stav Yakobi
Student ID         : 206040149
Email              : Stav.Yakobi@post.idc.ac.il
*/

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