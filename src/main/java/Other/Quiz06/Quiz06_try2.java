package Other.Quiz06;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Quiz06_try2 {
	
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
			
			// 결국에는 두 수가 같은 경우에만 진행이 가능한거지요
			// 그렇다면 두 수가 같다라는건
			// 어떻게 앞에 몇번을 더해서 만들어졌든 양쪽간 합이 그 수의
			// 2^N에 속한다는 거고
			// 그래서 DP를 선언한다면
			// DP[i][e] = b[i]의 수를 이용해서 2^e배 했을 때 나오는 경우의 수라고 합시다
			// 합칠 수 있어도 안합칠 수 도 있기 떄문에
			
			/**
			 * DP[i][j] = b[i]에서 세포합치기를 j번 반복했을 때의 경우의 수 라고 정의합시다.
			 * 그리고 j번 합쳤을 때 나오는 수는 b[i]*2^j 따라서 j번 합치기 위해서는 b[i]가 속해있는 세포의 값이 b[i]*(j-1)이 될 때,
			 * b[i]가 속해있는 세포가 시작되는 위치가 k라고 한다면 b[k-1]*2^p == b[i]*(j-1) (단, 0<= p < k-1) 당연히 k 이상으로 곱할 수는 없틀테니...
			 *
			 * 따러서 DP[i][j] = SUM(DP[k-1][p])(b[k-1]*2^p == b[i]*(j-1)의 조건일 떄)
			 * 그러면 K의 위치를 찾아야하는데.. 어떻게 찾아야하나..
			 * 이러면 값 보관하고 있을 K라는 배열을 하나 만들어야하나..?
			 * [8, 8, 8, 12, 6, 6]
			 *
			 * i = 5라고 한다면
			 * j = 0 은 안합친거
			 * j = 1 은 한번 합친거니 바로 옆의 6과 합쳐야함
			 * 그럼 k = 4가 될거고
			 * b[k-1] = b[3] 이 될테니 b[3] = 12
			 * b[i]*2^(j-1) = 12 이고 b[3] = 12이니 참!
			 *
			 * j = 2 면
			 * k = 5 - 2 = 3
			 * 3 ~ 4번 까지 b[5][j] 3 <= j < 5까지 전부 b[5]*2^j 에 속해야함
			 * 6, 12 이니까 전부 속함 가능!
			 * 매번 연산하는것보다 전부 구해서 넣어놓는게 더 좋으려나..?
			 *
			 *
			 *
			 */
			
			List<Integer> list = lists[4];
			int size = lists[4].size();
			
			int[][] DP = new int[n][n];
			int[][] PS = new int[size][size];
			
			//PS[5][0] = b[i]*2^0
			//PS[5][1] = b[i]*2^1
			//PS[5][2] = b[i]*2^2
			//
			for (int i = size-1; i > 0; i--) {
				int temp = 0;
				for (int j = 0; j < i; j++) {
					temp += list.get(i - j);
					int val = list.get(i) * (int)Math.pow(2, j);
					System.out.printf("i: %d val: %2d, temp: %2d\n",i, val, temp);
					if (val == temp) {
						PS[i][j] = list.get(i) * (int)Math.pow(2, j);
					}else{
						break;
					}
				}
			}
			
			Arrays.asList(PS).forEach(x -> System.out.println(Arrays.toString(x)));
			
			
			
			// [8, 8, 8, 12, 6, 6]
			// 16*, 8, 12, 6, 6
			// 16, 8, 12, 12*
			// 16, 8, 24
			// 3개 추가
			// [8, 8, 8, 12, 6, 6]
			// 8, 16*, 12, 6, 6
			// 8, 16, 12, 12*
			// 8, 16, 24*
			// 3개 추가
			// [8, 8, 8, 12, 6, 6]
			// 8, 8, 8, 12, 12*
			// 8, 8, 8, 24*
			// 2개 추가
			
			return answer;
		}
		
	}
	
}
