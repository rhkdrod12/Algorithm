package Other.Quiz05;

import java.util.*;

public class Quiz05 {
	/*
	몸짱 트레이너 라이언의 고민
	
	헬스장에서 일하는 몸짱 트레이너 라이언은 최근 손님들에게 불평을 많이 들었다.
	그것은 옷을 갈아입는데 다른 사람과 너무 가까워서 옷을 갈아입기가 불편하다는 것이었다.
	
	불만을 해결하기 위해 고민하던 라이언은 손님들의 예약시간을 참고해서 되도록이면 서로 멀리 떨어지도록 키를 나눠주기로 마음먹었다.
	예를 들어, 락커들이 3x3 정사각형 모양으로 배치되어있고,
	동시간대에 2명의 손님이 예약되어있다면 아래와 같이 락커를 할당하는 것을 고려해볼 수 있다.
	
	라이언이 일하는 헬스장은 아래와 같은 상황이라고 가정하자.
	
	락커는 정사각형으로 배치되어있고, 락커 사이에 옷을 갈아입을 공간이 있다. 단, 이 공간은 계산에서 제외된다.
	락커 간 거리는 상하좌우는 1로, 대각선은 2로 계산한다.
	손님들은 철저하게 예약시간에 맞춰 락커를 이용하고, 퇴실하는 시간까지 락커를 차지한다.
	영업시간은 오전 10시부터 오후 10시까지이다.
	헬스장은 예약제로 운영되므로 락커의 개수보다 더 많은 손님들이 동시간대에 몰리는 경우는 없다.
	이런 조건에서 헬스장을 이용한 손님들 중 가장 가까웠던 손님 간의 거리는 얼마일까?
	
	입력 형식
	입력은 정사각형 한 변의 길이 n과 손님수 m, 그리고 각 손님의 예약된 입실/퇴실 시간 timetable로 주어진다. 제한조건은 다음과 같다.

	0 < n <= 10
	0 <= m <= 1,000
	timetable은 m × 2 크기의 2차원 배열이다.
	각 행은 손님의 입실시각과 퇴실시각이 분 단위로 환산된 값 (t1, t2)가 들어있다.
	t1, t2에 대해서는 다음 조건이 성립한다. 600 <= t1 < t2 <= 1,320
	출력 형식
	할당된 락커들 간 거리 중 최소거리를 리턴한다. 손님 간에 이용 시간이 한 번도 겹치지 않을 경우에는 0을 리턴한다.
	
	예제 입출력
	n	m	timetable	answer
	3	2	[[1170,1210], [1200,1260]]	4
	2	1	[[840,900]]	0
	2	2	[[600,630],[630,700]]	2
	4	5	[[1140,1200],[1150,1200],[1100,1200],[1210,1300],[1220,1280]]	4
	
	예제에 대한 설명
	첫 번째 예제: 손님 2명이 20:00부터 20:10까지 이용시간이 겹치고, 이 상황은 본문에 소개된 예와 같다.
	두 번째 예제: 손님이 1명으로 겹치는 시간이 없기 때문에 0을 출력한다.
	세 번째 예제: 손님 2명이 10:30에 겹친다. 2x2 정사각형에 2명을 배치해야 하므로 정답은 2가 된다. (ex. (0,0), (1,1)에 배치)
	네 번째 예제: 손님 3명이 19:10부터 20:00까지 이용시간이 겹친다.
	이 경우 아래와 같이 배치해서 할당된 모든 락커 간 거리가 4가 되도록 할 수 있다. (할당된 락커는 #으로 표시)
	  # 0 0 0
	  0 0 0 #
	  0 0 0 0
	  0 # 0 0
	  
	  # 0 0 #
	  0 0 0 0
	  # 0 0 0
	  0 0 0 #
	  
	네 번째 예제에서 손님을 모서리부터 배치하는 건 좋은 전략이 아니다.
	2명까지는 거리가 6으로 가장 먼 거리가 되지만 예약된 다음 손님이 들어오면 할당된 락커 간 거리는 3이 돼버린다.
	이런 경우 라이언은 예약시간을 고려해서 거리가 4가 되도록 배치한다.
	*/
	public static void main(String[] args) {
		// 0 < n <= 10
		// 0 <= m <= 1,000
		// 600 <= t1 < t2 <= 1,320
		
		// int n = 4;
		// int m = 6;
		// int[][] timetable = {{600, 1320}, {600, 1320}, {800,1320}};
		int answer = 4;
		
		// int n = 3;
		// int m = 2;
		// int[][] timetable = {{600, 630}, {630, 700},{600, 630}, {630, 700},{630, 700},{630, 700}};
		
		int n = 10;
		int m = 1;
		int[][] timetable = {{840,900}, {900, 1100}, {1100, 1200}};
		
		System.out.println("answer: " + new Solution().solution(n, timetable.length, timetable));
		System.out.println("정답: " + answer);
	
	}
	
	static class Solution {
		public int solution(int n, int m, int[][] timetable) {
			
			// 상하좌우의 경우 1의 길이를 가짐
			// 어차피 대각선도 상하좌우 2번 움직인거나 마찬가지
			// 손님간 거리를 최대한 멀리 배정
			// 일단 최대 겹치는 사람수를 구해야함
			
			// 해당 거리는 (X1, Y1) , (X2,Y2) 일 때 X2-X1 + Y2-Y1 -> (X2+Y2) - (X1+Y1)
			// n = 4 일 때 4 X 4 형태이니까
			
			// 배치되어야하는 겹칠 수 있는 시간대의 최대 사람수
			// 어차피 나머지는 최대 사람이 있는 경우에만 가장 가까워질 수 있기 때문에 필요없음
			
			if(m == 0) return 0;
			
			Arrays.sort(timetable, (o1, o2) -> o1[1] - o2[1]);
			
			int maxPeople = 0;
			for (int i = 0; i < m; i++) {
				int cnt = 1;
				for (int target = i+1; target < m; target++) {
					if (timetable[i][1] >= timetable[target][0] &&
					    timetable[i][1] <= timetable[target][1]) {
						cnt++;
					}
				}
				maxPeople = Math.max(maxPeople, cnt);
			}

			if(maxPeople <= 1) return 0;
			
			// 모든 사람이 length를 만족시켜야만 참인거고
			// 그렇다면 어차피 무엇이 되었든 해당 최소거리로 모두 배치하는게 가능하고
			// 그 4, 5, 6 이렇게 배치할 수 도있찌만
			// 결국에는 해당 거리중 최소거리를 구하는 것임
			// 따라서 4, 4, 4도 무조건 성립하지
			// 배치를 구하는게 아니기 때문에 모든 경우를 전부 뒤질 필요는 없다라는거지유..
			// 즉 해당 거리로 해당 인원이 배치가 가능하냐만 따지면 됨..
			
			
			// 최대 가능한 거리는 양쪽 끝에 2명이 배치된 경우
			for (int length = 2 * n - 2; length > 0; length--) {
				for (int y = 0; y < n; y++) {
					for (int x = 0; x < n; x++) {
					
						Set<Node> set = new LinkedHashSet<>();
						set.add(new Node(x, y));
						
						// int tempX = x + 1 < n ? x + 1 : 0;
						// int tempY = x + 1 < n ? y : y + 1;
						
						for (int y1 = y; y1 < n; y1++) {
							for (int x1 = 0; x1 < n; x1++) {
								if(y1 < y || (y1 == y && x1 <= x)) continue;
								boolean flag = true;
								for (Node node : set) {
									int dis = Math.abs(node.x - x1) + Math.abs(node.y - y1);
									if (length > dis) {
										flag = false;
										break;
									}
								}
								if (flag) {
									set.add(new Node(x1, y1));
								}
								
								if (set.size() == maxPeople) {
									
									String str = "";
									for (Node node : set) {
										str += node.x + ":" + node.y +",";
									}
									
									for (int i = 0; i < n; i++) {
										for (int j = 0; j < n; j++) {
											if (str.contains(j + ":" + i)) {
												System.out.print("# ");
											}else{
												System.out.print("0 ");
											}
										}
										System.out.println();
									}
									
									System.out.println(set);
									return length;
								}
							}
						}
					}
				}
			}
			
			return 0;
		}
		
		class Node{
			int x;
			int y;
			
			public Node(int x, int y) {
				this.x = x;
				this.y = y;
			}
			
			@Override
			public String toString() {
				return "Node{" +
						       "x=" + x +
						       ", y=" + y +
						       '}';
			}
		}
	}
	
}
