class Solution {

    class Node {
        int prod;
        long[] pref;

        Node(int k) {
            pref = new long[k];
        }
    }

    int n, k;
    Node[] tree;

    private Node merge(Node left, Node right) {
        Node res = new Node(k);

        res.prod = (left.prod * right.prod) % k;

        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        for (int r = 0; r < k; r++) {
            int newRemainder = (left.prod * r) % k;
            res.pref[newRemainder] += right.pref[r];
        }

        return res;
    }

    private Node createNode(int value) {
        Node node = new Node(k);

        int remainder = value % k;

        node.prod = remainder;
        node.pref[remainder] = 1;

        return node;
    }

    private void build(int[] nums, int index, int left, int right) {
        if (left == right) {
            tree[index] = createNode(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(nums, index * 2, left, mid);
        build(nums, index * 2 + 1, mid + 1, right);

        tree[index] = merge(tree[index * 2], tree[index * 2 + 1]);
    }

    private void update(int index, int left, int right,
                        int position, int value) {

        if (left == right) {
            tree[index] = createNode(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (position <= mid) {
            update(index * 2, left, mid, position, value);
        } else {
            update(index * 2 + 1, mid + 1, right, position, value);
        }

        tree[index] = merge(tree[index * 2], tree[index * 2 + 1]);
    }

    private Node query(int index, int left, int right,
                       int queryLeft, int queryRight) {

        if (queryLeft <= left && right <= queryRight) {
            return tree[index];
        }

        int mid = left + (right - left) / 2;

        if (queryRight <= mid) {
            return query(index * 2, left, mid, queryLeft, queryRight);
        }

        if (queryLeft > mid) {
            return query(index * 2 + 1, mid + 1, right,
                         queryLeft, queryRight);
        }

        Node leftNode = query(index * 2, left, mid,
                              queryLeft, queryRight);

        Node rightNode = query(index * 2 + 1, mid + 1, right,
                               queryLeft, queryRight);

        return merge(leftNode, rightNode);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        build(nums, 1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            update(1, 0, n - 1, index, value);

            Node node = query(1, 0, n - 1, start, n - 1);

            result[q] = (int) node.pref[x];
        }

        return result;
    }
}