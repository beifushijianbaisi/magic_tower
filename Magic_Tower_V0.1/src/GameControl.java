import java.util.Scanner;

public class GameControl {
	GameData gameData;
	Menu menu;

	GameControl(GameData gameData, Menu menu) {
		this.gameData = gameData;
		this.menu = menu;
	}

	void gameStart() {
		Scanner keyboardInput = new Scanner(System.in);
		while (true) {
			String input = keyboardInput.next();
			if (input.length() != 1 || (input.charAt(0) != 'a' && input.charAt(0) != 's' && input.charAt(0) != 'd'
					&& input.charAt(0) != 'w' && input.charAt(0) != '0')) {
				System.out.println("Wrong Input.");
				continue;
			}
			if (input.charAt(0) == '0')
				menu.enterMenu();
			else
				handleInput(input.charAt(0));
			gameData.printMap();
		}
	}

	void handleInput(char inC) {
		int tX = 0, tY = 0;
		if (inC == 'a') {
			tX = gameData.pX;
			tY = gameData.pY - 1;
		}
		if (inC == 's') {
			tX = gameData.pX + 1;
			tY = gameData.pY;
		}
		if (inC == 'd') {
			tX = gameData.pX;
			tY = gameData.pY + 1;
		}
		if (inC == 'w') {
			tX = gameData.pX - 1;
			tY = gameData.pY;
		}
		
		int tileValue = gameData.map[gameData.currentLevel][tX][tY];
		
		if (tileValue == 2) {
			gameData.keyNum++;
			moveHero(tX, tY);
		} else if (tileValue == 3 && gameData.keyNum > 0) {
			gameData.keyNum--;
			moveHero(tX, tY);
		} else if (tileValue == 4) {
			gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
			gameData.currentLevel++;
			for (int i = 0; i < gameData.H; i++)
				for (int j = 0; j < gameData.W; j++)
					if (gameData.map[gameData.currentLevel][i][j] == 6) {
						gameData.pX = i;
						gameData.pY = j;
					}
		} else if (tileValue == 5) {
			System.out.print("You Win!!");
			System.exit(0);
		} else if (tileValue > 10) { 
			int recoveryAmount = (int) (gameData.maxHeroHealth * 0.3);
			gameData.heroHealth = Math.min(gameData.heroHealth + recoveryAmount, gameData.maxHeroHealth);
			moveHero(tX, tY);
		} else if (tileValue == 1) {
			moveHero(tX, tY);
		} else if (tileValue < 0) {
			int monsterHealth = -tileValue;
			if (gameData.heroHealth <= monsterHealth) {
				System.out.print("That monster has " + monsterHealth + " power, You Lose!!");
				System.exit(0);
			} else {
				int increaseAmount = Math.max(5, Math.min(100, (int) (monsterHealth * 0.1)));
				gameData.maxHeroHealth += increaseAmount;
				gameData.heroHealth += tileValue;
				gameData.heroHealth = Math.min(gameData.heroHealth, gameData.maxHeroHealth);
				moveHero(tX, tY);
			}
		}
	}
	
	void moveHero(int tX, int tY) {
		gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
		gameData.map[gameData.currentLevel][tX][tY] = 6;
		gameData.pX = tX;
		gameData.pY = tY;
	}
}
