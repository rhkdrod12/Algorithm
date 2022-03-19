package DP.Quiz01;

public class Quiz01 {
	
	/*
		문제 설명
		단어 퍼즐은 주어진 단어 조각들을 이용해서 주어진 문장을 완성하는 퍼즐입니다. 이때, 주어진 각 단어 조각들은 각각 무한개씩 있다고 가정합니다.
		예를 들어 주어진 단어 조각이 [“ba”, “na”, “n”, “a”]인 경우 "ba", "na", "n", "a" 단어 조각이 각각 무한개씩 있습니다.
		이때, 만들어야 하는 문장이 “banana”라면 “ba”, “na”, “n”, “a”의 4개를 사용하여 문장을 완성할 수 있지만,
		“ba”, “na”, “na”의 3개만을 사용해도 “banana”를 완성할 수 있습니다.
		사용 가능한 단어 조각들을 담고 있는 배열 strs와 완성해야 하는 문자열 t가 매개변수로 주어질 때,
		주어진 문장을 완성하기 위해 사용해야 하는 단어조각 개수의 최솟값을 return 하도록 solution 함수를 완성해 주세요.
		만약 주어진 문장을 완성하는 것이 불가능하면 -1을 return 하세요.
		
		제한사항
		strs는 사용 가능한 단어 조각들이 들어있는 배열로, 길이는 1 이상 100 이하입니다.
		strs의 각 원소는 사용 가능한 단어조각들이 중복 없이 들어있습니다.
		사용 가능한 단어 조각들은 문자열 형태이며, 모든 단어 조각의 길이는 1 이상 5 이하입니다.
		t는 완성해야 하는 문자열이며 길이는 1 이상 20,000 이하입니다.
		모든 문자열은 알파벳 소문자로만 이루어져 있습니다.
		
		입출력 예
		strs	t	result
		["ba","na","n","a"]	"banana"	3
		["app","ap","p","l","e","ple","pp"]	"apple"	2
		["ba","an","nan","ban","n"]	"banana"	-1
		
		app  le
		
		abdcefghijklmn    abcdefgh, i, j, k, l, m, n, hijklmn, abc, cefg
		
		abcdefgh, hijklmn, cefg, abc, i, j, k, l, m, n
		
		// hijklmn, cefg, abc 이 것으로 매칭하는게 가지 적게 매칭이 가능함
		// 일단 길이별로 정리를 해야하나.?
		// 근데 사용한걸 또 사용할 수 있으니..
		// 그렇다고 모든 경우의 수를 돌리면 분명 시간 초과일텐데..
		// 가장 긴걸 매칭한다고 해서 저게 가장 짧을 것이라는 보장이 없음
		
		입출력 예 설명
		입출력 예 #1
		문제의 예시와 같습니다.
		
		입출력 예 #2
		"ap" 1개, "ple" 1개의 총 2개로 "apple"을 만들 수 있으므로 필요한 단어 개수의 최솟값은 2를 return 합니다.
		
		입출력 예 #3
		주어진 단어로는 "banana"를 만들 수 없으므로 -1을 return 합니다.
	 */
	public static void main(String[] args) {
		
		// String[] strs = {"ba", "na", "n", "a"};
		// String t = "banana";
		
		String[] strs = {"ab", "na", "ne", "a", "bn"};
		String t = "nabnabn";
		// ["ab", "na", "n", "a", "bn"], "nabnabn"
		// DP 문제이다라
		// b가 되는 단어 없음
		// DP[a][b] a ~ b 까지 매칭되는 최소의 단어수로 한다고 하면
		// DP[a] = 0 ~ a 까지 매칭되는 최소의 단어수
		// 현재 단어의 길이를 n이라고 하고, 현재 단어가 0 ~ a 사이에 존재한다고 하면
		// if(str.lastIndexof(words) == a) DP[a] = DP[a - n] + 1 이 정도가 되려나??
		// 근데 무조건 갱신하면 안되자나
		// 물론 a는 t의 index이긴한데
		//
		//
		
		
		System.out.println("answer = " + new Solution().solution(strs, t));
		
	}
	
	static class Solution {
		public int solution(String[] strs, String t) {
			
			int[] DP = new int[t.length()];
			
			// 해당 단어 중에서 매칭이 안된 단어가 있는 경우에는..?
			
			for (int idx = 0; idx < t.length(); idx++) {
				String target = t.substring(0, idx+1);
				DP[idx] = 30000;
				
				for (int i = 0; i < strs.length; i++) {
					String word = strs[i];
					int wordLength = word.length();
					
					// System.out.printf("target: %-10s word: %-5s wordlength: %-5d", target, word, wordLength);
					if(target.endsWith(word)){
						DP[idx] = Math.min(DP[idx], 1 + (idx >= wordLength ? DP[idx - wordLength]:0));
						// System.out.printf(" idx: %-5d DP[idx]: %-5d arr: %s",  idx, DP[idx], Arrays.toString(DP));
					}
					// System.out.println();
				}
			}
			// System.out.println("Arrays.toString(DP) = " + Arrays.toString(DP));
			return DP[t.length()-1] == 30000 ? -1: DP[t.length()-1];
		}
		
	}
}
