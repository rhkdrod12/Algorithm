package Other.Quiz02;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Test3 {
	public static void main(String[] args) {
		int[][] land = {{1, 4, 8, 10}, {5, 5, 5, 5}, {10, 10, 10, 10}, {10, 10, 10, 20}};
		// int[][] land = {{10, 11, 10, 11,5}, {2, 21, 20, 10,3}, {1, 20, 21, 11,2}, {2, 1, 2, 1,9}, {5,7,2,1,3}};
		int height = 3;
		int result = 15;
		
		System.out.println(new Solution().solution(land, height));
	}
	
	static class Solution {
		
		static class Land implements Comparable<Land> {
			
			int row;
			int col;
			int cost;
			
			public Land(int row, int col, int cost) {
				this.row = row;
				this.col = col;
				this.cost = cost;
			}
			@Override
			public int compareTo(Land l) {
				return this.cost - l.cost;
			}
			
			@Override
			public String toString() {
				return "Land{" +
						       "row=" + row +
						       ", col=" + col +
						       ", cost=" + cost +
						       '}';
			}
		}
		
		public int solution(int[][] land, int height) {
			int answer = 0;
			int N = land.length;
			boolean[][] visited = new boolean[N][N];
			Queue<Land> landNoLadder = new LinkedList<Land>();
			PriorityQueue<Land> landNeedLadder = new PriorityQueue<Land>();
			landNoLadder.offer(new Land(0, 0, 0));
			
			
			// for (int i = 0; i < N; i++) {
			// 	for (int j = 0; j < N; j++) {
			// 		System.out.printf("  %3d(%2d,%2d)", land[i][j], i , j);
			// 	}
			// 	System.out.println();
			// }
			// System.out.println("-------------------------------------");
			
			int[] dRow = {-1, 1, 0, 0};
			int[] dCol = {0, 0, 1, -1};
			
			while (!landNoLadder.isEmpty()){
				Land curr = landNoLadder.poll();
				if (!visited[curr.row][curr.col]) {
					visited[curr.row][curr.col] = true;
					
					// 같은 곳끼리는 비용을 0으로 지정하기 때문에
					// 근데 최소값이 되는 경우는??
					// 아..priorityQueue
					// 그룹이 달라지면 landNeedLadder에 들어갈텐데 우선순위 큐라 cost가 가장 적은 것으로 정렬되어 있을 거고
					// 그리고 저기서 꺼내왔다라는 건 그룹이 달라졌다을 수도 있지만 기존에 그룹내에서도 height가 차이 나는 것들이 있을 수 있으니
					// 들어왔을 수 있는데 visited에 의해 차단될거고
					// 그러면 visited를 통과하는건 결국에는 그룹이 다른 경우에만 나오는 거지유
					// 그리고 landNoLadder에는 당연 아무런 값이 없을거고
					// 새로운 그룹에 대해 만들어 나갈테니..
					// 해당 그룹에서 맞나는 모든 경로에 대해 비교가 들어갔으니..
					
					answer += curr.cost;
					
					for (int i = 0; i < 4; i++) {
						int tRow = curr.row + dRow[i];
						int tCol = curr.col + dCol[i];
						
						if (tRow >= 0 && tRow < N && tCol >= 0 && tCol < N && !visited[tRow][tCol]){
							if (Math.abs(land[curr.row][curr.col] - land[tRow][tCol]) <= height) {
								landNoLadder.offer(new Land(tRow, tCol, 0));
							}else{
								// 다른 것으로 인해 해당 사다리 없이 처리되는 곳이라고 해도
								// 이쪽은 그룹을 처리하고 나서 poll로 땅기기 떄문에 문제 없이 처리될 것임..
								landNeedLadder.offer(new Land(tRow, tCol, Math.abs(land[curr.row][curr.col] - land[tRow][tCol])));
							}
						}
					}
				}
				// 사다리 없이 이동 가능한게 없으면
				if (landNoLadder.isEmpty()) {
					if (!landNeedLadder.isEmpty()) landNoLadder.offer(landNeedLadder.poll());
				}
			}
			return answer;
		}
	}
}
