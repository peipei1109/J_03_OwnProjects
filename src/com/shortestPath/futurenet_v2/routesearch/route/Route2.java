package com.shortestPath.futurenet_v2.routesearch.route;

import java.awt.List;
import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.lang.model.type.NoType;

public class Route2 {
	
	public final static int Inf = 2000; 
	public static int msize=0; //中间节点个数，初始化为0；	
    public static int nodeNum=0; //所有节点个数，初始化为0；
	public static int origin = -1, target = -1;
	private static HashSet<Integer> nodes=new HashSet<Integer>(); //用于统计图中有多少个顶点。	
	private static int[] sNodes=new int[50]; //中间节点不超过50个
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();	
	public static class EdgeInfo {

		public int linkId;
		public int cost;
		
		public EdgeInfo(int linkId, int cost){
			this.linkId = linkId;
			this.cost = cost;
		}
	}	
    
	private static int[][] dist; 
    private static int[][] path;	
	private static int[][][] f ;
	
	public static void initDist(int n){
		dist=new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				dist[i][j]=Inf;
			}
		}
		
		for(int src: edges.keySet()){
			for(Map.Entry<Integer, EdgeInfo> edginfo: edges.get(src).entrySet()){
				dist[src][edginfo.getKey()]=edginfo.getValue().cost;
				//System.out.println("边："+edginfo.getValue().linkId+","+dist[src][edginfo.getKey()]);//调试信息罢了
			}			
		}		
		for(int i =0;i<n;i++){
			dist[i][i]=0;
		}
	}
	
	
	
	
	public static void initPath(int n){
		path=new int[n][n];
		for(int i=0; i<n ; i++){
	        for(int j=0; j< n; j++){
	          if(dist[i][j]==Inf){
	               path[i][j] = -1;//表示  i -> j 不通 
	          }else{
	               path[i][j] = i;// 表示 i -> j 前驱为 i
	          }
	        }
	      }
	}
	
	
	public static void readEdges(String graphContent) throws NumberFormatException, IOException{
		String lines[] = graphContent.split("\\n");
		
		for(String line : lines){
			String s[] = line.trim().split(",");
			int linkId = Integer.parseInt(s[0]);
			int sourceId = Integer.parseInt(s[1]);
			int targetId = Integer.parseInt(s[2]);
			int cost = Integer.parseInt(s[3]);			
			EdgeInfo info = new EdgeInfo(linkId, cost);
			HashMap<Integer, EdgeInfo> map = new HashMap<Integer, EdgeInfo>();
			
			if(edges.containsKey(sourceId)){
				map = edges.get(sourceId);
			}
			
			boolean exist = map.containsKey(targetId);
			
			if(!exist || (exist && cost < map.get(targetId).cost)){
				map.put(targetId, info);
			}
			
			edges.put(sourceId, map);
			
			nodes.add(sourceId);
			nodes.add(targetId);
			
		}
		
		nodeNum = nodes.size();
	}
	
	
	public static void readNodes(String condition) throws IOException{
		int i=0;
		String s[] = condition.trim().split(",");
		
		origin = Integer.parseInt(s[0]);
		target = Integer.parseInt(s[1]);
		String r[] = s[2].split("\\|");
		msize=r.length;
		
		for(String str : r){
			int nodeId = Integer.parseInt(str);
			sNodes[i++]=nodeId;
		}
	}
	
	
	//完整的floy算法
	public static  void floyd(int n) {
	      for(int k=0; k<n; k++) {
	        for(int i=0; i<n; i++) {
	          for(int j=0; j<n; j++) {
	            if(!(dist[i][k]==Inf||dist[k][j]==Inf)&&dist[i][j] > dist[i][k] + dist[k][j]) {
	              dist[i][j] = dist[i][k] + dist[k][j];
	              path[i][k] = i;
	              path[i][j] = path[k][j];
	            }
	          }
	        }
	      }
	    }

	//传进来的参数是中间节点的个数。求中间节点集两两之间的最短距离与cost
	public static void floyd_m(int  msize) {  
		
		for (int k = 0; k <nodeNum; k++) { 
			//除去起点和终点
			if((k!=nodeNum-1)&&(k==origin||k==target)) k++;
			if((k==nodeNum-1)&&(k==origin||k==target)) break;
			for (int i = 0; i < msize; i++) {
				for (int j = 0; j < msize; j++) {
					if (!(dist[sNodes[i]][k] == Inf || dist[k][sNodes[j]] == Inf) && dist[sNodes[i]][sNodes[j]] > dist[sNodes[i]][k] + dist[k][sNodes[j]]) {
						dist[sNodes[i]][sNodes[j]] =dist[sNodes[i]][k] + dist[k][sNodes[j]];
						path[sNodes[i]][k] = sNodes[i];
						path[sNodes[i]][sNodes[j]] = path[k][sNodes[j]];
					}
				}
			}		
		}
	}
	
	//求起点到各中间点的最短距离cost,除去终点
	public static void floyd_s(int src, int msize){		
		for (int k = 0; k <nodeNum; k++) { 
			//除去终点
			if((k!=nodeNum-1)&&(k==target)) k++;
			if((k==nodeNum-1)&&(k==target)) break;
				for (int j = 0; j < msize; j++) {
					if (!(dist[src][k] == Inf || dist[k][sNodes[j]] == Inf) && dist[src][sNodes[j]] > dist[src][k] + dist[k][sNodes[j]]) {
						System.out.println("sssss..."+k+","+",,,,,"+sNodes[j]);
						dist[src][sNodes[j]] =dist[src][k] + dist[k][sNodes[j]];
						path[src][k] = src;
						path[src][sNodes[j]] = path[k][sNodes[j]];
					}
				}
			}						
	}
	
	//求各中间点到终点的最短距离cost,除去起点
	public static void floyd_t(int target, int msize){		
		for (int k = 0; k <nodeNum; k++) { 
			//除去起点
			if((k!=nodeNum-1)&&(k==origin)) k++;
			if((k==nodeNum-1)&&(k==origin)) break;
				for (int j = 0; j < msize; j++) {
					if (!(dist[sNodes[j]][k] == Inf || dist[k][target] == Inf) && dist[sNodes[j]][target] > dist[sNodes[j]][k] + dist[k][target]) {
						dist[sNodes[j]][target] =dist[sNodes[j]][k] + dist[k][target];
						path[sNodes[j]][k] = sNodes[j];
						path[sNodes[j]][target] = path[k][target];
					}
				}
			}						
	}

	
	// 参数为中间节点的长度
	public static void calShotest(int msize) {
		int min = Inf;
		f=new int[msize][msize][msize];
		for (int i = 0; i < msize; i++) {
			for (int j = 0; j < msize; j++) {
				if (i != j) {
					f[0][i][j] = dist[sNodes[i]][sNodes[j]] + dist[sNodes[j]][target];
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
							f[i][j][k] = dist[sNodes[j]][sNodes[k]] + min;
						}
					}
				}
			}
		}
	}
	
	public static void searchRoute(String graphContent,String condition ) throws NumberFormatException, IOException{
		
		//boolean isSolve=true; 先假设肯定有解。。。
		
		readEdges(graphContent);
		readNodes(condition);
		initDist(nodeNum);
		initPath(nodeNum);
		floyd_m(msize);
		floyd_s(origin, msize);
		floyd_t(target, msize);
		calShotest(msize);
		
		int min=Inf;
		int ms = -1;
		int md = -1;
		for(int i =0;i<msize;i++){
			for(int j=0;j<msize;j++){
				for(int k=0;k<msize;k++){
					if(dist[origin][j]+f[i][j][k]>=Inf)
						continue;
					else{
						if(!isCircle(origin, j, k, target)){
							if(min>f[i][j][k]){
								min=f[i][j][k];
								ms=i;
								md=k;
							}
						}
					}
				}
			}
		}
		
		System.out.println(ms+","+md);
		
	}
	
	
	public static boolean isCircle(int src, int j,int k,int des){
		boolean circle=false;
		ArrayList<Integer> nodeList=new ArrayList<Integer>();
		HashSet<Integer> nodesSet=new HashSet<Integer>();
		while(path[src][j]!=src){
			nodeList.add(path[src][j]);
			nodesSet.add(path[src][j]);
			
			j = path[src][j];
		}
		
		while(path[j][k]!=j){
			nodeList.add(path[j][k]);
			nodesSet.add(path[j][k]);
			k = path[j][k];
		}
		
		while(path[k][des]!=k){
			nodeList.add(path[k][des]);
			nodesSet.add(path[k][des]);
			des = path[k][des];
		}
		
		if(nodesSet.size()!=nodeList.size()){
			circle=true;
		}
		
		return circle;
		
	}
	
	public static void printPath(int from, int to) {
		/*
         * 这是倒序输出，若想正序可放入栈中，然后输出。
         * 
         * 这样的输出为什么正确呢？个人认为用到了最优子结构性质，
         * 即最短路径的子路径仍然是最短路径
         */
    	System.out.println("from: " +from+", to: "+to );
    	
        while(path[from][to]!=from) {
            System.out.print(path[from][to] +" ,距离为"+dist[from][to]+"");
           
            to = path[from][to];
        }
		
	}  
	
	
	public static void printDist(){
		for(int i=0;i<nodeNum;i++){
			for(int j=0;j<nodeNum;j++){
				if(dist[i][j]<Inf&&dist[i][j]!=0){
					System.out.println(i+","+j+","+dist[i][j]);
				}
			}
		}
	}
	
	

	public static void main(String[] args) {
		initPath(nodeNum);
		floyd_m(2);
		floyd_s(0, 2);
		floyd_t(1, 2);
		calShotest(2);
		
		System.out.printf("%d,%d,%d,%d,%d,%d,%d,%d",f[0][0][0],f[0][0][1],f[0][1][0],f[0][1][1],f[1][0][0],f[1][0][1],f[1][1][0],f[1][1][1]);
		printPath(0, 1);
	}

}
