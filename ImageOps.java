/*
Assignment number  : 5.1
File Name          : ​ImageOps.java
Name               : Stav Yakobi
Student ID         : 206040149
Email              : Stav.Yakobi@post.idc.ac.il
*/


/**
 * A library of image editing functions.
 */
public class ImageOps {
	// Use these constants, as needed.
	static final int NUM_OF_COLORS = 3;
	static final int R = 0;
	static final int G = 1;
	static final int B = 2;
    static final int MAX_COLOR_VALUE = 255;
    
/*	
@param fileName - name of the given PPM file
	 * @return - the image, as a 3-dimensional array
	 */
	public static int[][][] read(String filename) {
		StdIn.setInput(filename);
        StdIn.readString();
        int imgH = StdIn.readInt();//אורך
		int imgW = StdIn.readInt();//רוחב
	   StdIn.readInt();
	   int[][][] image = new int [imgW][imgH][3];
	   for(int i = 0; i < imgW; i++)
	   {
		   for(int k = 0; k < imgH; k++)
		   {
			   for(int j = 0;j < 3; j++)
			   {
					image[i][k][j]=StdIn.readInt();
			   }
		   }
	   }
	   return image;
	}
	
	/**
	 * Prints the array values, nicely formatted. 
	 * 
	 * @param pic - the image to display.
	 */
	public static void showData (int[][][] pic) {
        for(int i = 0; i < pic.length; i++)
        {
            for(int k = 0; k < pic[0].length; k++)
            {
                for(int j = 0;j < 3; j++)
                {
                    System.out.printf("%4s ",pic[i][k][j]);
                }
                System.out.print("   ");
            }
            System.out.println(" ");
        }
	}
	
	/**
	 * Renders an image, using StdDraw. 
	 * 
	 * @param pic - the image to render.
	 */
	public static void show(int[][][] pic) {
		StdDraw.setCanvasSize(pic[0].length, pic.length);
		int height = pic.length;
		int width = pic[0].length;
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
		StdDraw.show(30);
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				StdDraw.setPenColor(pic[i][j][R], pic[i][j][G], pic[i][j][B] );
				StdDraw.filledRectangle(j + 0.5, height - i - 0.5, 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
	
	/**
	 * Flips an image horizontally.
	 * SIDE EFFECT: Changes the given image.
	 * 
	 * @param pic - the image to flip
	 */
	public static void flipHorizontally(int[][][] pic) {
		for(int i = 0; i < pic.length; i++){
            for(int j = 0; j < pic[0].length/2; j++){
                swap(pic,i,j,i,pic[0].length-1-j);
            }
        }
	}
	
	/**
	 * Flips an image vertically
	 * * SIDE EFFECT: Changes the given image.
	 * 
	 * @param pic - the image to flip
	 */
	public static void flipVertically(int[][][] pic) {
		for(int i = 0; i < pic.length/2; i++){
            for(int j = 0; j < pic[0].length; j++){
                swap(pic,i,j,pic.length-1-i,j);
            }
        }
	}
	
	// Swaps the two given pixels in the given image.
	// SIDE EFFECT: Changes the pixles in the given image.
	// i1,j1 - coordinates of the first pixel
	// i2,j2 - coordinates of the second pixel
	private static void swap(int[][][] pic, int i1, int j1, int i2, int j2) {
        for(int i = 0; i < 3; i++){
            int x = pic[i1][j1][i];
            int y = pic[i2][j2][i];
            pic[i1][j1][i] = y;
            pic[i2][j2][i] = x;
        }
	}
	
	/**
	 * Turns an RGB color into a greycale value, using a luminance formula.
	 * The luminance is a weighted mean of the color's value, and is given by:
	 * 0.299 * r + 0.587 * b + 0.114 * b.
	 * 
	 * @param color - the color to be greyScaled.
	 * @return the greyscale value, as an array {greyscale, greyscale, greyscale}
	 */
	public static int[] luminance(int[] color) {
        int sum = (int)(color[0]*0.299+color[1]*0.587+color[2]*0.114);
        for(int i = 0; i < 3; i++){
            color[i] = sum;
        }
		return color;
	}
	
	/**
	 * Creates a greyscaled version of an image.
	 * 
	 * @param pic - the given image
	 * @return - the greyscaled version of the image
	 */
	public static int[][][] greyScaled (int[][][] pic) {
		for(int i = 0;i < pic.length;i++){
            for(int j = 0; j < pic[0].length;j++){
                luminance(pic[i][j]);
            }
        }
		return pic;
	}	
	
	/**
	 * Creates a blurred version of an image.
	 * Uses a block blur algorithm.
	 * 
	 * @param pic - the given image
	 * @return - the blurred version of the image
	 */
	public static int[][][] blurred (int[][][] pic) {
        int [][][] blurredPic = new int[pic.length][pic[0].length][3];
        for(int i = 0; i < pic.length; i++){
            for(int j = 0;j < pic[0].length; j++){
                for(int x = 0; x < 3;x++){
                    blurColor(pic, blurredPic, i, j, x);
                }
            }
        }
		return blurredPic;
	}

	// Blurs a given color of a given pixel in a given image.
	// Stores the result in a blurred version of the given image, without effecting the given image.
	// Uses a block blur algorithm.
	// pic - the given image
	// blurredPic - the blurred version of the given image
    // row - the row of the pixel
	// col - the column of the pixel
	// color - the color to blur: 0-red, 1-green, 2-blue
	private static void blurColor(int[][][] pic, int[][][] blurredPic, int row, int col, int color) {
        int sum = 0;
        int count = 0;
        for(int i = row-1; i <= row+1;i++){
            for(int j = col-1; j <= col+1; j++){
                int x = getColorIntensity(pic,i,j,color);
                if(x != -1){
                    sum = sum + x;
                    count++;
                }
            }
        }
        blurredPic[row][col][color] = sum/count;
	}
	
	// Returns the color intensity of a pixel, or -1 if the coordinates of the pixel are illegal.
	// pic - the given source image
	// row - the given row of the pixel
	// col - the given column of the pixel
	// color - the given color: 0-red, 1-green, 2-blue
	private static int getColorIntensity(int[][][] pic, int row, int col, int color) {
		if(row >= pic.length-1){
            return -1;
        }
        if(row < 0){
            return -1;
        }
        if(col < 0){
            return -1;
        }
        if(col >= pic[0].length-1){
            return -1;
        }
		return pic[row][col][color];
	}
	
	/**
	 * Calculates the horizontal gradient of the greyscaled version of an image
	 * 
	 * @param pic - the given image
	 * @return - the gradient of the greyscaled version of the given image.
	 */
	public static int[][] horizontalGradient(int[][][] pic) {
		int [][] newArr = new int [pic.length][pic[0].length];
		for(int i = 0;i < pic.length;i++){
			newArr[i][0] = 0;
		}
		for(int j = 0; j < pic.length; j++){
			newArr[j][pic[0].length-1] = 0;
		}
		for( int k = 0; k < pic.length; k++){
			for( int x =1; x < pic[0].length-1; x++){
				newArr[k][x]=pic[k][x+1][1]-pic[k][x-1][1];
			}
		}
		return newArr;
	}
	
	/**
	 * Normalizes a 2D array so that all the values are between 0 to 255
	 * SIDE EFFECT: Changes the given array
	 * 
	 * @param arr - the given array
	 */
	public static void normalize(int[][] arr) {
		int numMax = 0;
		int numMin = 255;
		for(int i = 0;i < arr.length;i++){
			for(int j = 0;j < arr[0].length;j++){
				if(arr[i][j] > numMax){
					numMax = arr[i][j];
				}
				if(arr[i][j] < numMin){
					numMin = arr[i][j];
				}
			}
		}
		double a = (double)numMax / (numMax - numMin);
		for(int i = 0; i < arr.length;i++){
			for(int j = 0; j < arr[0].length;j++){
				double b = (double)(arr[i][j]-numMin);
				arr[i][j] = (int)(a*b);
			}
		}
	}
	
	/**
	 * Creates a greyscaled image showing the horizontal edges of a given image.
	 * Uses gradient edge detection.
	 * 
	 * @param pic - the given image
	 * @return - a greyscaled image showing the horizontal edges of the given image
	 */
	public static int[][][] edges(int[][][] pic) {
		pic = greyScaled(pic);
		int[][] arr = horizontalGradient(pic);
		normalize(arr);
		for(int i = 0;i < pic.length;i++){
			for(int j = 0; j < pic[0].length;j++){
				for(int c = 0;c < 3; c++){
					pic[i][j][c] = arr[i][j];
				}
			}
		}
		return pic;
	}
}

