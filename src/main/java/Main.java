import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair> {
    private final int vertex;
    private final int minWeight;
    public Pair(int vertex , int minWeight) {
        this.vertex = vertex;
        this.minWeight = minWeight;
    }
    public int getVertex() {
        return vertex;
    }
    @Override
    public int compareTo(Pair o) {
        int result;
        result = Integer.compare(this.minWeight, o.minWeight);
        return result;
    }
}
public class Main {
    private static final int INF = 2140000000;
    private int[][] goWeightArr;
    private int[][] backWeightArr;
    private int peopleNum;
    private int edgeNum;
    private int targetVertex;
    private void myDijkstra(int startVertex , int[][] weightArr , int[] distance) {
        Arrays.fill(distance , INF);
        distance[startVertex] = 0;
        Pair pair = new Pair(startVertex , 0);
        PriorityQueue<Pair> myMinPQ = new PriorityQueue<Pair>();
        myMinPQ.add(pair);
        while (!myMinPQ.isEmpty()) {
            int vertex = myMinPQ.peek().getVertex();
            myMinPQ.poll();
            for (int i = 1; i < weightArr[vertex].length; i ++) {
                int weight = weightArr[vertex][i];
                if ((weight != INF) && (distance[vertex] + weight < distance[i])) {
                    distance[i] = distance[vertex] + weight;
                    myMinPQ.add(new Pair(i , distance[i]));
                }
            }
        }
    }
    private int getMaxWeight() {
        int result = 0;
        int[] goMinDistance = new int[peopleNum + 1];
        int[] backMinDistance = new int[peopleNum + 1];
        myDijkstra(targetVertex , goWeightArr , goMinDistance);
        myDijkstra(targetVertex , backWeightArr , backMinDistance);
        for (int i = 1; i < goMinDistance.length; i ++) {
            result = Math.max(result , goMinDistance[i] + backMinDistance[i]);
        }
        return result;
    }
    private void makeWeightArr() {
        goWeightArr = new int[peopleNum + 1][peopleNum + 1]; // target 으로 가는 방향 그래프
        backWeightArr = new int[peopleNum + 1][peopleNum + 1]; // target 에서 오는 방향 그래프
        for (int[] array : goWeightArr) {
            Arrays.fill(array, INF);
        }
        for (int[] array : backWeightArr) {
            Arrays.fill(array, INF);
        }
    }
    public void solution() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        peopleNum = Integer.parseInt(st.nextToken());
        edgeNum = Integer.parseInt(st.nextToken());
        targetVertex = Integer.parseInt(st.nextToken());
        makeWeightArr();

        for (int i = 0; i < edgeNum; i ++) {
            st = new StringTokenizer(br.readLine());
            int vertex = Integer.parseInt(st.nextToken());
            int edge = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            goWeightArr[edge][vertex] = weight;
            backWeightArr[vertex][edge] = weight;
        }
        System.out.println(getMaxWeight()); // 가장 긴 거리를 출력
        br.close();
    }
    public static void main (String[] args) throws Exception {
        new Main().solution();
    }
}
