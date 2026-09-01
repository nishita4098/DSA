import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0, startC = 0;
        int litterCount = 0;

        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        // Find start and assign IDs to litter cells
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        // State: row, col, energy, mask
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, energy, 0});

        // visited[row][col][energy][mask]
        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << litterCount];

        visited[startR][startC][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                int[] cur = queue.poll();

                int r = cur[0];
                int c = cur[1];
                int e = cur[2];
                int mask = cur[3];

                // All litter collected
                if (mask == allCollected) {
                    return moves;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Outside grid
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Cannot move if no energy
                    if (e == 0) {
                        continue;
                    }

                    int ne = e - 1;
                    int nmask = mask;

                    // Collect litter
                    if (classroom[nr].charAt(nc) == 'L') {
                        int id = litterId[nr][nc];
                        nmask |= (1 << id);
                    }

                    // Reset energy on R
                    if (classroom[nr].charAt(nc) == 'R') {
                        ne = energy;
                    }

                    if (!visited[nr][nc][ne][nmask]) {
                        visited[nr][nc][ne][nmask] = true;
                        queue.offer(new int[]{nr, nc, ne, nmask});
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}