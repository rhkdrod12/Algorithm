package DIjkstra.Quiz03;

import java.util.*;

public class Main {

    /*
        1번부터 3번까지 번호가 붙어있는 3개의 방이 있고, 방과 방 사이를 연결하는 길에는 이동하는데 걸리는 시간이 표시되어 있습니다.
        길은 화살표가 가리키는 방향으로만 이동할 수 있습니다. 미로에는 함정이 존재하며, 함정으로 이동하면, 이동한 함정과 연결된 모든 화살표의 방향이 바뀝니다.
        출발지점인 1번 방에서 탈출이 가능한 3번 방까지 이동해야 합니다. 탈출하는데 걸리는 최소 시간을 구하려고 합니다.
        
        그림의 원은 방을 나타내며 원 안의 숫자는 방 번호를 나타냅니다.
        방이 n개일 때, 방 번호는 1부터 n까지 사용됩니다.
        화살표에 표시된 숫자는 방과 방 사이를 이동할 때 걸리는 시간을 나타냅니다.
        화살표가 가리키고 있는 방향으로만 이동이 가능합니다. 즉, 위 그림에서 2번 방에서 1번 방으로는 이동할 수 없습니다.
        그림에 표시된 빨간색 방인 2번 방은 함정입니다.
        함정 방으로 이동하는 순간, 이동한 함정 방과 연결되어있는 모든 길의 방향이 반대가 됩니다.
        위 그림 1번 방에서 2번 방으로 이동하는 순간 1에서 2로 이동할 수 있던 길은 2에서 1로 이동할 수 있는 길로 바뀌고,
        3에서 2로 이동할 수 있던 길은 2에서 3으로 이동할 수 있는 길로 바뀝니다.
        똑같은 함정 방을 두 번째 방문하게 되면 원래 방향의 길로 돌아옵니다.
        즉, 여러 번 방문하여 계속 길의 방향을 반대로 뒤집을 수 있습니다.
        미로를 탈출하는데 필요한 최단 시간은 다음과 같습니다.
        1→2: 2번 방으로 이동합니다. 이동 시간은 2입니다.
        함정 발동: 2번 방과 연결된 모든 길의 방향이 반대가 됩니다.
        2→3: 3번 방으로 이동합니다. 이동 시간은 3입니다.
        탈출에 성공했습니다. 총 이동시간은 5입니다.
        방의 개수를 나타내는 정수 n, 출발 방의 번호 start, 도착 방의 번호 end, 통로와 이동시간을 나타내는 2차원 정수 배열 roads,
        함정 방의 번호를 담은 정수 배열 traps이 매개변수로 주어질 때, 미로를 탈출하는데 필요한 최단 시간을 return 하도록 solution 함수를 완성해주세요.
  
        제한사항
        2 ≤ n ≤ 1,000
        1 ≤ start ≤ n
        1 ≤ end ≤ n
        1 ≤ roads의 행 길이 ≤ 3,000
        roads의 행은 [P, Q, S]로 이루어져 있습니다.
        P에서 Q로 갈 수 있는 길이 있으며, 길을 따라 이동하는데 S만큼 시간이 걸립니다.
        1 ≤ P ≤ n
        1 ≤ Q ≤ n
        P ≠ Q
        1 ≤ S ≤ 3,000
        서로 다른 두 방 사이에 직접 연결된 길이 여러 개 존재할 수도 있습니다.
        0 ≤ traps의 길이 ≤ 10
        1 ≤ traps의 원소 ≤ n
        시작 방과 도착 방은 함정이 아닙니다.
        항상 미로를 탈출할 수 있는 경우만 주어집니다.
    
        입출력 예
        n	start	end	    roads	                            traps	    result
        3	1	    3	    [[1, 2, 2], [3, 2, 3]]	            [2]	        5
        4	1	    4	    [[1, 2, 1], [3, 2, 1], [2, 4, 1]]	[2, 3]	    4
     */
    
    /*
        생각을 해보면 트랩이 발동하는 경우에는 기존 경로가 사라지고 새로운 경로가 발생하는거긴해유
        DP[i][j] -> i 에서 j 까지 가는데 발생하는 비용
        
     */
    public static void main(String[] args) {
        int n = 3;
        int start = 1;
        int end = 3;
        int[][] roads = {{1, 2, 2}, {3, 2, 3}};
        int[] traps = {2};
        
        int result = new Solution().solution(n, start, end, roads, traps);
        System.out.println("result = " + result);
    }
    
}


class Solution {
    public int solution(int n, int start, int end, int[][] roads, int[] traps) {
        int answer = 0;
        
        /*
         * 생각을 해보면 일단 트랩까지는 갈 수 있지 근데 트랩으로 간다면 경로가 변경되지
         * 그러면 갈 수 있는 경로를 갱신해야한다는건데
         * 해당 경로로 갔을 때 트랩이 발동했냐안했냐를 저장하고 있어야한다는거네
         * 트랩이 발동하면 해당 트랩과 연결된 경로가 반대로 지정됨 해당 트랩만!!
         * -> 해당 트랩으로 이동할 때 트랩이 발동했냐 안했냐를 보면 되겠네
         * 발동했으면 못가는거고 그러면 경로를 두개씩 넣어놔야겠네 트랩과 연결된 경우에는
         *
         * 서로 다른 두 방 사이에 직접 연결된 길이 여러 개 존재할 수도 있습니다.??????
         * 흠.. 말 그대로 두 방 사이에 직접 연결된 길이 여러 개 있 수 있으면 이거 ... 참..
         * 더 싼 비용으로 교체하면 되기는 하는데.. 맞나..?
         * 그니까 두 개의 노드 사이에 연결이 여러 개 일수도 있다라는건데
         * 여러개이여도 상관없음, 어차피 최소값으로 왔다갔다할테니.
         *
         * 방은 1 ~ n 까지 순차적으로 존재
         */
        
        // 트랩정보 set에 담기
        // 트랩이 있는 경우에 경로 정보를 둘다 잡아놓기 위해서~
        Map<Integer, Boolean> trapSet = new HashMap<>();
        for (int i = 0; i < traps.length; i++) {
            trapSet.put(traps[i] - 1, false);
        }
        System.out.println(trapSet);
        
        //dp[i][j]는 i -> j 로 이동하는데 발생하는 비용 정보
        int[][] dp = new int[n][n];
        //경로 비용이야 나중에 꺼내면 되니까
        Set[] routes = new HashSet[n];
        
        // 해당 두 노드간 경로가 여러개 일 수도 있다고 하니 최소값을 넣기 위해서는 일단 최대값으로 지정해놓을 필요가 있겠네
        // 트랩이 발동 중이면...
        // 트랩인 경우 반대로도 이동이 가능한거니 거기 경로도 넣어놔야겟구만.
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            routes[i] = new HashSet();
        }
        
        for (int i = 0; i < roads.length; i++) {
            int[] val = roads[i];
            
            int a = val[0] - 1;
            int b = val[1] - 1;
            int cost = val[2];
            
            if (dp[a][b] > cost) {
                dp[a][b] = cost;
                routes[a].add(b);
            }
        }
        
        // 근데 이렇게 된다면 우선순위 큐를 두개를 만들어야하나..? 트랩 발동 했을 때 , 안했을 때로..
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }
        for (int i = 0; i < routes.length; i++) {
            System.out.println(routes[i]);
        }
        
        dijkstra(start - 1, dp, routes, trapSet);
        
        return answer;
    }
    
    static void dijkstra(int start, int[][] dp, Set<Integer>[] routes, Map<Integer, Boolean> traps) {
        
        System.out.println("startNum = " + start);
        
        PriorityQueue<Node> nodes = new PriorityQueue<>();
        
        boolean trap = false;
        boolean[] visited = new boolean[dp[start].length];
        visited[start] = true;
        
        for (Integer dest : routes[start]) {
            nodes.add(new Node(dest, dp[start][dest]));
        }
        System.out.println("갈 수 있는 경로: " + routes[start]);
        System.out.println(nodes);
        
        
        while (!nodes.isEmpty()) {
            Node nextNode = nodes.poll();
            
            if (trap != nextNode.trap) continue;
            
            if (visited[nextNode.nodeNum]) continue;
            
            // 방문한 곳이면 패스인데.. 그전에 방문은 했는데 트랩이 발동한 상황이라면..?
            visited[nextNode.nodeNum] = true;
            
        }
        
        
        // 생각을 해봅시다
        // 함정이 발동하면 2->3 으로 갈 수 있던게 3->2로 갈 수 있지유..
        /*
            함정이 발동 중이 아니다!
            다익스트라의 기본 원리는 가장 비용이 싼 곳부터 찾아서 갈 수 있는 길을 갱신해 나가는 방식
            
            [풀이]
            1. 시작 점에서 갈 수 있는 경로 중에서 최소 비용을 찾음
            2. 해당 지점이 트랩이 발동 중인지 확인 발동 중이면 패스 아니라면 해당 최소 비용의 지점을 방문 상태로 변경
            3. 그 후 해당 지점이 트랩인지 아닌지 확인, 상태코드를 트랩 상태(해당 지점을)로 변경 후 갈 수 있는 경로를 뒤집기
            4. 해당 지점에서 갈 수 있는 경로 중에서 기존 경로와 비용 비교 후 더 작다면 치환
            5. 1~4을 반복
            
            저러면 해당 지점의 트랩이 발동 중인지 아닌지를 판단을 해야하고 가려는 지점의 트랩이 발동 중 인지도 확인해야함
            해당 지점의 트랩이 발동 중이면 해당 지점으로 갈 수 없는거고
            마찬가지로 가려는 지점의 트랩이 발동 중이면 갈 수 없는 길
            잠깐만.. 근데 a -> b 로 갔는데 b가 함정이다 원래 c->b로 갈 수 있는 길이었는데
            발동해서 b->c로 갈 수 있는 길이 되었다
            근데 c도 함정이다
            
            
            그러면 c로 도작하는 순간 b->c의 길은 다시 c->b 상태로 변경되는데 a->b 은 여전히 b->a상태란 말이지..
            저러면 특정 a에서 b로 이동 할 때 트랩 발동 여부를 저장하고 있어야한다는 건데 왜냐면 c->b로 올 수 있는 상태일 수 도 있다라는거니까..
            
            그리고 b에서 나가는 길은 얼마든지 치환이 가능한데.. 특정 지점에서 b로 오는 경우는? 어떻게 확인?
            
            dp[a][b] 라고 하면 b지점이 트랩이면 무엇이 되었든 a-z 까지라고 하면 어차피 도중 경로가 어떻게 되었든 트랩이 발동하면 해당 지점에서 그 지점으로 못가는 거니까..
            dp[a-z][b] b로 가는 길          -> 트랩 발동 상태로 변경
            dp[b][a-z] b에서 나가는 길       -> 트랩 발동 상태로 변경
           
            
            
            
         */
        
        
    }
}

class Node implements Comparable<Node> {
    int nodeNum;
    int cost = Integer.MAX_VALUE;
    boolean trap;
    
    public Node(int nodeNum, int cost) {
        this.nodeNum = nodeNum;
        this.cost    = cost;
    }
    
    public Node(int nodeNum, int cost, boolean trap) {
        this.nodeNum = nodeNum;
        this.cost    = cost;
        this.trap    = trap;
    }
    
    public int getNodeNum() {
        return nodeNum;
    }
    
    public void setNodeNum(int nodeNum) {
        this.nodeNum = nodeNum;
    }
    
    public int getCost() {
        return cost;
    }
    
    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public boolean isTrap() {
        return trap;
    }
    
    public void setTrap(boolean trap) {
        this.trap = trap;
    }
    
    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.cost, o.cost);
    }
    
    @Override
    public String toString() {
        return "Node{" +
                "nodeNum=" + nodeNum +
                ", cost=" + cost +
                ", trap=" + trap +
                '}';
    }
}