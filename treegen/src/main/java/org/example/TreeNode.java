package org.example;

public class TreeNode {
    private int _id;

    TreeNode(int id) {
        _id = id;
    }

    public String toString() {
        return Integer.toString(_id);
    }

    public int id() {
        return _id;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _id;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TreeNode)) return false;
        return _id == ((TreeNode) o)._id;
    }

}
