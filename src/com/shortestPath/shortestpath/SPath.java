package com.shortestPath.shortestpath;

public class SPath {
	
	public final static int Inf=Integer.MAX_VALUE;
	public final static int n=4;
//	private int[][] dist={{0,9,Inf,2},
//			              {9,0,1,Inf},
//			              {Inf,1,0,3},
//			              {2,Inf,3,0}};
	
	private int[][] dist={{0,1,2,1},
            {Inf,0,Inf,Inf},
            {Inf,2,0,1},
            {Inf,1,1,0}};
	
	private int[][] map={{0,1,2,1},
            {Inf,0,Inf,Inf},
            {Inf,2,0,1},
            {Inf,1,1,0}};
	
	private int[][] path =new int[4][4];
	
	
	
	
	public  void floyd() {
	      for(int i=0; i<n ; i++){
	        for(int j=0; j< n; j++){
	          if(map[i][j]==Inf){
	               path[i][j] = -1;//表示  i -> j 不通 
	          }else{
	               path[i][j] = i;// 表示 i -> j 前驱为 i
	          }
	        }
	      }
	      for(int k=0; k<n; k++) { //中间节点一定要放在最外层循环~参考博客http://www.cnblogs.com/hxsyl/p/3270401.html
	        for(int i=0; i<n; i++) {
	          for(int j=0; j<n; j++) {
	            if(!(dist[i][k]==Inf||dist[k][j]==Inf)&&dist[i][j] > dist[i][k] + dist[k][j]) {
	              dist[i][j] = dist[i][k] + dist[k][j];
	              path[i][k] = i; // 调试出来这句话是多余的~~~但是也可以加上吧~不妨碍结果一样的
	              path[i][j] = path[k][j];
	            }
	          }
	        }
	      }
	    }
	    void printPath(int from, int to) {
	        /*
	         * 这是倒序输出，若想正序可放入栈中，然后输出。
	         * 
	         * 这样的输出为什么正确呢？个人认为用到了最优子结构性质，
	         * 即最短路径的子路径仍然是最短路径
	         */
	    	System.out.println("from: " +from+", to: "+to );
	    	System.out.print(path[from][to] +" ,距离为"+dist[from][to]+"");
	    
	        while(path[from][to]!=from) {
	            System.out.print(path[from][to] +" ,距离为"+dist[from][to]+"");
	           
	            to = path[from][to];
	        }
	    }
	    
	    
	    public static void main(String[] args) {
			SPath sPath=new SPath();
			sPath.floyd();
			sPath.printPath(0, 2);
		}

}
