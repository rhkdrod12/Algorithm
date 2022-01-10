package DIjkstra;

import java.util.*;

public class Dijkstra {

    public static void main(String[] args) {

        Integer a = 1;
        Integer b = 3;

        System.out.println(a.equals(b));
        int n = 4; // 마을 수
        int m = 8; // 간선 수
        int x = 2; // 타겟 마을

        int[][] route = {{1, 2, 4}, {1, 3, 2}, {1, 4, 7}, {2, 1, 1}, {2, 3, 5}, {3, 1, 2}, {3, 4, 4},
                {4, 2, 3}, {4,3,1}};

        int answer = solution(n, m, x, route);

    }

    public static int solution(int n, int m, int x, int[][] route) {
        int answer = 0;

        // 결국에는 다익스트라 알고리즘은 DP를 사용하는 거고
        // 경로를 저장할 공간이 필요
        // 경로는 arrayList로 저장하면 되려나..
        // 어찌보면 이름가지고 처리하려면 이것저것 많이 고려대상이 많아지네
        // 결국에는 index화 시켜서 처리하는게 더 빠르겠네..

        HashMap<Integer, Town<Integer>> nodes = new HashMap<>();

        // 경로에 따른 비용을 맵으로 만들기
        for (int i = 0; i < route.length; i++) {
            int s = route[i][0];
            int e = route[i][1];
            int c = route[i][2];

            if (nodes.containsKey(s)) {
                nodes.get(s).connectTown(e, c);
                nodes.get(s).setPath(e, s);
            } else {
                nodes.put(s, new Town(s, e, c));
            }
        }

        nodes.entrySet().forEach(r-> System.out.println(r));
        for(Integer townNum : nodes.keySet()){
            dijkstra(townNum, nodes);
        }

        System.out.println("결과");
        nodes.entrySet().forEach(r-> System.out.println(r));

        for(Integer townNum : nodes.keySet()){
            if(townNum != x){
                int cost = nodes.get(townNum).dist.get(x).distance + nodes.get(x).dist.get(townNum).distance;
                nodes.get(townNum).printPath(x);
                if(answer < cost) answer = cost;
            }
        }
        System.out.println("결과값: " + answer);
        return answer;
    }

    public static void dijkstra(int townNum, HashMap<Integer, Town<Integer>> towns) {

        Town<Integer> town = towns.get(townNum);

        while (!town.isEmpty()){
            Node<Integer> nextTownNode = town.getMinCostTown();

            if(town.isVisited(nextTownNode.nodeNum)) continue;
            town.visited(nextTownNode.nodeNum);

            Town<Integer> nextTown = towns.get(nextTownNode.nodeNum);

            for(Integer ableTownNum : nextTown.dist.keySet()){
                if(town.isVisited(ableTownNum)) continue;

                Node<Integer> tempNode = nextTown.dist.get(ableTownNum);
                if(!town.dist.containsKey(ableTownNum) || (town.dist.get(ableTownNum).distance > (tempNode.distance + nextTownNode.distance))){
                    town.dist.put(ableTownNum, new Node(ableTownNum, tempNode.distance + nextTownNode.distance));
                    town.setPath(ableTownNum, nextTown.townNum);
                }
            }
        }
        //약간 낭비이긴 하네, 굳이 없어도 되는 일회성 정보인데.. 저장안해도 되는데 공간을 차지하고 있으니,, null처리한다고 해도 음. 별로겠네
        town.setInit();

    }
}

class Town<T>{

    T townNum;
    PriorityQueue<Node> costs = new PriorityQueue<>();
    Set<T> visited = new HashSet<>();
    HashMap<T, Node> dist = new HashMap<>();
    HashMap<T, T> path = new HashMap<>();

    public Town(T townNum) {
        this.townNum = townNum;
        this.path.put(townNum, townNum);
        this.visited.add(townNum);
    }

    public Town(T townNum, T connectTownNum, int distance) {
        this(townNum);
        this.connectTown(connectTownNum, distance);
        this.path.put(connectTownNum, townNum);
    }

    public void setInit(){
        this.visited = null;
        this.costs = null;
    }

    public void printPath(T townNum) {
        ArrayList list = new ArrayList();
        list.add(townNum);
        T tempNum = townNum;
        for (int i = 0; i < path.size(); i++) {

            if(path.get(tempNum).equals(tempNum)) break;

            list.add(path.get(tempNum));
            tempNum = path.get(tempNum);
        }
        Collections.reverse(list);
        System.out.println(String.format("Path %s -> %s : %s", this.townNum, townNum , list));
    }

    public void connectTown(T townNum, int distance) {
        Node connectTown = new Node(townNum, distance);
        this.costs.add(connectTown);
        this.dist.put(townNum, connectTown);
    }
    public void setPath(T from, T to) {
        path.put(from, to);
    }

    public Node getMinCostTown() {
        return costs.poll();
    }

    public boolean isEmpty(){
        return costs.isEmpty();
    }

    public boolean isVisited(T townNum){
        return visited.contains(townNum);
    }
    public void visited(T townNum){
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

class Node<T> implements Comparable<Node> {
    T nodeNum;
    int distance = Integer.MAX_VALUE;

    public Node(T nodeNum) {
        this.nodeNum = nodeNum;
    }

    public Node(T nodeNum, int distance) {
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