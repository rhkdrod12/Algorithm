package Trie.Quiz01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		
		String query = "?????asd";
		
		System.out.println("query = " + query.substring(0, query.lastIndexOf("?")));
		System.out.println(query.lastIndexOf("?"));
		
		String[] words = {"frodo", "front", "fron", "frost", "frozen", "frame", "kakao"};
		String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?", "?????"};
		
		Solution sol = new Solution();
		
		int[] result = sol.solution(words, queries);
		System.out.println("result = " + Arrays.toString(result));
	}
}

class Solution {
	
	/*
	[본 문제는 정확성과 효율성 테스트 각각 점수가 있는 문제입니다.]

	친구들로부터 천재 프로그래머로 불리는 "프로도"는 음악을 하는 친구로부터 자신이 좋아하는
	노래 가사에 사용된 단어들 중에 특정 키워드가 몇 개 포함되어 있는지 궁금하니 프로그램으로 개발해 달라는 제안을 받았습니다.
	그 제안 사항 중, 키워드는 와일드카드 문자중 하나인 '?'가 포함된 패턴 형태의 문자열을 뜻합니다.
	와일드카드 문자인 '?'는 글자 하나를 의미하며, 어떤 문자에도 매치된다고 가정합니다.
	예를 들어 "fro??"는 "frodo", "front", "frost" 등에 매치되지만 "frame", "frozen"에는 매치되지 않습니다.
	
	가사에 사용된 모든 단어들이 담긴 배열 words와 찾고자 하는 키워드가 담긴 배열 queries가 주어질 때,
	각 키워드 별로 매치된 단어가 몇 개인지 순서대로 배열에 담아 반환하도록 solution 함수를 완성해 주세요.
	
	[가사 단어 제한사항]
	words의 길이(가사 단어의 개수)는 2 이상 100,000 이하입니다.
	각 가사 단어의 길이는 1 이상 10,000 이하로 빈 문자열인 경우는 없습니다.
	전체 가사 단어 길이의 합은 2 이상 1,000,000 이하입니다.
	가사에 동일 단어가 여러 번 나올 경우 중복을 제거하고 words에는 하나로만 제공됩니다.
	각 가사 단어는 오직 알파벳 소문자로만 구성되어 있으며, 특수문자나 숫자는 포함하지 않는 것으로 가정합니다.
	
	[검색 키워드 제한사항]
	queries의 길이(검색 키워드 개수)는 2 이상 100,000 이하입니다.
	각 검색 키워드의 길이는 1 이상 10,000 이하로 빈 문자열인 경우는 없습니다.
	전체 검색 키워드 길이의 합은 2 이상 1,000,000 이하입니다.
	검색 키워드는 중복될 수도 있습니다.
	각 검색 키워드는 오직 알파벳 소문자와 와일드카드 문자인 '?' 로만 구성되어 있으며, 특수문자나 숫자는 포함하지 않는 것으로 가정합니다.
	검색 키워드는 와일드카드 문자인 '?'가 하나 이상 포함돼 있으며, '?'는 각 검색 키워드의 접두사 아니면 접미사 중 하나로만 주어집니다.
	예를 들어 "??odo", "fro??", "?????"는 가능한 키워드입니다.
	반면에 "frodo"('?'가 없음), "fr?do"('?'가 중간에 있음), "?ro??"('?'가 양쪽에 있음)는 불가능한 키워드입니다.
	
	[입출력 예]
	words	                                                            queries	                                                result
	["frodo", "front", "frost", "frozen", "frame", "kakao"]	["fro??", "????o", "fr???", "fro???", "pro?"]	    [3, 2, 4, 1, 0]
	
	음.. 효율성 테스트가 있네.. 시간 제한이랑 메모리 사용제한이 걸려있다라는 말일거고..
	단순 이중 for문 돌리면 안된단 말이긋지..?
	? 는 접두사 또는 접미사로만 가능 중간에 ?가 있는 경우는 없음!
	단어의 길이가 1000000까지와 개수가 100000까지 키워드도 그만큼 주어지는거 가능하니 단순 이중 for문 돌린다고 하면
	
	거기에 단어도 일치하는지 for문 돌린다고 하면
	최대 100000*100000*1000000*1000000 번 작업들어가야함
	-> 백퍼 타임아웃 걸릴거 같은데.
	그러면 정규식은??
	
	1.일단 단어길이 맞지 않으면 탈락
	2.접미사와 접두사만 ?가 가능하니 음.. 일단 배열을 한번 정렬하고 나서
	3.word에 중복은 없지만 정렬은 한번 해주면 좋을거 같네,, queries도 같이
	
	 */
	
	
	public int[] solution(String[] words, String[] queries) {
		int[] answer = new int[queries.length];
		
		Map<Integer, Trie[]> map = new HashMap<>();
		Map<String, Integer> userKeyword = new HashMap<>();
		
		for (String word : words) {
			int length = word.length();
			Trie[] obj;
			if (map.containsKey(length)) {
				obj = map.get(length);
			} else {
				obj = new Trie[]{new TriePreFix((char) 0), new TrieSurFix((char) 0)};
				map.put(length, obj);
			}
			obj[0].insert(word);
			obj[1].insert(word);
		}
		
		for (int i = 0; i < queries.length; i++) {
			String query = queries[i];
			
			if (userKeyword.containsKey(query)) {
				answer[i] = userKeyword.get(query);
			} else {
				int length = query.length();
				Trie[] trieArr = map.get(length);
				Trie trie = null;
				
				if (trieArr != null) {
					// 접두사용
					if (query.startsWith("?")) {
						trie = trieArr[1].find(query);
					}
					// 접미사용
					else {
						trie = trieArr[0].find(query);
					}
					if (trie != null) {
						answer[i] = trie.getCount();
						userKeyword.put(query, answer[i]);
					}
				}
			}
		}
		return answer;
	}
	
}

// 숫자는 없는 소문자 알파벳구조이기 때문에
// 음.. 자식을 가지고 있어봤자 어차피 최대 26개 이니.. 그냥 list로 돌립시다.
// 아닌가.. 그냥 맵으로 만들어..?
// 워드 길이 별로 trie를 만든다면 조금더 빨리 검색하긴하긋네..
// String 으로 받았더니만 용량 초과나는구만..
abstract class Trie {
	
	char trieStr;
	int count;      //자식이 가지고 있는 word의 총 수를 저장
	boolean finish;
	Map<Character, Trie> child = new HashMap<>();
	
	public Trie(char trieStr) {
		this.trieStr = trieStr;
	}
	
	abstract public void insert(String target);
	
	abstract public Trie find(String target);
	
	public int getCount() {
		return count;
	}
}

//접미사용 - 뒤에 ? 있는 경우
class TriePreFix extends Trie {
	
	public TriePreFix(char trieStr) {
		super(trieStr);
	}
	
	public void insert(String target) {
		Trie triePrefix = this;
		count++;
		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			if (triePrefix.child.containsKey(c)) {
				triePrefix = triePrefix.child.get(c);
			} else {
				Trie childTriePreFix = new TriePreFix(c);
				triePrefix.child.put(c, childTriePreFix);   // 새로 생성된다는건
				triePrefix = childTriePreFix;
			}
			triePrefix.count++;
		}
		//어차피 맨 마지막에 가지고있는 trie가 해당 target의 trie일테니 finish로 치환
		triePrefix.finish = true;
	}
	
	// 해당 str를 가지고 있는 trie를 찾음
	public Trie find(String target) {
		Trie triePrefix = this;
		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			if (c == '?') return triePrefix;
			if (triePrefix.child.containsKey(c)) {
				triePrefix = triePrefix.child.get(c);
			} else {
				return null;
			}
		}
		return triePrefix;
	}
	
	
}

//접두사용 - 앞에 ? 있는 경우
class TrieSurFix extends Trie {
	
	public TrieSurFix(char trieStr) {
		super(trieStr);
	}
	
	public void insert(String target) {
		Trie trieSurFix = this;
		count++;
		for (int i = target.length() - 1; i >= 0; i--) {
			char c = target.charAt(i);
			if (trieSurFix.child.containsKey(c)) {
				trieSurFix = trieSurFix.child.get(c);
			} else {
				Trie childTrieSurFix = new TrieSurFix(c);
				trieSurFix.child.put(c, childTrieSurFix);   // 새로 생성된다는건
				trieSurFix = childTrieSurFix;
			}
			trieSurFix.count++;
		}
		//어차피 맨 마지막에 가지고있는 trie가 해당 target의 trie일테니 finish로 치환
		trieSurFix.finish = true;
	}
	
	// 해당 str를 가지고 있는 trie를 찾음
	public Trie find(String target) {
		Trie triePrefix = this;
		for (int i = target.length() - 1; i >= 0; i--) {
			char c = target.charAt(i);
			
			if (c == '?') return triePrefix;
			
			if (triePrefix.child.containsKey(c)) {
				triePrefix = triePrefix.child.get(c);
			} else {
				return null;
			}
		}
		return triePrefix;
	}
}