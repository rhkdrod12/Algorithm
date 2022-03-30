package Other.Quiz06;

import java.util.Arrays;
import java.util.LinkedList;

public class Quiz06 {
	
	/*
		문제 설명
		당신에게 자연수로 이루어진 길이가 n인 배열 b가 주어집니다.
		초기에는 모든 수들이 "안티 세포" 안에 들어있습니다.
		일반적인 세포는 분열을 하지만, 이 안티 세포는 반대로 여러 안티 세포가 모여 합성을 합니다.
		당신은 다음과 같은 과정을 통해 인접한 두 안티 세포를 합치거나 또는 그대로 두려고 합니다.
		
		i=0로 설정하고, 빈 배열 c를 하나 만듭니다.
		i가 n이라면 과정을 종료합니다.
		b[i]를 포함하는 안티 세포를 X, 그리고 X 바로 왼쪽에 있는 안티 세포를 Y라고 정의합니다.
		만약 Y가 존재하고 X의 모든 숫자의 합과 Y의 모든 숫자의 합이 같다면,
		당신은 이 두 안티 세포를 합치거나 합치지 않는 행동 중에서 하나를 선택할 수 있습니다.
		만약 X와 Y를 합친다면, 둘을 합치고, c의 맨 뒤에 i를 추가한 뒤 다시 3번 과정으로 돌아갑니다.
		만약 X와 Y를 합치지 않는다면(또는 Y가 존재하지 않는다면), i를 1 증가시키고 2번 과정으로 돌아갑니다.
		예를 들어, 다음은 b = [1,1,1,1]일 때 위와 같은 과정을 거치는 것을 나타낸 것입니다.
		
		i	안티 세포	c	비고
		0	(1)(1)(1)(1)	[]	초기 상태입니다.
		1	(1)(1)(1)(1)	[]	i=0 일 때는 Y가 존재하지 않으므로 i를 1 증가시켰습니다.
		1	(1,1)(1)(1)	[1]	b[1]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합쳤습니다. 따라서 c에 i=1이 추가됩니다.
		2	(1,1)(1)(1)	[1]	b[1]을 포함하는 안티 세포(X) 왼쪽의 안티 세포 Y가 존재하지 않으므로 i를 1 증가시켰습니다.
		3	(1,1)(1)(1)	[1]	X의 모든 수의 합은 1이고, Y의 모든 수의 합은 2이므로, 둘은 합칠 수 없습니다. 따라서 i을 1 증가시켰습니다.
		3	(1,1)(1,1)	[1,3]	b[3]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합쳤습니다. 따라서 c에 i=3이 추가됩니다.
		4	(1,1)(1,1)	[1,3]	b[3]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합칠 수 있었지만 그러지 않았습니다. 따라서 i를 1 증가시켰습니다.
		이 경우 c = [1,3]이 됩니다. 물론 이는 c를 만들 수 있는 하나의 경우일 뿐이며, 당신의 선택에 따라 [], [1], [3], [1,3], [2], [1,3,3]으로 c배열을 다양하게 만들 수 있습니다. 당신이 어떤 선택을 하더라도 유한한 횟수 안에 c 배열을 만들 수 있음은 증명될 수 있습니다.
		
		당신은 b가 주어졌을 때 만들 수 있는 서로 다른 배열 c의 개수가 몇 개인지 알고 싶습니다.
		
		정수로 이루어진 배열 a와 s가 매개변수로 주어집니다. a는 여러 개의 b 배열을 순서대로 이어 붙인 배열이며, s는 각 b 배열의 길이가 순서대로 담긴 배열입니다.
		각 b 배열에 대해 문제의 답을 109 + 7로 나눈 나머지를 구하여 배열에 차례대로 담아 return 하도록 solution 함수를 완성해주세요.
		
		예를 들어, a = [1,2,3,4,5,6,7,8,9], s = [2,3,4] 라면, 다음 3가지 b 배열에 대해서 답을 구해야 합니다.
		
		b = [1,2] (s[0] = 2 이므로, a의 첫 2개 원소가 b 배열을 이룹니다.)
		b = [3,4,5] (s[1] = 3 이므로, a의 그다음 3개 원소가 b 배열을 이룹니다.)
		b = [6,7,8,9] (s[2] = 4 이므로, a의 그다음 4개 원소가 b 배열을 이룹니다.)
		제한사항
		1 ≤ a의 길이 ≤ 200,000
		1 ≤ a의 모든 수 ≤ 109
		1 ≤ s의 길이 ≤ a의 길이
		1 ≤ s의 모든 수 ≤ a의 길이
		s의 모든 수의 합 = a의 길이
		입출력 예
		a	s	result
		[1,1,1,1,1,1,2,5,8,2,1,1,4,8,8,8,12,6,6]	[4,3,1,5,6]	[6,3,1,5,9]
		입출력 예 설명
		입출력 예 #1
		
		다음 5개의 b 배열에 대한 답을 구해야 합니다.
		b	답
		[1,1,1,1]	6
		[1,1,2]	3
		[5]	1
		[8,2,1,1,4]	5
		[8,8,8,12,6,6]	9
		따라서, [6,3,1,5,9]를 return 해야 합니다.
		
		
	 */
	public static void main(String[] args) {
		
		
		int[] a = {1, 1, 1, 1, 1, 1, 2, 5, 8, 2, 1, 1, 4, 8, 8, 8, 12, 6, 6};
		int[] s = {4, 3, 1, 5, 6};
		
		//[6,3,1,5,9]
		System.out.println("new Solution().solution(a, s) = " + Arrays.toString(new Solution().solution(a, s)));
		
		
	}
	
	static class Solution {
		
		public int[] solution(int[] a, int[] s) {
			
			int n = s.length;
			
			int[] answer = new int[n];
			
			LinkedList<Integer>[] lists = new LinkedList[n];
			int idx = 0;
			for (int i = 0; i < s.length; i++) {
				lists[i] = new LinkedList<>();
				for (int j = idx; j < idx + s[i]; j++) {
					lists[i].add(a[j]);
				}
				idx += s[i];
			}
			
			// 기본적으로 항상 비어있는 배열이 하나 있을테니 무조건 1이상이고
			// Y와 바인딩을 할지 안할지는 결정할 수 있기 때문에
			// 즉 앞에 데이터가 필요하다는 것일거고,, 그럼 DP네 또는 브루트 포스?
			// 근데 최적의 경로를 구하는건 아니니 DP라기 보다는 무차별 대입이 맞을 것 같은데..
			
			/*
			i	안티 세포	    c	    비고
			0	(1)(1)(1)(1)	[]	    초기 상태입니다.
			1	(1)(1)(1)(1)	[]	    i=0 일 때는 Y가 존재하지 않으므로 i를 1 증가시켰습니다.
			1	(1,1)(1)(1)	    [1]	    b[1]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합쳤습니다. 따라서 c에 i=1이 추가됩니다.
			2	(1,1)(1)(1)	    [1] 	b[1]을 포함하는 안티 세포(X) 왼쪽의 안티 세포 Y가 존재하지 않으므로 i를 1 증가시켰습니다.
			3	(1,1)(1)(1)	    [1]	    X의 모든 수의 합은 1이고, Y의 모든 수의 합은 2이므로, 둘은 합칠 수 없습니다. 따라서 i을 1 증가시켰습니다.
			3	(1,1)(1,1)	    [1,3]	b[3]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합쳤습니다. 따라서 c에 i=3이 추가됩니다.
			4	(1,1)(1,1)	    [1,3]	b[3]을 포함하는 안티 세포(X)와 그 왼쪽의 안티 세포(Y)를 합칠 수 있었지만 그러지 않았습니다. 따라서 i를 1 증가시켰습니다.
			
			이 경우 c = [1,3]이 됩니다. 물론 이는 c를 만들 수 있는 하나의 경우일 뿐이며,
			당신의 선택에 따라 [], [1], [3], [1,3], [2], [1,3,3]으로 c배열을 다양하게 만들 수 있습니다.
			당신이 어떤 선택을 하더라도 유한한 횟수 안에 c 배열을 만들 수 있음은 증명될 수 있습니다.
			 */
			
			// [8,2,1,1,4]
			// 8,2,2*,4
			// 8,4*,4
			// 8,8*
			// 16*
			// 4개 추가
			
			//
			// 이걸 일일이 찾는다는건 최대 !200000 이 된다는 소리인데.
			// 100% 타임아웃인데..
			// [8, 8, 8, 12, 6, 6]
			// 16*, 8, 12, 6, 6
			// 16, 8, 12, 12*
			// 16, 8, 24
			// 3개 추가
			// 8, 16*, 12, 6, 6
			// 8, 16, 12, 12*
			// 8, 16, 24*
			// 3개 추가
			// 8, 8, 8, 12, 12*
			// 8, 8, 8, 24*
			// 2개 추가
			
			// 8개가 가능한데 빈 배열 하나 있으니 총 9개
			// 일단 무식하게 한다면 해당 포인터를 찍어서 하면 될거 같기는 하네 ..
			// 일단 무식한 방법은 역시나 메모리 초과에다가 시간 초과 나오네
			// 결국에는 다른 방법이 있다라는 건데
			
			int cnt = 1;
			for (int i = 0; i < lists.length; i++) {
				total = 1;
				dfs(1, lists[i]);
				answer[i] = total;
				System.out.println("------------------------------");
			}
			
			return answer;
		}
		
		int e = (int)Math.pow(10, 9) + 7;
		int total = 0;
		// 일단 무식하게 풀어봅시다.
		public void dfs(int point, LinkedList<Integer> list) {
			
			if (point == 0) point = 1;
			
			for (int i = point; i < list.size(); i++) {
				int Y = list.get(i - 1);
				int X = list.get(i);
				
				if (Y == X) {
					total = (total+1) % e;
					LinkedList<Integer> temp = new LinkedList<>(list);
					temp.set(i, Y + X);
					temp.remove(i - 1);
					System.out.printf("p: %d, %s\n",i, temp);
					dfs(i-1, temp);
				}
			}
		}
	}
	
}
