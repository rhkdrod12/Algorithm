package Other.Quiz02;

import java.util.*;

public class Quiz02 {
	
	public static void main(String[] args) {
		
		
		// int[][] land = {{1, 4, 8, 10, 10}, {5, 5, 5, 5, 10}, {10, 10, 10, 10, 1}, {10, 10, 10, 20, 1}, {1, 1, 1, 1, 1}};
		// int[][] land = {{1, 4, 8, 10}, {5, 5, 5, 5}, {10, 10, 10, 10}, {10, 10, 10, 20}};
		int[][] land = {{10, 11, 10, 11,5}, {2, 21, 20, 10,3}, {1, 20, 21, 11,2}, {2, 1, 2, 1,9}, {5,7,2,1,3}};
		int height = 1;
		int result = 15;
		
		System.out.println(new Solution().solution(land, height));
	}
	
	/*
	문제 설명
	N x N 크기인 정사각 격자 형태의 지형이 있습니다.
	각 격자 칸은 1 x 1 크기이며, 숫자가 하나씩 적혀있습니다.
	격자 칸에 적힌 숫자는 그 칸의 높이를 나타냅니다.
	이 지형의 아무 칸에서나 출발해 모든 칸을 방문하는 탐험을 떠나려 합니다.
	칸을 이동할 때는 상, 하, 좌, 우로 한 칸씩 이동할 수 있는데, 현재 칸과 이동하려는 칸의 높이 차가 height 이하여야 합니다.
	높이 차가 height 보다 많이 나는 경우에는 사다리를 설치해서 이동할 수 있습니다.
	이때, 사다리를 설치하는데 두 격자 칸의 높이차만큼 비용이 듭니다.
	따라서, 최대한 적은 비용이 들도록 사다리를 설치해서 모든 칸으로 이동 가능하도록 해야 합니다.
	설치할 수 있는 사다리 개수에 제한은 없으며, 설치한 사다리는 철거하지 않습니다.
	각 격자칸의 높이가 담긴 2차원 배열 land와 이동 가능한 최대 높이차 height가 매개변수로 주어질 때,
	모든 칸을 방문하기 위해 필요한 사다리 설치 비용의 최솟값을 return 하도록 solution 함수를 완성해주세요.
	
	[제한사항]
	
	land는 N x N크기인 2차원 배열입니다.
	land의 최소 크기는 4 x 4, 최대 크기는 300 x 300입니다.
	land의 원소는 각 격자 칸의 높이를 나타냅니다.
	격자 칸의 높이는 1 이상 10,000 이하인 자연수입니다.
	height는 1 이상 10,000 이하인 자연수입니다.
	
	land[a][b] => (a,b)의 높이를 말함
	(a,b)에서 상화좌우로 이동이 가능하지만 이동하려는 좌표의 높이와 현재의 높이차가 주어진 height보다 이하인 경우에만 사다리없이 가능
	따라서 최대한 적은 비용이 들도록 사다리를 설치해서 모든 칸으로 이동이 가능하도록 해야함
	
	이분연결인가..?
	
	즉 모든 칸은 연결하려면 한칸도 빠짐없이 연결을 가지고 있으면 되는거고
	그리고 사다리 설치피용은 높이차만큼 발생하니 최대한 적게 높이차가 나는 곳으로 연결을 갱신해나가면 되지 않겠남..?
	
	어차피 이동 가능한건 상하좌우이긴 하지만 못가는 경우에는 사다리를 설치해서 갈 수가 있지
	
	set같은 곳에다가 방문한 곳 넣어서 모든 곳을 방문햇는지 size가지고 확인하면 될거 같고..
	
	그리고 이동시에는
	
	일단 정사각형 -> 정사각형에서 모든 칸은 다 채우면서 이동하려면 딱히 도착점은 없네..?
	그리고 재방문을 못하는 것도 아니네..??
	그러이면 이야기가 달라지지?
	
	최소한의 횟수로 최소한의 비용으로 모든 곳을 방문하는 것이 아니라
	최소 사다리 비용를 구하는 거니..
	
	일단 사다리 없이 이동이 가능하면 하나의 묶음으로 볼 수 있겠네..?
	
	그리고 이 묶음들이 몇개가 있으며 이 묶음들에서 가장 싸게 이동 가능한 지점이 있을 거고
	그리고 이 게 최소의 비용으로 가능한 방식이겟네..
	
	블럭 묶는건 어렵지 않을 것으로 보이는데
	그럼 블럭간 최소 비용으로 이동 가능한 지점 찾는건~?
	
	블록으로 묶는다면 어떻게 묶지요?? list?
	
	해당 지점으로 가는 사다리가 여러개 일 수 있나..? 이건 있을 수 있는데
	해당 지점에서 출발하는 사다리는 여러개 일 수가 없음
	
	
	
	 */
	static class Solution {
		
		int n;
		int height;
		
		public int solution(int[][] land, int height) {
			int answer = 0;
			
			this.height = height;
			// 상하좌우로 이동이 가능핟고 했으니 변할 수 있는 값을 배열로 만듬
			n = land.length;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					System.out.printf("  %3d", land[i][j]);
				}
				System.out.println();
			}
			System.out.println("-------------------------------------");
			
			// 그룹화
			int group = 0;
			boolean[][] visited = new boolean[land.length][land.length];
			for (int i = 0; i < land.length; i++) {
				for (int j = 0; j < land[i].length; j++) {
					if(!visited[i][j]){
						setGroup(i, j, group++, land, visited);
					}
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					System.out.printf("  %3d", groupMap.get(new vector(i, j)));
				}
				System.out.println();
			}
			System.out.println("-------------------------------------");
			
			
			// 그룹간 사다리 최소비용 찾기
			visited = new boolean[land.length][land.length];
			Map<vector, Node> costMap = new HashMap<>();
			for (int i = 0; i < land.length; i++) {
				for (int j = 0; j < land[i].length; j++) {
					if(visited[i][j]) continue;
					visited[i][j] = true;
					int curGroup = groupMap.get(new vector(i, j));
					for (int k = 0; k < 4; k++) {
						int x = i + dx[k];
						int y = j + dy[k];
						
						if (x >= 0 && x < n && y >= 0 && y < n) {
							if(visited[x][y]) continue;
							
							int nextGroup = groupMap.get(new vector(x, y));
							if(curGroup != nextGroup){
								int diff = Math.abs(land[i][j] - land[x][y]);
								vector key = new vector(curGroup, nextGroup);
								if (!costMap.containsKey(key) || costMap.get(key).cost > diff) {
									costMap.put(key, new Node(key, diff));
								}
							}
						}
					}
				}
			}
			
			
			PriorityQueue<Node> set = new PriorityQueue<>();
			for (vector key : costMap.keySet()) {
				set.add(costMap.get(key));
			}
			
			// 지금 이 구조면
			// 6 -> 7
			// boolean[] groupVisited = new boolean[group];
			// for (Node node : set) {
			// 	int a = node.vector.x;
			// 	int b = node.vector.y;
			//
			// 	if(groupVisited[b] || groupVisited[a]) continue;
			// 	groupVisited[b] = true;
			// 	System.out.println(node);
			// 	answer += node.cost;
			// }
			
			
			Kruskal.setParent(group);
			answer = Kruskal.cal(set);
			return answer;
			
		}
		
		static class Kruskal {
			
			static int[] parent;
			
			static public  void setParent(int group) {
				parent = new int[group];
				for (int i = 0; i < parent.length; i++) {
					parent[i] = i;
				}
			}
			
			static public int cal(PriorityQueue<Node> set) {
				int answer = 0;
				
				while (!set.isEmpty()){
					Node node = set.poll();
					int x = node.vector.x;
					int y = node.vector.y;
					
					if (union(x, y)) {
						System.out.println(node);
						answer += node.cost;
					}
				}
				
				return answer;
			}
			
			static public int findParent(int i){
				if (parent[i] != i) {
					return findParent(parent[i]);
				}else{
					return i;
				}
			}
			
			static public boolean union(int x, int y) {
				
				int xRoot = findParent(x);
				int yRoot = findParent(y);
				
				if (xRoot != yRoot) {
					parent[yRoot] = xRoot;
					return true;
				}else{
					return false;
				}
			}
		}
		
		
		static class Node implements Comparable<Node> {
			
			vector vector;
			int cost;
			
			public Node(vector vector, int cost) {
				
				if(vector.x > vector.y){
					int temp = vector.x;
					vector.x = vector.y;
					vector.y = temp;
				}
				
				this.vector = vector;
				this.cost   = cost;
			}
			
			@Override
			public int compareTo(Node o) {
				return this.cost - o.cost;
			}
			
			@Override
			public String toString() {
				return "Node{" +
						       "vector=" + vector +
						       ", cost=" + cost +
						       '}';
			}
		}
		
		
		
		static class vector {
			int x;
			int y;
			
			public vector(int x, int y) {
				this.x = x;
				this.y = y;
			}
			
			
			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				vector vector = (vector) o;
				return x == vector.x && y == vector.y;
			}

			@Override
			public int hashCode() {
				return Objects.hash(x, y);
			}
			
			// @Override
			// public int compareTo(vector o) {
			// 	return this.num - o.num;
			// }
			
			@Override
			public String toString() {
				return "vector{" +
						       "x=" + x +
						       ", y=" + y +
						       '}';
			}
		}
		
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, 1, -1};
		Map<vector, Integer> groupMap = new HashMap<>();
		
		// 그룹화
		public void setGroup(int i, int j, int group, int[][] land, boolean[][] visited){
			
			vector vector = new vector(i, j);
			Queue<Solution.vector> queue = new LinkedList<>();
			
			queue.add(vector);
			visited[i][j] = true;
			groupMap.put(new vector(i, j), group);
			
			while (!queue.isEmpty()) {
				Solution.vector vec = queue.poll();
				
				int a = vec.x;
				int b = vec.y;
				for (int k = 0; k < 4; k++) {
					int x = a + dx[k];
					int y = b + dy[k];
					
					if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y] && Math.abs(land[a][b] - land[x][y]) <= height) {
						visited[x][y] = true;
						Solution.vector nextVector = new vector(x, y);
						groupMap.put(new vector(x, y), group);
						queue.add(nextVector);
					}
				}
			}
		}
		
	}
	
}
