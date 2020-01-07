package venta.fotos_cliente;


import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JTreeRemoveNode extends JFrame {
    public JTreeRemoveNode() throws HeadlessException {
        initializeUI();
    }

    private void initializeUI() {
        setSize(200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Address Book");
        String[] names = new String[] {"Alice", "Bob", "Carol", "Mallory"};
        for (String name : names) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
            root.add(node);
        }
        
        final JTree tree = new JTree(root);
        JScrollPane pane = new JScrollPane(tree);
        pane.setPreferredSize(new Dimension(200, 400));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

                TreePath[] paths = tree.getSelectionPaths();
                for (TreePath path : paths) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.getParent() != null) {
                        model.removeNodeFromParent(node);
                    }
                }*/

            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tree, BorderLayout.CENTER);
        getContentPane().add(removeButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JTreeRemoveNode().setVisible(true);
            }
        });
    }
}