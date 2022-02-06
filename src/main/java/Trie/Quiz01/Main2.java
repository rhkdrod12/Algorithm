package Trie.Quiz01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main2 {
	public static void main(String[] args) {
		
		String[] words = {"frodo", "front", "fron", "frost", "frozen", "frame", "kakao"};
		String[] queries = {"fro??", "????o", "fr???", "fro???", "pro?", "?????"};
		
		Solution2 sol = new Solution2();
		
		int[] result = sol.solution(words, queries);
		System.out.println("result = " + Arrays.toString(result));
	}
}

class Solution2 {
	public int[] solution(String[] words, String[] queries) {
		int[] answer = new int[queries.length];
		
		HashMap<Integer, Trie[]> trieMap = new HashMap<>();
		
		//음 메모리 초과가 나는 이유는??
		for (int i = 0; i < words.length; i++) {
			int length = words[i].length();
			Trie[] tries = trieMap.getOrDefault(length, new Trie[2]);
			if(tries[0] == null){
				tries[0] = new Trie((char) 0);
				tries[1] = new Trie((char) 0);
			}
			tries[0].insert(words[i]);
			tries[1].insert(reverse(words[i]));
			trieMap.put(length, tries);
		}
		
		for (int i = 0; i < queries.length; i++) {
			String query = queries[i];
			int length = query.length();
			Trie[] tries = trieMap.get(length);
			
			if(tries != null){
				Trie trie = null;
				if (query.startsWith("?")) {
					trie = tries[1];
					query = reverse(query);
				}else{
					trie = tries[0];
				}
				
				if(trie != null){
					Trie findTrie = trie.find(query);
					if(findTrie != null) answer[i] = findTrie.count;
				}
			}
		}
		return answer;
	}
	
	static String reverse(String target) {
		return new StringBuilder(target).reverse().toString();
	}
	
	static class Trie {
		char trieStr;
		int count;      //자식이 가지고 있는 word의 총 수를 저장
		boolean word;
		Map<Character, Trie> child = new HashMap<>();    // 소문자는 26글자이므로
		
		public Trie(char trieStr) {
			this.trieStr = trieStr;
		}
		
		public void insert(String target){
			Trie trie = this;
			count++;
			for (int i = 0; i < target.length(); i++) {
				char c = target.charAt(i);
				if (trie.child.containsKey(c)) {
					trie = trie.child.get(c);
				}else{
					Trie childTrie = new Trie(c);
					trie.child.put(c, childTrie);   // 새로 생성된다는건
					trie = childTrie;
				}
				trie.count++;
			}
			//어차피 맨 마지막에 가지고있는 trie가 해당 target의 trie일테니 finish로 치환
			trie.word = true;
		}
		
		public Trie find(String target){
			Trie trie = this;
			for (int i = 0; i < target.length(); i++) {
				char c = target.charAt(i);
				if(c == '?') return trie;
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
					", count=" + count +
					", word=" + word +
					", child=" + child +
					'}';
		}
	}
}
