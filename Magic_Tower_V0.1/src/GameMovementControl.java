import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMovementControl extends JFrame {
    private int x = 5; // 角色初始的x坐标
    private int y = 5; // 角色初始的y坐标
    private JLabel characterPositionLabel;

    public GameMovementControl() {
        setTitle("角色移动控制");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 角色位置显示
        characterPositionLabel = new JLabel("角色位置: (" + x + ", " + y + ")");
        add(characterPositionLabel, BorderLayout.NORTH);

        // 添加按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        // 占位符
        buttonPanel.add(new JLabel());

        // "上" 按钮
        JButton upButton = new JButton("上");
        buttonPanel.add(upButton);

        // 占位符
        buttonPanel.add(new JLabel());

        // "左" 按钮
        JButton leftButton = new JButton("左");
        buttonPanel.add(leftButton);

        // "下" 按钮
        JButton downButton = new JButton("下");
        buttonPanel.add(downButton);

        // "右" 按钮
        JButton rightButton = new JButton("右");
        buttonPanel.add(rightButton);

        add(buttonPanel, BorderLayout.CENTER);

        // 为每个按钮添加动作监听器
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveUp();
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveLeft();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveRight();
            }
        });

        setVisible(true);
    }

    // 移动逻辑
    private void moveUp() {
        y--;
        updateCharacterPosition();
    }

    private void moveLeft() {
        x--;
        updateCharacterPosition();
    }

    private void moveDown() {
        y++;
        updateCharacterPosition();
    }

    private void moveRight() {
        x++;
        updateCharacterPosition();
    }

    // 更新角色位置的显示
    private void updateCharacterPosition() {
        characterPositionLabel.setText("角色位置: (" + x + ", " + y + ")");
    }

    public static void main(String[] args) {
        new GameMovementControl();
    }
}
