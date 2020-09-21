package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.*;

public class Main {
    public static int initial_start_row=0;
    public static int initial_start_column=0;
    public static boolean left_move= false;
    public static boolean right_move= false;

    public static boolean up_move= false;
    public static boolean Down_move= false;
    public static int[][] goal_State;
    public static int left_move_count=0;
    public static int right_move_count=0;
    public static int up_move_count=0;
    public static int down_move_count=0; //down is actually right and right is down

    public static int weight_count=0;
    public static int start_row_left=0;
    public static int _start_column_left=0;

    public static int start_row_down=0;
    public static int _start_column_down=0;

    public static int start_row_up=0;
    public static int _start_column_up=0;

    public static int start_row_right=0;
    public static int _start_column_right=0;
    public static ArrayList[] arr_check;
    Tree root;
        public static String all_moves="";
    public static  PriorityQueue<Tree> pq;
    public static  PriorityQueue<Integer> pq1;
  //  public static HashSet<Tree>hs;
    public static int [][] left_move1=new int [4][4];
    public static int [][] down_move=new int [4][4];
    public static int [][] up_move1=new int [4][4];
    public static int [][] right_move1=new int [4][4];
   public static  ArrayList<Tree> hs;
    public static void main(String[] args) {
        // create 2d array first
        int count = 0;
        int count1 = 0;
        Set<Integer> treeset = new TreeSet<>();
        int[][] arr = new int[4][4];

        arr_check = new ArrayList[4];
        int[][] empty_move = new int[4][4];

//        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
//        String line = null;
//        try {
//            line = b.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String breakPoint[] = line.split(" ");

        int[] arr1 = {1, 0, 2, 4, 5, 7, 3, 8, 9, 6, 11, 12, 13, 10, 14, 15};
        //    int[][] arr1= new int [4][4];
        int[] goal_State1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        goal_State = new int[4][4];
        for (int i = 0; i < goal_State.length; i++) {
            for (int j = 0; j < goal_State.length; j++) {
                goal_State[i][j] = goal_State1[count];
                count++;
            }
        }

        System.out.println("");
        System.out.println("");
        System.out.println("GOAL STATE");

        for (int i = 0; i < goal_State.length; i++) {
            for (int j = 0; j < goal_State[i].length; j++) {
                System.out.print(goal_State[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr1[count1] == 0) {
                    initial_start_column = j;
                    initial_start_row = i;
                }
                arr[i][j] = arr1[count1];

                count1++;
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("Initial State");
        printarray2D(arr);

        Tree root_node = new Tree(arr);
        root_node.set_root(root_node);
        root_node.add(root_node, arr, weight_count);//setting root node to initial state

        //creating branches here
        //you have to push the weight of each branch in priority queue

        left_move1 = left_move(left_move1, 0, root_node);
        up_move1 = up_move(up_move1, 0, root_node);
        right_move1 = right_move(right_move1, 0, root_node);
        down_move = down_move(down_move, 0, root_node);
        int minimum1= 0;
        pq = new PriorityQueue<>();
        pq1 = new PriorityQueue<>();
        hs = new ArrayList<>();
        //keep track of
        hs.add(root_node);
        Iterator it = hs.iterator();
        for (int i = 0; i < hs.size(); i++) { //bfs implementation
            Tree minimum = hs.get(i);

            if(minimum1==-1)
            {
                System.out.println();
                System.out.println();

                System.out.println("Final State");
                printarray2D(minimum.arr1);
                System.out.println();
                System.out.println();
                System.out.println("Moves State");
                System.out.println("Total moves " + ":  " +all_moves);
            }
             //pq.poll() is getting node  and removing it both at the same time
            if (minimum.Down != null) {
                pq1.add(minimum.Down.weight);
            }
            if (minimum.Up != null) {
                pq1.add(minimum.Up.weight);
            }
            if (minimum.left != null) {
                pq1.add(minimum.left.weight);
            }
            if (minimum.right != null) {
                pq1.add(minimum.right.weight);
            }

            if(minimum1!=-1){
                minimum1 = check_move(pq1); //here it will return the poll
                if (minimum.Down != null) {
                if (minimum.Down.weight == minimum1) {
                    //go down
                    all_moves = all_moves + ("D");
                    hs.add(go_down(minimum, minimum.arr1));

                }
            }
            if (minimum.left != null) {
                if (minimum.left.weight == minimum1) {
                    all_moves = all_moves + ("L");

                    hs.add(go_left(minimum, minimum.arr1));
                    //go left
                }
            }
            if (minimum.Up != null) {
                if (minimum.Up.weight == minimum1) {
                    //go up
                    all_moves= all_moves+ ("U");

                    hs.add(go_up(minimum, minimum.arr1));
                }
            }
            if (minimum.right != null) {
                if (minimum.right.weight == minimum1) {
                    //go right
                    all_moves= all_moves + ("R");

                    hs.add(go_Right(minimum, minimum.arr1));
                }
            }

            }
            if(minimum1==0)
            {
                minimum1=-1;
            }
        }

//                 else
//                 {
//                     System.out.println("");
//                     System.out.println("");
//
//                     System.out.println("Goal state");
//                     if(minimum.right.weight==0) {
//                         printarray2D(minimum.right.arr1);
//                     }if(minimum.left.weight==0)
//                        {
//                            printarray2D(minimum.left.arr1);
//                        }
//                     if(minimum.Down.weight==0 )
//                     {
//                         printarray2D(minimum.Down.arr1);
//                     }
//                     if(minimum.Up.weight==0)
//                     {
//                         printarray2D(minimum.Up.arr1);
//                     }

        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
//        Main main = new Main();

        long time = System.nanoTime();
//        PuzzleLogic yuh = solved.findFinalSolution(puzzzle);
        long rtime = System.nanoTime();
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;

        //System.out.println("Moves: "+yuh.pathOfPuzzle);
       // System.out.println("Number of Nodes expanded: "+solved.nodes);
        System.out.println("Time Taken: " + rtime/1000000000);
        System. out.println("Memory used: "+Runtime.getRuntime().totalMemory() );

                 }

                 //okay not a good coding style or code
                 //however, implement condition for both scenarios and push into queue accordingly
                 //if(minimum.left!=null&&minimum.Down!=null&&minimum.right!=null&&minimum.Up!=null) {
                 //store weight into priority queue
             //    if(minimum.left!=null&&minimum.Down!=null&&minimum.right!=null){
               //  if (minimum.left.weight > minimum.Down.weight && minimum.right.weight  > minimum.Down.weight) {//example say down one is smallest so we chose to go down
                         //assign new columns
//                         copying_arr(left_move, down_move);//
//                         check_blank(down_move);
//                         //                    minimum.Down.root=minimum;
//                         down_move = down_move(down_move, 1, minimum.Down);
//                          up_move = up_move(left_move, 1, minimum.Down);
//                         right_move = right_move(left_move, 1, minimum.Down);
//                         left_move = left_move(left_move, 1, minimum.Down);
                         //check for same states by storing both the nodes in hashset


//                        hs.add(minimum.Down);
                     //}
                 //      }
                 //}
//                 if (minimum.Down!=null&&minimum.right!=null)
//                 {
//                     if ( minimum.right.weight <minimum.Down.weight) //so in this case you can pick any let's say we will chose left
//                     {
//                         copying_arr(down_move, right_move1);//
//
//                         check_blank(right_move1);
//
//                         up_move1 = up_move(down_move, 1, minimum.right);
//                         left_move1 = left_move(right_move1, 1, minimum.right);
//                         down_move = down_move(down_move, 1, minimum.right);
//                         right_move1 = right_move(right_move1, 1, minimum.right);
//
//
//                         pq.add(minimum.right);

//                     }
//             }
//                 if (minimum.Up != null&&minimum.left!=null) {
//                     if (minimum.Up.weight > minimum.left.weight) {
//
//                         copying_arr(down_move,left_move1);
//
//                         //go with the left branch
//                         check_blank(left_move1);
//
//                         left_move1 = left_move(left_move1, 1, minimum.left);
//                         up_move1 = up_move(down_move, 1, minimum.left);
//                         down_move = down_move(down_move, 1, minimum.left);
//                         right_move1 = right_move(down_move, 1, minimum.Down);
//                         pq.add(minimum.left);
//                     }
//                 }
//                 if (minimum.Up != null&&minimum.left!=null) {
//                     if (minimum.Up.weight <= minimum.left.weight ) {
//
//                         copying_arr(up_move1,down_move);
//
//                         //go with the left branch
//                         check_blank(down_move);
//                         down_move = down_move(down_move, 1, minimum.left);
//                         right_move1 = right_move(up_move1, 1, minimum.left);
//                         left_move1 = left_move(up_move1, 1, minimum.left);
//                         up_move1 = up_move(up_move1, 1, minimum.left);
//
//                         pq.add(minimum.left);
//                     }
//                 }




    public static Tree go_down(Tree minimum, int[][] arr)
    {
        //copying_arr(left_move1, arr);//
        check_blank(arr);
        //                    minimum.Down.root=minimum;
        down_move = down_move(arr, 1, minimum.Down);
        up_move1 = up_move(arr, 1, minimum.Down);
        right_move1 = right_move(arr, 1, minimum.Down);
        left_move1 = left_move(arr, 1, minimum.Down);
        return minimum.Down;
    }

    public static Tree go_Right(Tree minimum,int[][]arr)
    {
   //     copying_arr(left_move1, down_move);//
        check_blank(arr);
        //                    minimum.Down.root=minimum;
        down_move = down_move(arr, 1, minimum.right);
        up_move1 = up_move(arr, 1, minimum.right);
        right_move1 = right_move(arr, 1, minimum.right);
        left_move1 = left_move(arr, 1, minimum.right);
        return minimum.right;
    }

    public static Tree go_left(Tree minimum,int[][]arr)
    {
//        copying_arr(left_move1, down_move);//
        check_blank(arr);
        //                    minimum.Down.root=minimum;
        down_move = down_move(arr, 1, minimum.left);
        up_move1 = up_move(arr, 1, minimum.left);
        right_move1 = right_move(arr, 1, minimum.left);
        left_move1 = left_move(arr, 1, minimum.left);
        return minimum.left;
    }

    public static Tree go_up(Tree minimum,int[][]arr)
    {
      //  copying_arr(left_move1, down_move);//
        check_blank(arr);
        //                    minimum.Down.root=minimum;
        down_move = down_move(arr, 1, minimum.Up);
        up_move1 = up_move(arr, 1, minimum.Up);
        right_move1 = right_move(arr, 1, minimum.Up);
        left_move1 = left_move(arr, 1, minimum.Up);
        return minimum.Up;
    }

    public static int check_move(PriorityQueue<Integer> check)
    {

        return check.poll();
    }
    public static int[][] copying_arr(int[][]copy_array,int [][]down_move)
    {
        for (int i = 0; i < down_move.length; ++i) {
            for (int j = 0; j < down_move[i].length; ++j) {
                copy_array[i][j] = down_move[i][j];
            }
        }
        return copy_array;
    }
    public static int check_blank(int[][] moves_array)
    {
        for (int i = 0; i < moves_array.length; ++i) {
            for (int j = 0; j < moves_array[i].length; ++j) {
                if(moves_array[i][j]==0)
                {
                    start_row_down=i;
                    _start_column_down = j;
                    start_row_left=i;
                    _start_column_left=j;
                    start_row_up=i;
                    _start_column_up=j;
                    initial_start_column=j;
                    initial_start_row=i;
                    start_row_right=i;
                    _start_column_right=j;
                    return 0;
                }
            }
        }
        return 0;
    }

    public static void printarray2D(int[][]arr)
    {

        for(int i =0 ;i <arr.length;i++)
        {
            for(int j=0;j<arr[i].length;j++)
            {
                System.out.print(arr[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static void compare_with_goal_State(int[][] goal_state,int[][]branch_array)
    {

            for(int i =0;i< goal_State.length;i++)
            {
                for(int j=0;j<goal_state[i].length;j++){
                    if(goal_state[i][j]==branch_array[i][j])
                    {
                      //  System.out.println("element matched ");
                        //goal_count++;
                    }
                    else
                    {
                        weight_count++;
                        //System.out.println(weight_count++) ;
                    }
            }
            }


    }

    public static int[][]up_move(int[][]up_move,int i, Tree node)
    {
        if (isSafe((initial_start_column), initial_start_row-1)) {
            if(i>0) {
                if (isSafe((_start_column_up), start_row_up-1)) {
                     up_move= check_for_possible_up_moves(node, up_move, _start_column_up, start_row_up);
                }
            }
            else
            {
                up_move = check_for_possible_up_moves(node, up_move, initial_start_column, initial_start_row);
            }
        }
        return up_move;
    }
        //right move check if the move is valid
    public static int[][]right_move(int[][]right_move,int i, Tree node)
    {
        if (isSafe((initial_start_column+1), initial_start_row)) {
            if(i>0) {
                if (isSafe((_start_column_right+1), start_row_right)) {
                    right_move= check_for_possible_right_moves(node, right_move, _start_column_right, start_row_right);
                }
            }
            else
            {
                right_move = check_for_possible_right_moves(node, right_move, initial_start_column, initial_start_row);
            }
        }
        return right_move;
    }

    public static int[][]left_move(int[][]left_move,int i, Tree node)
    {
        if (isSafe((initial_start_column - 1), initial_start_row)) {
            if(i>0) {
                if (isSafe((_start_column_left - 1), start_row_left)) {
                    left_move = check_for_possible_left_moves(node, left_move, _start_column_left, start_row_left);
                }
            }
            else
            {
                left_move = check_for_possible_left_moves(node, left_move, initial_start_column, initial_start_row);
            }
        }
        return left_move;
    }

    public static int[][] down_move(int[][]down_move,int i,Tree root_node)
    {
        if (isSafe((initial_start_column), initial_start_row + 1)) {
            if(i>0) {
                if (isSafe((_start_column_down), start_row_left+1)) {
                    down_move = check_for_possible_down_moves(root_node, down_move, _start_column_down, start_row_down);
                }
            }
            else
            {
                down_move = check_for_possible_down_moves(root_node, down_move, initial_start_column, initial_start_row);
            }
        }
        return down_move;
    }

    public static boolean isSafe(int x, int y)
     {
         return (x >= 0 && x < 4 && y >= 0 && y < 4);
     }

     //checking for up move
     public static int[][] check_for_possible_up_moves(Tree node1, int[][] arr1, int start_column, int start_row) {
         int[][] arr2 = new int[4][4];
         if (up_move_count == 0)
             for (int i = 0; i < node1.arr1.length; ++i) {
                 for (int j = 0; j < node1.arr1[i].length; ++j) {
                     arr2[i][j] = node1.arr1[i][j];
                 }
                 up_move_count =1;
             }

         else {
             for (int i = 0; i < arr1.length; ++i) {
                 for (int j = 0; j < arr1[i].length; ++j) {
                     arr2[i][j] = arr1[i][j];
                 }
             }
         }

         if(arr2[start_row][start_column]==0) //means we are on correct position
         {//make moves now

             if(arr2[start_row-1][start_column]!=0) //[column-1] move to left if its not 0(empty) /
             {
                 weight_count=0;
                 int temp = arr2[start_row-1][start_column];
                 arr2[start_row-1][start_column]=arr2[start_row][start_column];
                 arr2[start_row][start_column]=temp;
                 //left_move=true;
                 up_move=true;
                 compare_with_goal_State(goal_State,arr2);
                 //pq.add(node1);
                 node1.add(node1,arr2,weight_count);
                 System.out.println();
                 System.out.println();
                 System.out.println("UP Move");
                 printarray2D(arr2);
                 up_move=false;
             }
         }

         return arr2;
     }

//check for possible right move and create its node
public static int[][] check_for_possible_right_moves(Tree node1, int[][] arr1, int start_column, int start_row) {
    int[][] arr2 = new int[4][4];
    if (right_move_count == 0)
        for (int i = 0; i < node1.arr1.length; ++i) {
            for (int j = 0; j < node1.arr1[i].length; ++j) {
                arr2[i][j] = node1.arr1[i][j];
            }
            right_move_count =1;
        }

    else {
        for (int i = 0; i < arr1.length; ++i) {
            for (int j = 0; j < arr1[i].length; ++j) {
                arr2[i][j] = arr1[i][j];
            }
        }
    }

    if(arr2[start_row][start_column]==0) //means we are on correct position
    {//make moves now

        if(arr2[start_row][start_column+1]!=0) //[column-1] move to left if its not 0(empty) /
        {
            weight_count=0;
            int temp = arr2[start_row][start_column+1];
            arr2[start_row][start_column+1]=arr2[start_row][start_column];
            arr2[start_row][start_column]=temp;
            //left_move=true;
            Down_move=true;
            compare_with_goal_State(goal_State,arr2);
            //pq.add(node1);
            node1.add(node1,arr2,weight_count);
            System.out.println();
            System.out.println();
            System.out.println("Right Move");
            printarray2D(arr2);
            Down_move=false;
        }
    }

    return arr2;
}



    //checking for left moves for branching

    public static int[][] check_for_possible_left_moves(Tree node1, int[][] arr1, int start_column, int start_row) {
        int[][] arr2 = new int[4][4];
        if (left_move_count == 0)
            for (int i = 0; i < node1.arr1.length; ++i) {
                for (int j = 0; j < node1.arr1[i].length; ++j) {
                    arr2[i][j] = node1.arr1[i][j];
                }
                left_move_count =1;
            }

        else {
            for (int i = 0; i < arr1.length; ++i) {
                for (int j = 0; j < arr1[i].length; ++j) {
                    arr2[i][j] = arr1[i][j];
                }
            }
        }

        if(arr2[start_row][start_column]==0) //means we are on correct position
        {//make moves now

            if(arr2[start_row][start_column-1]!=0) //[column-1] move to left if its not 0(empty) /
            {
                weight_count=0;
                int temp = arr2[start_row][start_column-1];
                arr2[start_row][start_column-1]=arr2[start_row][start_column];
                arr2[start_row][start_column]=temp;
                left_move=true;
                compare_with_goal_State(goal_State,arr2);
                //pq.add(node1);
                node1.add(node1,arr2,weight_count);
                System.out.println();
                System.out.println();
                System.out.println("Left Move");
//                _start_column_left = start_column-1;
//                start_row_left=start_row;
                printarray2D(arr2);
                left_move=false;
            }
        }

        return arr2;
    }
//create another function for every move which returns blank space column and row
    //checking for right moves for branching
    public static int[][] check_for_possible_down_moves(Tree node,int[][]arr1,int start_column, int start_row)
    {
        int [][]arr2=new int[4][4];
        if(down_move_count==0) {
            for (int i = 0; i < node.arr1.length; ++i) {
                for (int j = 0; j < node.arr1[i].length; ++j) {
                    arr2[i][j] = node.arr1[i][j];
                }
                down_move_count++;
            }
        }
        else {
            for (int i = 0; i < arr1.length; ++i) {
                for (int j = 0; j < arr1[i].length; ++j) {
                    arr2[i][j] = arr1[i][j];
                }
            }
        }
        if(arr2[start_row][start_column]==0) //means we are on correct position
        {//make moves now
            weight_count =0;

            if(arr2[start_row+1][start_column]!=0) //[column-1] move to left if its not 0(empty)
            {
                int temp = arr2[start_row+1][start_column];
                arr2[start_row+1][start_column]=arr2[start_row][start_column];
                arr2[start_row][start_column]=temp;
                right_move=true;
                compare_with_goal_State(goal_State,arr2);
                node.add(node,arr2,weight_count);
                //pq.add(node);
                System.out.println();
                System.out.println();
                System.out.println("Down Move");
//                start_row_down=start_row+1;
//                _start_column_down = start_column;
                right_move=false;//actually its down
                printarray2D(arr2);

            }
        }
        return arr2;
    }


    public static class Tree implements Comparator<Integer>{


        Tree root;
        int arr1[][];
        Tree left;
        Tree right;
        Tree Up;
        Tree Down;
        int weight;

        @Override
        public int compare(Integer o1, Integer o2) {
            if(o1<o2)
                return 1;
                else
                    return -1;
        }

        Tree() {

        }

        public Tree update_root()
        {
            return root;
        }
        Tree(int value[][]) {
            this.arr1 = value;

        }
        public void set_root(Tree root)
        {
            this.root = root;

        }



        public Tree add_recursive(Tree curr_node, int[][] data,int weight) {
            if(curr_node==null)
                return new Tree(data);

            //add according to the move!! so say if user make move left you will add to left node
            if(left_move)
            {

                curr_node.left = add_recursive(curr_node.left,data,weight);
                curr_node.left.weight = weight;
            }
            if(right_move)
            {
                curr_node.Down = add_recursive(curr_node.Down,data,weight);
                curr_node.Down.weight = weight;
            }
            if(up_move)
            {
                curr_node.Up = add_recursive(curr_node.Up,data,weight);
                curr_node.Up.weight = weight;
            }

            if(Down_move)
            {
                curr_node.right = add_recursive(curr_node.right,data,weight);
                curr_node.right.weight = weight;
            }
            return curr_node;
        }

        public void add(Tree node,int[][] data,int weight)
        {
             this.root = add_recursive(node,data,weight);
        }
    }
}
