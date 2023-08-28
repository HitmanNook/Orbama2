import processing.core.PApplet;
import processing.core.PImage;

import java.security.Key;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static processing.core.PConstants.CODED;

public class GameBoard {
	private Thing[][] grid;

	int ir, ic, tr, tc, count, er, ec, or, oc;
	int FinalBossRate = 0;
	boolean FinalBoss = false;
	int loss, win = 0;

	public GameBoard(int width, int height, PImage[] pics, PApplet p) {
		count = 0;
		grid = new Thing[height][width];
		grid[4][4] = new Player(5);
		ir = 4;
		ic = 4;
		newGrid();
	}

	public boolean move(int keyVal) {
		//get ir ic of player
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof Player) {
					ir = r;
					ic = c;
				}
			}
		}
		//calculate tr tc of player
		arrowMovement(keyVal);

		//reset tr tc of player if out grid
		if (isOutGrid(tr, tc)) {
			if (!FinalBoss) {
				outGridMovement(); //recalculation
			}else{
				tr = ir;
				tc = ic;
			}
			if (grid[tr][tc] == null) {
				grid[tr][tc] = grid[ir][ic];
				grid[ir][ic] = null;
			}

			return true;
		} //tr and tc will be reset now

		//if tr tc has enemy
		if (grid[tr][tc] instanceof Enemy) {
			if (battle()) {
				grid[tr][tc] = grid[ir][ic];
				grid[ir][ic] = null;
			} else {
				return false;
			}
		}


		//if tr tc is empty
		if(grid[tr][tc] == null){
			grid[tr][tc] = grid[ir][ic];
			grid[ir][ic] = null;
		}
		return true; // if move was valid, return true
	}

	public void arrowMovement(int keyVal){
		if(keyVal >= 0) {
			tr = ir;
			tc = ic;

			if (keyVal == 3) {
				tc = ic + 1;
			}
			if (keyVal == 2) {
				tc = ic - 1;
			}
			if (keyVal == 0) {
				tr = ir - 1;
			}
			if (keyVal == 1) {
				tr = ir + 1;
			}
		}
	}

	public boolean battle(){
		int i = (int)((Math.random()*100)+1);

		if(grid[tr][tc].level == MAX_VALUE){
			int rand = (int) ((Math.random()*100)+1);
			if(grid[ir][ic].level<1000){
				if(rand==1){
					win++;
				}else{
					loss++;
				}
			}
			if(grid[ir][ic].level>=1000){
				if(rand<=10){
					win++;
				}else{
					loss++;
				}
			}
			if(grid[ir][ic].level>=10000){
				if(rand<=50){
					win++;
				}else{
					loss++;
				}
			}
			if(grid[ir][ic].level>=100000){
				if(rand<=75){
					win++;
				}else{
					loss++;
				}
			}
		}
		if(grid[ir][ic].level > grid[tr][tc].level){
			grid[ir][ic].level = grid[ir][ic].level + grid[tr][tc].level;
			//win if player lvl greater than enemy lvl
			return true;
		}
		if(grid[ir][ic].level < grid[tr][tc].level){
			//maybe win if player lvl lesser than enemy lvl
			loss++;
			return false;
		}
		if(grid[ir][ic].level == grid[tr][tc].level) {
			//maybe win if player lvl equal to enemy lvl
			if (i > 50) {
				System.out.println("lost same lvl");
				loss++;
				return false;
			}
			System.out.println("won same lvl");
			grid[ir][ic].level = grid[ir][ic].level + grid[tr][tc].level;
		}
		return true;
	}

	public void outGridMovement(){
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (!(grid[r][c] instanceof Player)) {
					grid[r][c] = null;
				}
			}
		}

		if(tr < 0){
			tr = grid.length - 1;

		}
		if(tr >= grid.length){
			tr = 0;
		}
		if(tc < 0){
			tc = grid[0].length - 1;
		}
		//System.out.println(tc);
		//System.out.println(grid[0].length);
		if(tc >= grid[0].length){
			tc = 0;
		}
		//System.out.println(tr);
		//System.out.println(tc);
		newGrid();
	}

	public void newGrid() {
		count = 0;
		int rand = (int) ((Math.random() * 100) + 1);
		if (rand <= 5 && FinalBossRate > 0) {
			grid[4][4] = new Enemy(MAX_VALUE, 2);
			FinalBoss = true;
			//final boss
		} else {
			while (count < 5) {
				or = (int) (Math.random() * 9);
				oc = (int) (Math.random() * 9);
				er = (int) (Math.random() * 9);
				ec = (int) (Math.random() * 9);


				if (grid[or][oc] == null) {
					grid[or][oc] = new Obstacle(0, 0);
				}

				if (grid[er][ec] == null) {
					grid[er][ec] = new Enemy((int) (Math.random() * 5 + ((grid[ir][ic].level) - 2)), 1);
					count++;
					FinalBossRate++;
				}
			}
		}
	}

	public boolean isOutGrid(int r, int c) {
		if(r < 0 || r >= grid.length || c < 0 || c >= grid[0].length){
				return true;
		}
		return false;
	}

	public int isGameOver() {
		if (win > 0) {
			return 2;
		}
		if (loss > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	public void testOptions(){
		grid[ir][ic].level = 100000;
	}

	public Thing[][] getGrid() {
		return grid;
	}
}