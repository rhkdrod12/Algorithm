package DIjkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DijkstraArr {

    static StringTokenizer st;

    public static void main(String[] args) throws NumberFormatException, IOException {

//		int n = 4; // 마을 수
//		int m = 8; // 간선 수
//		int x = 2; // 타겟 마을
//
//		int[][] route = { { 1, 2, 4 }, { 1, 3, 2 }, { 1, 4, 7 }, { 2, 1, 1 }, { 2, 3, 5 }, { 3, 1, 2 }, { 3, 4, 4 },
//				{ 4, 2, 3 } };
//
//		int answer = solution(n, m, x, route);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());

        int[][] route = new int[m][3];
        for(int i = 0 ; i < m ; i++) {
            st = new StringTokenizer(br.readLine());
            route[i][0] = Integer.parseInt(st.nextToken());
            route[i][1] = Integer.parseInt(st.nextToken());
            route[i][2] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solution(n, m, x, route));
    }

    public static int solution(int n, int m, int x, int[][] route) {

        int answer = Integer.MIN_VALUE;

        // 노드 생성, node[i][j] -> i에서 j로 이동하는데 발생하는 최소 비용
        int[][] node = new int[n][n];

        // 노드에 본인은 0, 나머지는 무한대 비용으로 설정
        for (int i = 0; i < node.length; i++) {
            for (int j = 0; j < node[i].length; j++) {
                node[i][j] = i == j ? 0 : Integer.MAX_VALUE;
            }
        }

        // 경로에 따른 비용 입력
        // 배열의 시작 인덱스는 0이므로 1~4까지 값을 0~3까지로 변경
        for (int[] val : route) {
            node[val[0] - 1][val[1] - 1] = val[2];
        }

        // 각 마을에서 x로 이동할 때의 최소비용을 구하고 x에서 각 마을로 향할 때의 최소비용을 구해서 합하면 됨
        for (int i = 0; i < n; i++) {
            dijkstra(i, node);
        }

        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, node[i][x-1] + node[x-1][i]);
        }

        return answer;
    }

    public static void dijkstra(int sIdx, int[][] node) {

        // 방문한 노드를 재방문하면 안되니
        int[] dist = node[sIdx];
        boolean[] visited = new boolean[dist.length];
        visited[sIdx] = true; // 본인은 방문처리

        for (int i = 0; i < dist.length; i++) {
            // 최소비용으로 이동가능한 다음 Idx찾기
            int nIdx = getMinIdx(dist, visited);

            if (nIdx == -1) break;


            int cost = dist[nIdx]; // sIdx에서 nIdx까지 이동하는데 발생하는 비용
            int[] nDist = node[nIdx];
            visited[nIdx] = true;
            for (int j = 0; j < nDist.length; j++) {
                if (!visited[j] && nDist[j] != Integer.MAX_VALUE && dist[j] > cost + nDist[j]) {
                    dist[j] = cost + nDist[j];
                }
            }
        }

    }

    public static int getMinIdx(int[] dist, boolean[] visited) {
        int idx = -1;
        int cost = Integer.MAX_VALUE;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && cost > dist[i]) {
                cost = dist[i];
                idx = i;
            }
        }
        return idx;
    }
}
