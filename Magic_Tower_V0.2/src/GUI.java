import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
	GameData gameData;
	JFrame f;
	JLabel[][] b;

	GUI(GameData gameData) {
		this.gameData = gameData;
		f = new JFrame("Magic Tower");
		b = new JLabel[gameData.H][gameData.W];
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				b[i][j] = new JLabel();
				b[i][j].setBounds(j * 100, i * 100, 100, 100);
				int row = i; // 使用最终变量来传递行
				int col = j; // 使用最终变量来传递列
				b[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						handleMouseClick(row, col); // 处理鼠标点击
					}
				});
				f.add(b[i][j]);
			}
		}
		f.setSize(gameData.W * 100 + 10, gameData.H * 100 + 40);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		refreshGUI();
	}

	private void handleMouseClick(int i, int j) {
		int currentX = gameData.pX; // 获取玩家当前 X 坐标
		int currentY = gameData.pY; // 获取玩家当前 Y 坐标

		// 判断点击的格子是否相邻
		if ((Math.abs(currentX - i) == 1 && currentY == j) || (currentX == i && Math.abs(currentY - j) == 1)) {
			int targetValue = gameData.map[gameData.currentLevel][i][j];

			// 处理玩家移动的逻辑
			if (targetValue == 2) { // 钥匙
				gameData.keyNum++;
				moveHero(i, j);
			} else if (targetValue == 3 && gameData.keyNum > 0) { // 门
				gameData.keyNum--;
				moveHero(i, j);
			} else if (targetValue == 4) { // 楼梯
				// 当前层玩家位置重置为地板
				gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
				// 更新层数，进入下一层
				gameData.currentLevel++;

				// 找到新层玩家的初始位置
				boolean heroFound = false;
				for (int x = 0; x < gameData.H; x++) {
					for (int y = 0; y < gameData.W; y++) {
						if (gameData.map[gameData.currentLevel][x][y] == 6) { // 找到玩家位置
							gameData.pX = x;
							gameData.pY = y;
							heroFound = true;
							break;
						}
					}
					if (heroFound)
						break;
				}

				// 刷新界面以显示下一层地图
				refreshGUI();
			} else if (targetValue == 5) { // 终点
				System.out.print("You Win!!");
				System.exit(0);
			} else if (targetValue > 10) { // 生命药水
				gameData.heroHealth += targetValue;
				moveHero(i, j);
			} else if (targetValue == 1) { // 地板
				moveHero(i, j);
			} else if (targetValue < 0) { // 怪物
				if (gameData.map[gameData.currentLevel][i][j] + gameData.heroHealth <= 0) {
					System.out.print("You Lose!!");
					System.exit(0);
				} else {
					gameData.heroHealth += gameData.map[gameData.currentLevel][i][j];
					moveHero(i, j);
				}
			}
		}
	}
	
	private void moveHero(int i, int j) {
		// 将玩家当前位置的图标重置为地板
		gameData.map[gameData.currentLevel][gameData.pX][gameData.pY] = 1;
		// 更新玩家的新位置
		gameData.map[gameData.currentLevel][i][j] = 6;
		gameData.pX = i;
		gameData.pY = j;
		refreshGUI(); // 刷新图形界面
	}

	public void refreshGUI() {
		for (int i = 0; i < gameData.H; i++) {
			for (int j = 0; j < gameData.W; j++) {
				Image scaledImage = chooseImage(gameData.map[gameData.currentLevel][i][j]);
				b[i][j].setIcon(new ImageIcon(scaledImage));
			}
		}

		// 确保玩家位置图标更新
		int playerX = gameData.pX; // 玩家当前位置
		int playerY = gameData.pY;
		b[playerX][playerY].setIcon(new ImageIcon(chooseImage(6))); // 假设6是玩家的图标索引
	}

	private static Image chooseImage(int index) {
		ImageIcon[] icons = new ImageIcon[10];
		Image scaledImage;
		icons[0] = new ImageIcon("Wall.jpg");
		icons[1] = new ImageIcon("Floor.jpg");
		icons[2] = new ImageIcon("Key.jpg");
		icons[3] = new ImageIcon("Door.jpg");
		icons[4] = new ImageIcon("Stair.jpg");
		icons[5] = new ImageIcon("Exit.jpg");
		icons[6] = new ImageIcon("Hero.jpg");
		icons[7] = new ImageIcon("Potion.jpg");
		icons[8] = new ImageIcon("Monster.jpg");
		if (index > 10)
			scaledImage = icons[7].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else if (index < 0)
			scaledImage = icons[8].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		else
			scaledImage = icons[index].getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		return scaledImage;
	}
}
