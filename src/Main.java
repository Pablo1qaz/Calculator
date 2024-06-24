import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class Main {

    private static boolean makeCommand = false;
    private static String lastCommand = "";
    private static double result = 0;
    private static double lastNumber = 0;

    public static void createAndShowGUI() {
        JFrame jf = new JFrame("My First Calculator");
        jf.setSize(500, 700);
        jf.setResizable(false);

        JPanel jp = new JPanel(new GridLayout(4, 4));
        jf.getContentPane().add(jp);

        JTextField jtf = new JTextField("0");
        jtf.setEditable(false);
        jf.getContentPane().add(jtf, BorderLayout.NORTH);
        jtf.setHorizontalAlignment(JTextField.RIGHT);
        jtf.setFont(jtf.getFont().deriveFont(Font.BOLD, 20f));

        ActionListener MyActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                System.out.println("Button = " + command);

                if ("0123456789.".contains(command)) {
                    if (makeCommand) {
                        jtf.setText(command);
                        makeCommand = false;
                    } else {
                        if (jtf.getText().equals("0")) {
                            jtf.setText(command);
                        } else {
                            jtf.setText(jtf.getText() + command);
                        }
                    }
                } else if ("/*-+".contains(command)) {
                    if (!jtf.getText().isEmpty()) {
                        lastCommand = command;
                        lastNumber = Double.parseDouble(jtf.getText());
                        result = lastNumber;
                        makeCommand = true;
                    }
                } else if ("=".equals(command)) {
                    if (!lastCommand.isEmpty()) {
                        if (!makeCommand) {
                            lastNumber = Double.parseDouble(jtf.getText());
                        }

                        switch (lastCommand) {
                            case "+":
                                result += lastNumber;
                                break;
                            case "-":
                                result -= lastNumber;
                                break;
                            case "*":
                                result *= lastNumber;
                                break;
                            case "/":
                                if (lastNumber != 0) {
                                    result /= lastNumber;
                                } else {
                                    jtf.setText("Error");
                                    return;
                                }
                                break;
                        }

                        jtf.setText(Double.toString(result));
                        makeCommand = true;
                    } else {
                        // If lastCommand is empty, just display the current number
                        jtf.setText(jtf.getText());
                        result = Double.parseDouble(jtf.getText());
                    }
                } else if ("C".equals(command)) {
                    jtf.setText("0");
                    result = 0;
                    lastCommand = "";
                    makeCommand = false;
                }
            }
        };

        MouseMotionListener myMouseMotionListener = new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                System.out.println("[MouseMotionListener] Dragged! Button = " + ((JButton) e.getSource()).getText());
            }

            public void mouseMoved(MouseEvent e) {
                System.out.println("[MouseMotionListener] Moved! Button = " + ((JButton) e.getSource()).getText());
            }
        };

        MouseListener myMouseListener = new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("[MouseListener] Clicked! Button = " + ((JButton) e.getSource()).getText());
            }

            public void mouseEntered(MouseEvent e) {
                System.out.println("[MouseListener] Entered! Button = " + ((JButton) e.getSource()).getText());
            }

            public void mouseExited(MouseEvent e) {
                System.out.println("[MouseListener] Exited! Button = " + ((JButton) e.getSource()).getText());
            }

            public void mousePressed(MouseEvent e) {
                System.out.println("[MouseListener] Pressed! Button = " + ((JButton) e.getSource()).getText());
            }

            public void mouseReleased(MouseEvent e) {
                System.out.println("[MouseListener] Released! Button = " + ((JButton) e.getSource()).getText());
            }
        };

        WindowListener myWindowListener = new WindowListener() {
            public void windowActivated(WindowEvent e) {
                System.out.println("[WindowListener] Activated!" + " visible=" + jf.isVisible() + ", active=" + jf.isActive());
            }

            public void windowClosed(WindowEvent e) {
                System.out.println("[WindowListener] Closed!");
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("[WindowListener] Closing!");
            }

            public void windowDeactivated(WindowEvent e) {
                System.out.println("[WindowListener] Deactivated!");
            }

            public void windowDeiconified(WindowEvent e) {
                System.out.println("[WindowListener] Deiconified!");
            }

            public void windowIconified(WindowEvent e) {
                System.out.println("[WindowListener] Iconified!");
                jf.setTitle("kuku");
                jf.setVisible(true);
            }

            public void windowOpened(WindowEvent e) {
                System.out.println("[WindowListener] Opened!" + " visible=" + jf.isVisible() + ", active=" + jf.isActive());
            }
        };

        WindowStateListener myWindowStateListener = new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                System.out.print("[WindowStateListener] State Changed: ");
                switch (e.getNewState()) {
                    case Frame.NORMAL:
                        System.out.println("Normal!");
                        break;
                    case Frame.ICONIFIED:
                        System.out.println("Iconified!");
                        break;
                    case Frame.MAXIMIZED_HORIZ:
                    case Frame.MAXIMIZED_VERT:
                    case Frame.MAXIMIZED_BOTH:
                        System.out.println("Maximized!");
                        break;
                    default:
                        System.out.println(e.getNewState());
                        break;
                }
            }
        };

        String[] buttons = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "=", "C", "/"
        };

        for (String but : buttons) {
            JButton jb = new JButton(but);
            jb.addActionListener(MyActionListener);
            jb.addMouseListener(myMouseListener);
            jb.addMouseMotionListener(myMouseMotionListener);
            jp.add(jb);
        }

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.addWindowListener(myWindowListener);
        jf.addWindowStateListener(myWindowStateListener);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
