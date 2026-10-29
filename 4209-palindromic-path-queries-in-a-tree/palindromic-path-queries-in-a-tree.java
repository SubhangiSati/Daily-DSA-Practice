class Solution {
    static class LazyPropagationSegmentTree {
        int n;
        int[] tree, lazy;

        LazyPropagationSegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            lazy = new int[4 * n];
            build(1, 0, n - 1, arr);
        }

        void build(int node, int l, int r, int[] arr) {
            if (l == r) {
                tree[node] = arr[l];
                return;
            }

            int mid = (l + r) / 2;
            build(node * 2, l, mid, arr);
            build(node * 2 + 1, mid + 1, r, arr);
            tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
        }

        void apply(int node, int l, int r, int mask) {
            if ((r - l + 1) % 2 == 1) {
                tree[node] ^= mask;
            }
            lazy[node] ^= mask;
        }

        void push(int node, int l, int r) {
            if (lazy[node] == 0 || l == r) {
                lazy[node] = 0;
                return;
            }

            int mid = (l + r) / 2;
            apply(node * 2, l, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, r, lazy[node]);
            lazy[node] = 0;
        }

        void update(int ql, int qr, int mask) {
            update(1, 0, n - 1, ql, qr, mask);
        }

        void update(int node, int l, int r, int ql, int qr, int mask) {
            if (qr < l || r < ql) {
                return;
            }

            if (ql <= l && r <= qr) {
                apply(node, l, r, mask);
                return;
            }

            push(node, l, r);

            int mid = (l + r) / 2;
            update(node * 2, l, mid, ql, qr, mask);
            update(node * 2 + 1, mid + 1, r, ql, qr, mask);

            tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
        }

        int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        int query(int node, int l, int r, int ql, int qr) {
            if (qr < l || r < ql) {
                return 0;
            }

            if (ql <= l && r <= qr) {
                return tree[node];
            }

            push(node, l, r);

            int mid = (l + r) / 2;

            return query(node * 2, l, mid, ql, qr)
                    ^ query(node * 2 + 1, mid + 1, r, ql, qr);
        }
    }

    static class Lift {
        int LOG;
        int[] depth;
        int[][] up;

        Lift(int n, List<Integer>[] graph) {
            LOG = 1;
            while ((1 << LOG) <= n) {
                LOG++;
            }

            depth = new int[n];
            up = new int[LOG][n];

            int[] parent = new int[n];
            Arrays.fill(parent, -1);

            int[] stack = new int[n];
            int top = 0;
            stack[top++] = 0;
            parent[0] = 0;

            while (top > 0) {
                int u = stack[--top];

                up[0][u] = parent[u];

                for (int k = 1; k < LOG; k++) {
                    up[k][u] = up[k - 1][up[k - 1][u]];
                }

                for (int v : graph[u]) {
                    if (v == parent[u]) {
                        continue;
                    }

                    parent[v] = u;
                    depth[v] = depth[u] + 1;
                    stack[top++] = v;
                }
            }
        }

        int lca(int a, int b) {
            if (depth[a] < depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int diff = depth[a] - depth[b];

            for (int k = 0; k < LOG; k++) {
                if (((diff >> k) & 1) != 0) {
                    a = up[k][a];
                }
            }

            if (a == b) {
                return a;
            }

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][a] != up[k][b]) {
                    a = up[k][a];
                    b = up[k][b];
                }
            }

            return up[0][a];
        }
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        int[] tin = new int[n];
        int[] size = new int[n];
        int[] parent = new int[n];
        int[] order = new int[n];
        int[] nextIndex = new int[n];

        Arrays.fill(parent, -1);

        int timer = 0;
        int[] stack = new int[n];
        int top = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[top - 1];

            if (nextIndex[u] == 0) {
                tin[u] = timer;
                order[timer++] = u;
                size[u] = 1;
            }

            if (nextIndex[u] < graph[u].size()) {
                int v = graph[u].get(nextIndex[u]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                stack[top++] = v;
            } else {
                top--;
                if (u != 0) {
                    size[parent[u]] += size[u];
                }
            }
        }

        int[] masks = new int[n];
        int[] nodeMask = new int[n];

        for (int i = 0; i < n; i++) {
            nodeMask[i] = 1 << (s.charAt(i) - 'a');
        }

        for (int i = 0; i < n; i++) {
            int u = order[i];

            if (u == 0) {
                masks[tin[u]] = nodeMask[u];
            } else {
                masks[tin[u]] = masks[tin[parent[u]]] ^ nodeMask[u];
            }
        }

        LazyPropagationSegmentTree segTree = new LazyPropagationSegmentTree(masks);

        Lift lift = new Lift(n, graph);

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int newMask = 1 << (c - 'a');
                int delta = nodeMask[u] ^ newMask;

                int left = tin[u];
                int right = tin[u] + size[u] - 1;

                segTree.update(left, right, delta);
                nodeMask[u] = newMask;

            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lift.lca(u, v);

                int maskU = segTree.query(tin[u], tin[u]);
                int maskV = segTree.query(tin[v], tin[v]);

                int pathMask = maskU ^ maskV ^ nodeMask[ancestor];

                result.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return result;
    }
}