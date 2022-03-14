package Other.Quiz03;

import java.util.PriorityQueue;

public class Practice {
	
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
	
	 */
	
	public static void main(String[] args) {
		
		int[][] land = {{1, 4, 8, 10, 10}, {5, 5, 5, 5, 10}, {10, 10, 10, 10, 1}, {10, 10, 10, 20, 1}, {1, 1, 1, 1, 100}};
		// int[][] land = {{1, 4, 8, 10}, {5, 5, 5, 5}, {10, 10, 10, 10}, {10, 10, 10, 20}};
		// int[][] land = {{10, 11, 10, 11,5}, {2, 21, 20, 10,3}, {1, 20, 21, 11,2}, {2, 1, 2, 1,9}, {5,7,2,1,3}};
		int height = 9;
		int result = 15;
		
		System.out.println(new Solution().solution(land, height));
	}
	
	static class Solution{
		
		public int solution(int[][] land, int height) {
			int answer = 0;
			
			// 지형크기
			int n = land.length;
			
			// 좌우로 이동 가능한 경로
			int[] dx = {0, 0, -1, 1};
			int[] dy = {-1, 1, 0, 0};
			
			// 방문 가능한 좌표는 다시 방문할 필요가 없으니
			boolean[][] visited = new boolean[n][n];
			
			
			PriorityQueue<Node> queue = new PriorityQueue<>();
			PriorityQueue<Node> groupQueue = new PriorityQueue<>();
			
			System.out.println("-----------------------------------------------------");
			for (int i = 0; i < land.length; i++) {
				for (int i1 = 0; i1 < land[i].length; i1++) {
					System.out.printf(" %8s", land[i][i1]);
				}
				System.out.println();
			}
			System.out.println("-----------------------------------------------------");
			
			
			groupQueue.add(new Node(0, 0, 0));
			
			while (!groupQueue.isEmpty()) {
				
				Node nextNode = groupQueue.poll();
				if(visited[nextNode.x][nextNode.y]) continue;
				
				answer += nextNode.cost;
				queue.add(nextNode);
				
				while (!queue.isEmpty()) {
					
					Node curNode = queue.poll();
					
					int x = curNode.x;
					int y = curNode.y;
					
					if(visited[x][y]) continue;
					visited[x][y] = true;
					
					for (int i = 0; i < 4; i++) {
						int mx = x + dx[i];
						int my = y + dy[i];
						
						if (mx >= 0 && mx < n && my > 0 && my < n) {
							
							int diff = Math.abs(land[x][y] - land[mx][my]);
							
							if (diff <= height) {
								queue.add(new Node(mx, my, 0));
							}else{
								groupQueue.add(new Node(mx, my, diff));
							}
						}
					}
					
					System.out.println("-----------------------------------------------------");
					for (int i = 0; i < visited.length; i++) {
						for (int i1 = 0; i1 < visited[i].length; i1++) {
							System.out.printf(" %8s", visited[i][i1]);
						}
						System.out.println();
					}
					System.out.println("-----------------------------------------------------");
					
				}
			}
			
			
			/**
			 * s
			 */
			
			
			
			
			return answer;
		}
		
		class Node implements Comparable<Node> {
			
			int x;
			int y;
			int cost;
			
			public Node(int x, int y, int cost) {
				this.x    = x;
				this.y    = y;
				this.cost = cost;
			}
			
			@Override
			public int compareTo(Node o) {
				return this.cost - o.cost;
			}
		}
	}
	
}
