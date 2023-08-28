import processing.core.*;

import javax.swing.*;

public class RunGraphicalGame extends PApplet {
	GameBoard grid;

	Display display;
	int frame;
	PImage[] list;

	public void settings() {
		size(640, 550);
	}

	public void setup() {
		list = new PImage[]{loadImage("frame_00_delay-0.06s.gif"), loadImage("frame_01_delay-0.06s.gif"), loadImage("frame_02_delay-0.06s.gif"),
				loadImage("frame_03_delay-0.06s.gif"), loadImage("frame_04_delay-0.06s.gif"), loadImage("frame_05_delay-0.06s.gif"),
				loadImage("frame_06_delay-0.06s.gif"), loadImage("frame_07_delay-0.06s.gif"), loadImage("frame_08_delay-0.06s.gif"),
				loadImage("frame_09_delay-0.06s.gif"), loadImage("frame_10_delay-0.06s.gif"), loadImage("frame_11_delay-0.06s.gif"),
				loadImage("frame_12_delay-0.06s.gif"), loadImage("frame_13_delay-0.06s.gif"), loadImage("frame_14_delay-0.06s.gif"),
				loadImage("frame_15_delay-0.06s.gif"), loadImage("frame_16_delay-0.06s.gif"), loadImage("frame_17_delay-0.06s.gif"),
				loadImage("frame_18_delay-0.06s.gif"), loadImage("frame_19_delay-0.06s.gif"), loadImage("frame_20_delay-0.06s.gif"),
				loadImage("frame_21_delay-0.06s.gif"), loadImage("frame_22_delay-0.06s.gif"), loadImage("frame_23_delay-0.06s.gif"),
				loadImage("frame_24_delay-0.06s.gif"), loadImage("frame_25_delay-0.06s.gif"), loadImage("frame_26_delay-0.06s.gif"),
				loadImage("frame_27_delay-0.06s.gif"), loadImage("frame_28_delay-0.06s.gif"), loadImage("frame_29_delay-0.06s.gif"),
				loadImage("frame_30_delay-0.06s.gif"), loadImage("frame_31_delay-0.06s.gif"), loadImage("frame_32_delay-0.06s.gif"),
				loadImage("frame_33_delay-0.06s.gif"), loadImage("frame_34_delay-0.06s.gif"), loadImage("frame_35_delay-0.06s.gif")};
		PImage i = list[1];
		grid = new GameBoard(9, 9, list, this);
		display = new Display(this, 10, 10, 400, 400);

		display.setImage(1, "cia.jpg");
		display.setImage(2, "BidenBlast.jpg");
		display.initializeWithGame(grid);
		frame = 0;
	}

	@Override
	public void draw() {
		background(200);
		display.drawGrid(grid.getGrid(), list[frame % 36]); // display the game
		frame ++;
		if(grid.isGameOver()==1){
			fill(255,0,0);
			textSize(100);
			text("You LOST",100,225);
			textSize(13);
		}
		if(grid.isGameOver()==2){
			fill(255,0,0);
			textSize(100);
			text("You WON",100,225);
			textSize(13);
		}
	}

	public void keyReleased() {
		int keyVal = -1;
		if(keyPressed==true){
			if(key=='b'){
				keyVal = 100;
			}
		}
		if(key == CODED){
			if(keyCode == UP){
				keyVal = 0;
			}
			if(keyCode == DOWN){
				keyVal = 1;
			}
			if(keyCode == LEFT){
				keyVal = 2;
			}
			if(keyCode == RIGHT){
				keyVal = 3;
			}
			if(grid.isGameOver()==0) {
				grid.move(keyVal);
			}
		}
	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}