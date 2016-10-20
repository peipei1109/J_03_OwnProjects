package com.shortestPath.shortestpath;

public class Frod_v2 {
	public final static int Inf = Integer.MAX_VALUE;   //用MAX_VALUE代替infinite
	public final static int n = 4;  //节点的个数

	class Edge {
		public int u, v;
		public int cost;
		
		public Edge(int u, int v, int cost) {
			super();
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
	}

	private int[][] dist = { { 0, 1, 2, 1 }, { Inf, 0, Inf, Inf }, { Inf, 2, 0, 1 }, { Inf, 1, 1, 0 } };
	private int[][] map = { { 0, 1, 2, 1 }, { Inf, 0, Inf, Inf }, { Inf, 2, 0, 1 }, { Inf, 1, 1, 0 } };
	private int[][] path = new int[4][4];
	
	
	public int[] snodes={2,3};  //中间节点
	public int msize=snodes.length;
	public int origin=0; //起点
	public int target=1; //终点
	
	
	//三维数组，保存中间计算的cost
	public int[][][] f =new int[msize][msize][msize];
	
	//初始化各个前驱节点
	public void initPath(){
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == Inf) {
					path[i][j] = -1;// 表示 i -> j 不通
				} else {
					path[i][j] = i;// 表示 i -> j 前驱为 i
				}
			}
		}
	}

	//传进来的参数是中间节点的个数。求中间节点集两两之间的最短距离与cost
	public void floyd_m(int  msize) {  
		
		for (int k = 0; k <n; k++) { 
			//除去起点和终点
			if((k!=n-1)&&(k==origin||k==target)) k++;
			if((k==n-1)&&(k==origin||k==target)) break;
			for (int i = 0; i < msize; i++) {
				for (int j = 0; j < msize; j++) {
					if (!(dist[snodes[i]][k] == Inf || dist[k][snodes[j]] == Inf) && dist[snodes[i]][snodes[j]] > dist[snodes[i]][k] + dist[k][snodes[j]]) {
						dist[snodes[i]][snodes[j]] =dist[snodes[i]][k] + dist[k][snodes[j]];
						path[snodes[i]][k] = snodes[i];
						path[snodes[i]][snodes[j]] = path[k][snodes[j]];
					}
				}
			}		
		}
	}
	
	//求起点到各中间点的最短距离cost,除去终点
	public void floyd_s(int src, int msize){		
		for (int k = 0; k <n; k++) { 
			//除去终点
			if((k!=n-1)&&(k==target)) k++;
			if((k==n-1)&&(k==target)) break;
				for (int j = 0; j < msize; j++) {
					if (!(dist[src][k] == Inf || dist[k][j] == Inf) && dist[src][snodes[j]] > dist[src][k] + dist[k][snodes[j]]) {
						dist[src][snodes[j]] =dist[src][k] + dist[k][snodes[j]];
						path[src][k] = src;
						path[src][snodes[j]] = path[k][snodes[j]];
					}
				}
			}						
	}
	
	//求各中间点到终点的最短距离cost,除去起点
	public void floyd_t(int target, int msize){		
		for (int k = 0; k <n; k++) { 
			//除去起点
			if((k!=n-1)&&(k==origin)) k++;
			if((k==n-1)&&(k==origin)) break;
				for (int j = 0; j < msize; j++) {
					if (!(dist[snodes[j]][k] == Inf || dist[k][target] == Inf) && dist[snodes[j]][target] > dist[snodes[j]][k] + dist[k][target]) {
						dist[snodes[j]][target] =dist[snodes[j]][k] + dist[k][target];
						path[snodes[j]][k] = snodes[j];
						path[snodes[j]][target] = path[k][target];
					}
				}
			}						
	}

	
	// 参数为中间节点的长度
	public void calShotest(int msize) {
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < msize; i++) {
			for (int j = 0; j < msize; j++) {
				if (i != j) {
					f[0][i][j] = dist[snodes[i]][snodes[j]] + dist[snodes[j]][target];
				}
			}
		}

		for (int i = 1; i < msize; i++) {
			for (int j = 0; j < msize; j++) {
				for (int k = 0; k < msize; k++) {
					for (int p = 0; p < msize; p++) {
						if ((j != k) && (j != p) && (k != p)) {
							if (min > f[i - 1][k][p]) {
								min = f[i - 1][k][p];
								if(p!=msize-1)continue;
							}
							f[i][j][k] = dist[snodes[j]][snodes[k]] + min;
						}
					}
				}
			}
		}
	}
	
	
	
	
	public void printPath(int from, int to) {
		/*
		 * 这是倒序输出，若想正序可放入栈中，然后输出。
		 * 
		 * 这样的输出为什么正确呢？个人认为用到了最优子结构性质， 即最短路径的子路径仍然是最短路径
		 */
		System.out.println("from: " + from + ", to: " + to);
		System.out.print(path[from][to] + " ,距离为" + dist[from][to] + "");

		while (path[from][to] != from) {
			System.out.print(path[from][to] + " ,距离为" + dist[from][to] + "");

			to = path[from][to];
		}
	}

	public static void main(String[] args) {
		Frod_v2 fr = new Frod_v2();
		fr.initPath();
		fr.floyd_m(2);
		fr.floyd_s(0, 2);
		fr.floyd_t(1, 2);
		fr.calShotest(2);
		
		System.out.printf("%d,%d,%d,%d,%d,%d,%d,%d",fr.f[0][0][0],fr.f[0][0][1],fr.f[0][1][0],fr.f[0][1][1],fr.f[1][0][0],fr.f[1][0][1],fr.f[1][1][0],fr.f[1][1][1]);
		fr.printPath(0, 1);
	}

}
