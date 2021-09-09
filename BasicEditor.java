

/*
The program takes two command-line arguments, the name of a ppm picture,
followed by an optional second command-line argument which is either ​fh​, ​fv​, or ​gr​. 
the program displays the image horizontally flipped, vertically flipped, or greyscaled
according to the second command-line argument. and show the picture
*/
public class BasicEditor {
    static public void main(String[] args){
         int[][][] picArr = ImageOps.read(args[0]);
         if(args.length == 2){
             String picChange = args[1];
         
             if(picChange.equals("fh")){
                  ImageOps.flipHorizontally(picArr);
             }
             else if(picChange.equals("fv")){
                 ImageOps.flipVertically(picArr);
             }
             else if(picChange.equals("gr")){
                 ImageOps.greyScaled(picArr);
             }
         }
         ImageOps.show(picArr);
     }
}   
