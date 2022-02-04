package Trie.Quiz01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		
		String[] words = {"frodo", "front", "fron", "frost", "frozen", "frame", "kakao"};
		String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
		
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
		// 일단 선형으로는 절대 안된다는걸 확인했음 ㅠㅠ
		// trie 알고리즘의 존재 여부를 확인
		// trie로 만들어봅시다
		
		// 접두사는 이제 문제 없이 찾는데
		// 찾는건 찾는데 그럼 카운트는~? insert할 때
		// 접미사는????
		
		// String[] words = {"frodo", "front", "fron", "frost", "frozen", "frame", "kakao"};
		// String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?"};
		
		Trie root = new Trie("");
		
		for (int i = 0; i < words.length; i++) {
			root.insert(words[i]);
		}
		System.out.println("root = " + root);
		
		return answer;
	}
}
// 숫자는 없는 소문자 알파벳구조이기 때문에
// 음.. 자식을 가지고 있어봤자 어차피 최대 26개 이니.. 그냥 list로 돌립시다.
// 아닌가.. 그냥 맵으로 만들어..?
//
class Trie{
	String trieStr;
	boolean finish;
	Map<Character, Trie> child = new HashMap<>();
	
	public Trie(String trieStr) {
		this.trieStr = trieStr;
	}
	
	public void insert(String target){
		Trie trie = this;
		StringBuilder builder = new StringBuilder(this.trieStr);
		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			builder.append(c);
			if (trie.child.containsKey(c)) {
				trie = trie.child.get(c);
			}else{
				Trie childTrie = new Trie(builder.toString());
				trie.child.put(c, childTrie);
				trie = childTrie;
			}
		}
		//어차피 맨마직에 가지고있는 trie가 해당 target의 trie일테니 finish로 치환
		trie.finish = true;
	}
	
	public Trie find(String target){
		Trie trie = this;
		for (int i = 0; i < target.length(); i++) {
			char c = target.charAt(i);
			if (trie.child.containsKey(c)) {
				trie = trie.child.get(c);
			}else{
				return null;
			}
		}
		return trie;
	}
	
	@Override
	public String toString() {
		return "Trie{" +
				"trieStr='" + trieStr + '\'' +
				", finish=" + finish +
				", child=" + child +
				'}';
	}
}

