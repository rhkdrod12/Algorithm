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
		
		// int n = 3;
		// int start = 1;
		// int end = 3;
		// int[][] roads = {{0, 1, 2}, {2, 1, 3}};
		// int[] traps = {1, 2};
		
		int n = 4;
		int start = 1;
		int end = 4;
		int[][] roads = {{1, 2, 1}, {3, 2, 1}, {2, 4, 1}};
		int[] traps = {2, 3};
		
		int result = new Solution().solution(n, start, end, roads, traps);
		System.out.println("result = " + result);
	}
	
}


class Solution {
	public int solution(int n, int start, int end, int[][] roads, int[] traps) {
		int answer = 0;
        
        /*
            비트마스크
            bit를 이용해 true, false를 판단하는 방식, 해당 비트가 1이면 함정이 발동 상태, 0이면 미발동 상태로 판정
            
            함정의 수는 최대 10개 -> 함정으로 인해 만들 수 있는 경우의 수는 최대 2^10 = 1024가지
            함정의 수는 최대 10개이기 때문에 필요 bit수는 10개
            
            어떠한 경로를 이용하든 발생 가능한 함정의 경우의 수는 2^n 이 될꺼고
            기존의 경로에서 1024배 증가한 수가 되려나..
            
            
            그렇다면~~ 해당 경로로 이동하는데 가장 적게 드는 비용으로 생각하고 돌리면 되려나..?
            여기서는 방문을 했는지 중요하지 않음 -> 다시 돌아가야하는 경우도 있기 때문에,
            
            dp[n][j] = 시작점에서 n지점으로 이동했을 때 j의 경우만큼 함정이 발생했을 때의 최소 비용
            
            갈수있는 경로 함정이 발동했을 때의 경로를 모두 넣어두고 해당 지점에서 발생할 수 있는 모든 경우의 수에 대해 dp에 저장해놓으면 되겠지유
            
            최소 비용의 지점부터 탐색이 들어간다고 한다면
            
            [발생 가능한 경우의 수]
            현재 노드           다음 노드        방향
            발동                발동             정방향
            발동                미발동           역방향
            미발동              발동             역방향
            미발동              미발동           정방향
            
            1. 현재 노드에서 갈 수 있는 최소비용의 지점을 찾음
            2. 현재 노드와 다음 노드의 함정 상태에 따른 비용 갱신
            
         */
		
		Map<Integer, Integer> trapMap = new HashMap<>();
		for (int i = 0; i < traps.length; i++) {
			trapMap.put(traps[i]-1, 1 << i);
		}
		
		ArrayList<Node>[] dist = new ArrayList[n];
		for (int i = 0; i < dist.length; i++) {
			dist[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < roads.length; i++) {
			int s = roads[i][0]-1;
			int e = roads[i][1]-1;
			int cost = roads[i][2];
			
			dist[s].add(new Node(e, cost));
			if (trapMap.containsKey(e) || trapMap.containsKey(s)) {
				dist[e].add(new Node(s, cost, true));
			}
		}
		
		answer = dijkstra(start - 1, dist, trapMap, end-1);
		return answer;
	}
	
	static int dijkstra(int start, ArrayList<Node>[] dist, Map<Integer, Integer> trapMap, int target) {
		
		int[][] dp = new int[dist.length][1 << trapMap.size()];
		for (int i = 0; i < dp.length; i++) {
			Arrays.fill(dp[i] , Integer.MAX_VALUE);
		}
		
		boolean[][] visited = new boolean[dist.length][1 << trapMap.size()];
		
		PriorityQueue<Node> queue = new PriorityQueue<>();
		queue.add(new Node(start, 0));
		
		while (!queue.isEmpty()) {
			Node curNode = queue.poll();
			
			int curNum = curNode.nodeNum;
			int curState = curNode.state;
			int curCost = curNode.cost;
			boolean curTrapOn = false;
			
			// 현재 노드가 트랩이라면 state를 변경, 트랩이 발동 상태라면 curTrapOn를 true로 변경
			if (trapMap.containsKey(curNum)){
				curState ^= trapMap.get(curNum);
				if((curState & trapMap.get(curNum)) == trapMap.get(curNum)) curTrapOn = true;
			}
			
			// 현재 노드번호에 현재 state로 방문한 적이 있으면 통과
			if (visited[curNum][curState]) continue;
			visited[curNum][curState] = true;
			
			// 현재 노드에서 갈 수 있는 경로 확인
			// 여기서는 갔던 노드를 재방문이 가능하단 말이지 state만 다르다면
			ArrayList<Node> nextNodeList = dist[curNum];
			for (int i = 0; i < nextNodeList.size(); i++) {
				Node nextNode = nextNodeList.get(i);
				int nextNum = nextNode.nodeNum;
				int nextCost = nextNode.cost;
				boolean nextReverse = nextNode.reverse; //역방향 여부
				
				if(nextNum == target) break;
				
				boolean nextTrapOn = false;
				// curState에 따라 현재 nextNode가 트랩이 발동했는지 안했는지 확인이 가능
				if (trapMap.containsKey(nextNum)) nextTrapOn = ((curState & trapMap.get(nextNum)) == trapMap.get(nextNum));
				
				/**
				 *  현재 트랩 미발동       다음 트랩 미발동   정방향
				 *  현재 트랩 발동         다음 트랩 미발동   역방향
				 *  현재 트랩 미발동       다음 트랩 발동     역방향
				 *  현재 트랩 발동         다음 트랩 발동     정방향
				 */
				//정방향 nextReverse가 false인경우에만 가능
				//역방향인 경우에는 nextReverse가 true인 경우에만 가능
				if(((curTrapOn == nextTrapOn) && !nextReverse) || ((curTrapOn != nextTrapOn) && nextReverse)){
					if(dp[nextNum][curState] > curCost + nextCost){
						dp[nextNum][curState] = curCost + nextCost;
						queue.add(new Node(nextNum, curCost + nextCost, curState));
					}
				}
			}
		}
		
		int result = Integer.MAX_VALUE;
		int[] targetDist = dp[target];
		for (int i = 0; i < targetDist.length; i++) {
			if(result > targetDist[i]) result = targetDist[i];
		}
		return result;
	}
}

class Node implements Comparable<Node> {
	int nodeNum;
	int cost = Integer.MAX_VALUE;
	int state = 0;
	boolean reverse;
	
	public Node(int nodeNum, int cost) {
		this.nodeNum = nodeNum;
		this.cost    = cost;
	}
	
	public Node(int nodeNum, int cost, int state) {
		this.nodeNum = nodeNum;
		this.cost    = cost;
		this.state   = state;
	}
	
	public Node(int nodeNum, int cost, boolean reverse) {
		this.nodeNum = nodeNum;
		this.cost    = cost;
		this.reverse = reverse;
	}
	
	public Node(int nodeNum, int cost, int state, boolean reverse) {
		this.nodeNum = nodeNum;
		this.cost    = cost;
		this.state   = state;
		this.reverse = reverse;
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
				", state=" + state +
				", reverse=" + reverse +
				'}';
	}
}