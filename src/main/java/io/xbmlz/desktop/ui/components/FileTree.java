package io.xbmlz.desktop.ui.components;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;

public class FileTree extends JTree {

    private static final FileSystemView fsv = FileSystemView.getFileSystemView();

    private static String rootPath;

    public FileTree(String rootPath) {
        FileTree.rootPath = rootPath;
        init();
    }

    public FileTree() {
        init();
    }

    private void init() {
        setRootVisible(false);
        setRowHeight(20);
        setModel(new FileTreeModel(new DefaultMutableTreeNode(new FileNode(
                "root",
                null,
                null
        ))));
        expandRow(0);
        setCellRenderer(new FileTreeCellRenderer());
        addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode parentNode = ((DefaultMutableTreeNode) event.getPath()
                        .getLastPathComponent());
                parentNode.removeAllChildren();
                FileNode fileNode = (FileNode) parentNode.getUserObject();
                File[] subFiles = fsv.getFiles(fileNode.file, false);
                for (File subFile : subFiles) {
                    parentNode.add(
                            new DefaultMutableTreeNode(new FileNode(
                                    fsv.getSystemDisplayName(subFile),
                                    fsv.getSystemIcon(subFile),
                                    subFile
                            )));
                }
                ((DefaultTreeModel) getModel()).nodeStructureChanged(parentNode);
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                node.removeAllChildren();
                node.add(new DefaultMutableTreeNode(new FileNode("child", null, null)));
            }
        });
    }

    private static class FileTreeModel extends DefaultTreeModel {

        public FileTreeModel(TreeNode root) {
            super(root);
            File[] subFiles;
            if (rootPath == null) {
                subFiles = fsv.getHomeDirectory().listFiles();
            } else {
                subFiles = fsv.getFiles(new File(rootPath), false);
            }
            for (File subFile : subFiles) {
                ((DefaultMutableTreeNode) root).add(
                        new DefaultMutableTreeNode(new FileNode(
                                fsv.getSystemDisplayName(subFile),
                                fsv.getSystemIcon(subFile),
                                subFile
                        )));
            }
        }

        @Override
        public boolean isLeaf(Object node) {
            FileNode fileNode = (FileNode) ((DefaultMutableTreeNode) node).getUserObject();
            if (fileNode.file == null)
                return false;
            return fileNode.file.isFile();
        }
    }

    private static class FileNode {
        public String name;

        public Icon icon;

        public File file;

        public FileNode(String name, Icon icon, File file) {
            this.name = name;
            this.icon = icon;
            this.file = file;
        }
    }

    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                    hasFocus);
            FileNode fileNode = (FileNode) ((DefaultMutableTreeNode) value).getUserObject();
            if (fileNode.icon != null) {
                label.setIcon(fileNode.icon);
                label.setText(fileNode.name);
            }
            return label;
        }
    }
}
