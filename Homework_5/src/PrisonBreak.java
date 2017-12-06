import java.util.*;
import java.io.*;
public class PrisonBreak {
    public static void main(String[] args){
        //try to read from file
        try{
            File file = new File("in1.txt");
            Scanner input = new Scanner(file);

            int T = Integer.parseInt(input.nextLine()); //number of test cases

            for(int i = 0; i < T; i++){

                int N = Integer.parseInt(input.nextLine()); //the size of N x N matrix
                int[][] matrix = new int[N][N];

                for(int j = 0; j < N; j++){
                    String[] tempArr = input.nextLine().split(" ");
                    for(int k = 0; k < N; k++){ //O(n^2)
                        matrix[j][k] = Integer.parseInt(tempArr[k]);
                    }
                }

                TestCase case1 = new TestCase(N, matrix);

                //FIXMe Delete Later
                System.out.println(N);

                case1.printMatrix();

                System.out.println(case1.getNumEscapePaths());
                System.out.println("-------------------------------------");
            }

        }catch(FileNotFoundException e){
            System.out.println("File does not exist. Please place a file named \"in1.txt\" in the directory");
        }
    }
}

class TestCase{
    private int N;
    private int[][] matrix;
    private boolean[][] marked;
    private Node start;
    private Node end;

    private class Node{
        private int val;
        private int row;
        private int col;

        public Node(int val, int row, int col){
            this.val = val;
            this.row = row;
            this.col = col;
        }
    }

    public TestCase(int N, int[][] matrix){
        this.N = N;
        this.matrix = matrix;
        this.marked = new boolean[N][N];

        start = new Node(matrix[0][0], 0, 0);
        end = new Node(matrix[N - 1][N - 1], N - 1, N - 1);

        //initialize marked matrix where 1 = false
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(matrix[i][j] == 1){
                    marked[i][j] = true;
                }else{
                    marked[i][j] = false;
                }
            }
        }

    }

    public void printMatrix(){
        for(int[] row : matrix){
            for(int col : row){
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public int getNumEscapePaths() {
        int count = 0;
        /*
        If the first cell [0,0] and the last cell [N-1,N-1] contain motion detectors
        John can't break out of the prison
        */
        if (start.val == 1 && end.val == 1) {
            return count;
        }

        Stack<Node> myStack = new Stack<>(); //create stack for DFS
        myStack.push(start); //push starting cell

        Node vertex;
        while(!myStack.isEmpty()){
            vertex = myStack.pop();
            int i = vertex.row;
            int j = vertex.col;

            if(!marked[i][j]){
                marked[i][j] = true;
                System.out.print("[" + vertex.row + "," + vertex.col + "] - ");

                //check right neighbor
                if(j + 1 < N){
                    if(matrix[i][j + 1] == 0){
                        int val = matrix[i][j + 1];
                        myStack.push(new Node(val, i, j + 1));
                    }
                }

                //check bottom neighbor
                if(i + 1 < N){
                    if(matrix[i + 1][j] == 0){
                        int val = matrix[i + 1][j];
                        myStack.push(new Node(val, i + 1, j));
                    }
                }

                //check left neighbor
                if((j - 1) >= 0){
                    if(matrix[i][j - 1] == 0){
                        int val = matrix[i][j - 1];
                        myStack.push(new Node(val, i, j - 1));
                    }
                }

                //check top neighbor
                if((i - 1) >= 0){
                    if(matrix[i - 1][j] == 0){
                        int val = matrix[i - 1][j];
                        myStack.push(new Node(val, i - 1, j));
                    }
                }

            }
        }

        return count;
    }

}