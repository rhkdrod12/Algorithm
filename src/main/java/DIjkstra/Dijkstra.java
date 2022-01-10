package DIjkstra;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.swing.*;
import java.security.Key;
import java.util.*;

public class Dijkstra {

    public static void main(String[] args) {

        int n = 4; // 마을 수
        int m = 8; // 간선 수
        int x = 2; // 타겟 마을

        int[][] route = {{1, 2, 4}, {1, 3, 2}, {1, 4, 7}, {2, 1, 1}, {2, 3, 5}, {3, 1, 2}, {3, 4, 4},
                {4, 2, 3}};

        int answer = solution(n, m, x, route);

    }

    public static int solution(int n, int m, int x, int[][] route) {
        int answer = 0;

        // 결국에는 다익스트라 알고리즘은 DP를 사용하는 거고
        // 경로를 저장할 공간이 필요
        // 경로는 arrayList로 저장하면 되려나..
        // 어찌보면 이름가지고 처리하려면 이것저것 많이 고려대상이 많아지네
        // 결국에는 index화 시켜서 처리하는게 더 빠르겠네..


        HashMap<Integer, Town> nodes = new HashMap<>();


        // 경로에 따른 비용을 맵으로 만들기
        for (int i = 0; i < route.length; i++) {
            int s = route[i][0];
            int e = route[i][1];
            int c = route[i][2];

            if (nodes.containsKey(s)) {
                nodes.get(s).connectTown(e, c);
            } else {
                nodes.put(s, new Town(s, e, c));
            }
        }

        nodes.entrySet().forEach(r-> System.out.println(r));
        dijkstra(1, nodes);

//        Arrays.asList(route).forEach(item -> System.out.println(Arrays.toString(item)));
        // 비용도 저장하고 있어야하고 경로도 저장하고 있어야함~~
        // 일단 1 ~ n 까지 순차적으로 증가하는 방식임
        // 경로를 저장하고 있어야한다는 건데

        return answer;
    }

    public static void dijkstra(int townNum, HashMap<Integer, Town> towns) {

        Town town = towns.get(townNum);

        while (!town.isEmpty()){
            Node nextNode = town.getMinCostTown();

            if(town.isVisited(nextNode.nodeNum)) continue;
            town.visited(nextNode.nodeNum);

            Town nextTown = towns.get(nextNode.nodeNum);

            for(Integer ableMoveTownNum : nextTown.dist.keySet()){
                if(town.isVisited(ableMoveTownNum)) continue;

                Node tempNode = nextTown.dist.get(ableMoveTownNum);

                if(!town.dist.containsKey(tempNode)){
                    town.dist.put(ableMoveTownNum, new Node(ableMoveTownNum, tempNode.distance + nextNode.distance));
                }else if(town.dist.get(ableMoveTownNum).distance > tempNode.distance + nextNode.distance){
                    town.dist.get(ableMoveTownNum).distance = tempNode.distance + nextNode.distance;
                }

            }


        }
        // 저렇게 되면 방문정보를 어떻게 만드냐인데..
        // set?

    }
}

class Town{

    int townNum;
    PriorityQueue<Node> costs = new PriorityQueue<>();
    Set<Integer> visited = new HashSet<>();
    HashMap<Integer, Node> dist = new HashMap<>();
    HashMap<Integer, Integer> path = new HashMap<>();

    public Town(int townNum) {
        this.townNum = townNum;
        this.path.put(townNum, townNum);
        this.visited.add(townNum);
    }

    public Town(int townNum, int connectTownNum, int distance) {
        this(townNum);
        this.connectTown(connectTownNum, distance);
    }

    public void connectTown(int townNum, int distance) {
        Node connectTown = new Node(townNum, distance);
        this.costs.add(connectTown);
        this.dist.put(townNum, connectTown);
    }

    public Node getMinCostTown() {
        return costs.poll();
    }

    public boolean isEmpty(){
        return costs.isEmpty();
    }

    public boolean isVisited(int townNum){
        return visited.contains(townNum);
    }
    public void visited(int townNum){
        visited.add(townNum);
    }

    @Override
    public String toString() {
        return "Town{" +
                "townNum=" + townNum +
                ", costs=" + costs +
                ", visited=" + visited +
                ", dist=" + dist +
                ", path=" + path +
                '}';
    }
}

class Node implements Comparable<Node> {
    int nodeNum;
    int distance = Integer.MAX_VALUE;

    public Node(int nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Node(int nodeNum, int distance) {
        this.nodeNum = nodeNum;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        return distance - o.distance;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeNum=" + nodeNum +
                ", distance=" + distance +
                '}';
    }
}