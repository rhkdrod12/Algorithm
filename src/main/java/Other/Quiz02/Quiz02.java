package Other.Quiz02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz02 {
	
	public static void main(String[] args) {
		
		int[][] land = {{1, 4, 8, 10}, {5, 5, 5, 5}, {10, 10, 10, 10}, {10, 10, 10, 20}};
		int height = 3;
		int result = 15;
		
		new Solution().solution(land, height);
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
		int[][] land;
		List<Integer> list = new ArrayList<>();
		int idx = 0;
		public int solution(int[][] land, int height) {
			int answer = 0;
			
			
			
			this.n      = land.length;
			this.height = height;
			this.land   = land;
			
			int idx = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// if(idx%n > 0){
					// 	System.out.print(String.format("  %3d", idx));
					// }else{
					// 	System.out.print(String.format("  %03d", idx));
					// }
					System.out.printf("  %3d", idx++);
					list.add(land[i][j]);
				}
				System.out.println();
			}
			System.out.println("-------------------------------------");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					// if(idx%n > 0){
					// 	System.out.print(String.format("  %3d", idx));
					// }else{
					// 	System.out.print(String.format("  %03d", idx));
					// }
					System.out.printf("  %3d", land[i][j]);
				}
				System.out.println();
			}
			System.out.println("-------------------------------------");
			
			
			// 위쪽이 가능하다면 위쪽 map에서 해당 idx을 찾아서 해당 비용과 비교??
			// 그리고 그 중에서 최소값과 갈 수 있는 경로를 저장하고 있으면 되지 않을까요??
			// 무조건 height이상으로 큰 경우에만 사다리가 세워지는데
			// 그렇게 되려면... 음음음음
			// 일단 블록 별로 구분해봅시다
			// 어차피 블록내 같은 칸끼리는 같은 거나 마찬가지이니
			// 블록으로 만들어 새로운 index로 만들어서 이를 다익스트라로 구하면 될거같은데.
			//
			// for (int i = 0; i < list.size(); i++) {
			// 	int cost = list.get(i);
			// 	Node node = new Node(i, cost);
			//
			// 	System.out.printf("i: %2d", i);
			// 	//위쪽
			// 	if(i/n > 0){
			// 		System.out.printf(" U: %2d", i-n);
			// 	}
			// 	//왼쪽
			// 	if(i%n > 0){
			// 		System.out.printf(" L: %2d", i-1);
			// 	}
			// 	//오른쪽
			// 	if(i%n < n-1){
			// 		System.out.printf(" R: %2d", i+1);
			// 	}
			// 	//아래쪽
			// 	if(i/n < n-1){
			// 		System.out.printf(" D: %2d", i+n);
			// 	}
			// 	System.out.println();
			// }
			visited = new boolean[n * n];
			for (int i = 0; i < list.size(); i++) {
				dfs(i, list.get(i));
				blockIdx++;
			}
			System.out.println("\nmap = " + map);
			
			// 위쪽   idx/n <= 0   일 떄 불가
			// 왼쪽   idx%n <= 0   일 때 불가
			// 오른쪽 idx%n >= n-1 일 때 불가
			// 아래쪽 idx/n >= n-1 일 때 불가
			
			return answer;
		}
		
		int blockIdx = 0;
		Map<Integer, Node> map = new HashMap<Integer, Node>();
		boolean[] visited;
		public void dfs(int idx, int preCost) {
			
			// if (visited[idx]) return;
			// visited[idx] = true;
			
			if(Math.abs(preCost - list.get(idx)) > height){
				return;
			}else{
				map.put(idx, new Node(idx, list.get(idx), blockIdx));
			}
			
			if(idx/n > 0){
				dfs(idx-n, list.get(idx));
			}
			if(idx%n > 0){
				dfs(idx-1, list.get(idx));
			}
			if(idx%n < n-1){
				dfs(idx+1, list.get(idx));
			}
			if(idx/n < n-1){
				dfs(idx+n, list.get(idx));
			}
		}
		
		
		class Node {
			int nodeNum;
			int cost;
			int blockNum;
			
			public Node(int nodeNum, int cost) {
				this.nodeNum = nodeNum;
				this.cost    = cost;
			}
			
			public Node(int nodeNum, int cost, int blockNum) {
				this.nodeNum  = nodeNum;
				this.blockNum = blockNum;
				this.cost     = cost;
			}
			
			@Override
			public String toString() {
				return "Node{" +
						"nodeNum=" + nodeNum +
						", cost=" + cost +
						", blockNum=" + blockNum +
						'}';
			}
		}
	}
	
}
