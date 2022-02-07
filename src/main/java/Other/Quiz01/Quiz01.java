package Other.Quiz01;

public class Quiz01 {
	
	public static void main(String[] args) {
		
		int answer = new Solution().solution(2147483647);
		System.out.println(answer);
	}
	
	
	static class Solution {
		public int solution(int n) {
			int answer = 0;
			
			// 짝수인경우 연산하지 않음, 0반환
			if (n % 2 == 0) return 0;
			
			dfs(n-2, 2);
			answer = cnt;
			
			return answer;
		}
		
		/**
		 * @param target 현재 가감량
		 * @param addCnt 사용한 + 개수
		 * @return
		 */
		int cnt = 0;
		public void dfs(int target, int addCnt) {
			// 가감으로 처리합시다..ㅠㅠ
			// 가감이니 만약 target 3이고 addCnt가 2개가 남으면 올바른 값 증감
			// target이 3보다 작은데 남은 addCnt가 사용가능한 addCnt보다 크다면 불가능하므로 return 처리
			// 어떻게 되었든 항상 3의 배수가 나오고 안사용된 ++가 모두 사용되어야함
			// 즉 ++가 사용되었다면 어느쪽이라도 3의배수값이 하나라도 나와야함
			// 3으로 나눌 수 있을 때 3의 배수가 곱해진거나 마찬가지, 그리고 3의 배수가 곱해졌을 때 뒤에서 오는 구조이니까
			// 사용한 addCnt가 있어야지요 그렇기 때문에 addCnt > 1 즉 못해도 2개는 있어야한다는거
			
			if(target < 3 || (int)(Math.log(target)/Math.log(3))*2 < addCnt) return;
			if(target == 3 && addCnt == 2){
				cnt++;
				return;
			}
			if (target % 3 == 0 && addCnt > 1) {dfs(target / 3, addCnt - 2);}
			dfs(target - 1, addCnt + 1);
			
		}
	}
}


