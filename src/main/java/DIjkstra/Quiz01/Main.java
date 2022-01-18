package DIjkstra.Quiz01;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
        /*
          [문제]
          젤다의 전설 게임에서 화폐의 단위는 루피(rupee)다. 그런데 간혹 '도둑루피'라 불리는 검정색 루피도 존재하는데,
          이걸 획득하면 오히려 소지한 루피가 감소하게 된다!
          젤다의 전설 시리즈의 주인공, 링크는 지금 도둑루피만 가득한 N x N 크기의 동굴의 제일 왼쪽 위에 있다.
          [0][0]번 칸이기도 하다. 왜 이런 곳에 들어왔냐고 묻는다면 밖에서 사람들이 자꾸 "젤다의 전설에 나오는 녹색 애가 젤다지?"라고 물어봤기 때문이다.
          링크가 녹색 옷을 입은 주인공이고 젤다는 그냥 잡혀있는 공주인데,
          게임 타이틀에 젤다가 나와있다고 자꾸 사람들이 이렇게 착각하니까 정신병에 걸릴 위기에 놓인 것이다.
          하여튼 젤다...아니 링크는 이 동굴의 반대편 출구, 제일 오른쪽 아래 칸인 [N-1][N-1]까지 이동해야 한다.
          동굴의 각 칸마다 도둑루피가 있는데, 이 칸을 지나면 해당 도둑루피의 크기만큼 소지금을 잃게 된다.
          링크는 잃는 금액을 최소로 하여 동굴 건너편까지 이동해야 하며, 한 번에 상하좌우 인접한 곳으로 1칸씩 이동할 수 있다.
          링크가 잃을 수밖에 없는 최소 금액은 얼마일까?
         */

        /*
          [입력]
          입력은 여러 개의 테스트 케이스로 이루어져 있다.
          각 테스트 케이스의 첫째 줄에는 동굴의 크기를 나타내는 정수 N이 주어진다. (2 ≤ N ≤ 125) N = 0인 입력이 주어지면 전체 입력이 종료된다.
          이어서 N개의 줄에 걸쳐 동굴의 각 칸에 있는 도둑루피의 크기가 공백으로 구분되어 차례대로 주어진다.
          도둑루피의 크기가 k면 이 칸을 지나면 k 루피를 잃는다는 뜻이다. 여기서 주어지는 모든 정수는 0 이상 9 이하인 한 자리 수다.
         */

        // 그냥 간단하게 거기 까지 가는데 발생하는 비용인거자나
        // 그리고 해당위치까지 가는데 최소비용이 발생하는 경로를 구하는 문제

        int n = 3;
        int[][] route = {
                {5, 5, 4},
                {3, 9, 1},
                {3, 2, 7}};

        solution(n, route);
    }

    public static void solution(int n, int[][] route) {

        

        // 일단 시작점과 도착점의 비용정보는 그리 중요하지 않음, 어차피 무조건 빠지는 값이니..
        // 이동 가능한 경로를 저장해야하는데
        // 임의로 번호를 배치해야하려나..?
        // 순차적으로 0 ~ n*n-1으로 번호를 부여한다고 하면
        //n 1 2 3
        //1 0 1 2
        //2 3 4 5   // 3/3 으로 해서 몫이 있으면 위로 이동 가능 3%3 해서 나머지가 있으면 왼쪽으로 이동가능?
        //3 6 7 8   // 그럼 오른쪽은? 나머지 했을 때 n-1이면 오른쪽은 불가, 아래는 몫이 n-1이면 아래로 이동 불가
        //
        //n  01 02 03 04
        //01 01 02 03 04
        //02 05 06 07 08
        //03 09 10 11 12
        //04 13 14 15 16
        
        // (i-1)*n + j
        
        // (i+1)*(j+1)-1을 노드 번호라고 합시다~ 그리고 문제는 0 -> 8까지 가는데 필요한 비용인거자나
        
        // 0 -> 1,3
        // 1 -> 0,2,4
        // 4 -> 1,3,5,7
        
        int[][] dp = new int[n*n][n*n]; //dp[i][j] = i -> j로 가는데 발생하는 비용
        PriorityQueue<Node>[] nodes = new PriorityQueue[n*n];    // Node[i][j] i -> j로 이동하는데 발생하는 비용정보
        // 경로정보 만들기
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            nodes[i] = new PriorityQueue<>();
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int idx = i*n+j;
                // 좌표가 (i, j) 일때
                // (i-1, j)     i>0
                // (i-1, j-1)   i>0,    j>0
                // (i, j+1)     j<n-1
                // (i+1, j+1)   i<n-1,  j<n-1
    
                System.out.println("idx = " + idx);
                int leftIdx = idx % n > 0 ? ((i) * n + j-1) : -1;
                int upIdx = idx / n > 0 ? ((i - 1) * n + (j)) : -1;
                int rightIdx = idx % n < n - 1 ? ((i) * n + (j + 1)) : -1;
                int downIdx = idx / n < n - 1 ? ((i + 1) * n + (j)) : -1;
     
                dp[idx][idx] = 0;
                if(leftIdx != -1){
                    dp[idx][leftIdx] = route[i][j-1];
                    nodes[idx].add(new Node(leftIdx, route[i][j - 1]));
                }
                if(upIdx != -1){
                    dp[idx][upIdx] = route[i-1][j];
                    nodes[idx].add(new Node(upIdx, route[i-1][j]));
                }
                if(rightIdx != -1){
                    dp[idx][rightIdx] = route[i][j+1];
                    nodes[idx].add(new Node(rightIdx, route[i][j+1]));
                }
                if(downIdx != -1){
                    dp[idx][downIdx] = route[i+1][j];
                    nodes[idx].add(new Node(downIdx, route[i+1][j]));
                }
            }
        }
        Arrays.asList(dp).forEach(e -> System.out.println(Arrays.toString(e)));

        for (int i = 0; i < nodes.length; i++) {
            System.out.printf("%d-> %s\n", i, nodes[i].toString());
        }
    }
    
    public static void dijkstra(int nodeNum, int )

}

class Node implements Comparable<Node> {
    int nodeNum;
    int cost;

    public Node(int nodeNum, int cost) {
        this.nodeNum = nodeNum;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return cost - o.cost;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeNum=" + nodeNum +
                ", cost=" + cost +
                '}';
    }
}
