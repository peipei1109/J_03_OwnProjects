package com.shortestPath.shortestpath;

import java.awt.print.Printable;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;

//单源最短路径

public class Bellma_Ford {
	
	public final int  Inf =Integer.MAX_VALUE;
	public final int  N =1000;
	public static int nodenum =50;
	public static int edgenum=7;
	public static int original=1;
	
	class Edge{
		public int u,v;
		public int cost;
		
		public Edge(int u, int v, int cost) {
			super();
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
		
	}
	
	Edge [] edge =new Edge[N];
	private int[] dis =new int[N];
	private int[]pre =new int[N];
	
	
	
	
	public boolean BellmanFord(){
		for(int i=1;i<=nodenum;i++){
			dis[i]=(i==original?0:Inf);
		}
		for(int i = 1; i <= nodenum - 1; ++i)
		    for(int j = 1; j <= edgenum; ++j)
		      if(dis[edge[j].v] > dis[edge[j].u] + edge[j].cost) //松弛（顺序一定不能反~）
		      {
		        dis[edge[j].v] = dis[edge[j].u] + edge[j].cost;
		        pre[edge[j].v] = edge[j].u;
		      }
		      boolean flag = true; //判断是否含有负权回路
		      for(int i = 1; i <= edgenum; ++i)
		        if(dis[edge[i].v] > dis[edge[i].u] + edge[i].cost)
		        {
		          flag = false;
		          break;
		        }
		        return flag;
			
	}
	
	
	public void Print_path(int root){
		while(root != pre[root]) //前驱
		  {
		    System.out.printf("%d-->",root);
		    root = pre[root];
		  }
		  if(root == pre[root])
			  System.out.printf("%d\n",root);
		
	}
	
	
	public static void main(String[] args) throws IOException {
		Bellma_Ford bf =new Bellma_Ford();
		
		bf.pre[original] = original;
		if(bf.BellmanFord())
		    for(int i = 1; i <= nodenum; ++i) //每个点最短路
		    {
		      System.out.printf("%d\n", bf.dis[i]);
		      System.out.printf("Path:");
		      bf.Print_path(i);
		    }
		  else
		    System.out.println("have negative circle\n");		 
		
	   }
	    
	    
	    
}
	
