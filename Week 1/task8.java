/*
 * Objective: Implement serialization and deserialization of a binary tree without using any built-in serialization functions.
    • Details: The serialization of a tree to a string must be such that it can be deserialized back to the original tree structure.
    • Functions to Implement:
        ◦ String serialize(TreeNode root): This function should take the root node of a binary tree and return a string representation of the tree. The serialization should capture all necessary details to allow the tree's structure and node values to be reconstructed.
        ◦ TreeNode deserialize(String data): This function takes a string representation of a binary tree and reconstructs the tree, returning the root node of the reconstructed tree.

 */

class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    private void DFS(StringBuilder out, TreeNode root) {
        if (root == null) {
            out.append("null,");
            return;
        }
        out.append(root.val).append(",");
        DFS(out, root.left);
        DFS(out, root.right);
    }

    public String serialize(TreeNode root) {
        StringBuilder out = new StringBuilder();
        DFS(out, root);
        return out.toString();
    }

    public TreeNode deserialize(String data) {
        String[] values = data.split(",");
        return deserializeHelper(values, new int[]{0});
    }

    private TreeNode deserializeHelper(String[] values, int[] index) {
        if (index[0] >= values.length || values[index[0]].equals("null")) {
            index[0]++;
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(values[index[0]++]), null, null);
        node.left = deserializeHelper(values, index);
        node.right = deserializeHelper(values, index);
        return node;
    }
}

public class task8 {
    public static void main(String[] args) {
        TreeNode n4 = new TreeNode(4, null, null);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n6 = new TreeNode(6, null, null);
        TreeNode n7 = new TreeNode(7, null, null);
        TreeNode n2 = new TreeNode(2, n4, n5);
        TreeNode n3 = new TreeNode(3, n6, n7);
        TreeNode root = new TreeNode(1, n2, n3);

        String serialized = root.serialize(root);
        System.out.println("Serialized: " + serialized);

        TreeNode deserializedRoot = root.deserialize(serialized);
        System.out.println("Deserialized: " + root.serialize(deserializedRoot));
    }
}
