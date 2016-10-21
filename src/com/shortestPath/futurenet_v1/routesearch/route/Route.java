package com.shortestPath.futurenet_v1.routesearch.route;

import java.awt.List;
import java.awt.Dialog.ModalExclusionType;
import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.NoType;
import javax.swing.tree.AbstractLayoutCache.NodeDimensions;

public class Route {

	public final static int Inf = 1020;
	public static int msize = 0; // 中间节点个数，初始化为0；
	public static int nodeNum = 0; // 所有节点个数，初始化为0；
	public static int origin = -1, target = -1;
	private static HashSet<Integer> nodes = new HashSet<Integer>(); // 用于统计图中有多少个顶点。
	private static int[] sNodes = new int[50]; // 中间节点不超过50个
	private static HashMap<Integer, HashMap<Integer, EdgeInfo>> edges = new HashMap<Integer, HashMap<Integer, EdgeInfo>>();

	public static class EdgeInfo {

		public int linkId;
		public int cost;

		public EdgeInfo(int linkId, int cost) {
			this.linkId = linkId;
			this.cost = cost;
		}
	}

	private static int[][] dist;
	private static int[][] map;
	private static int[][] path;
	private static int[][][] f;

	public static void readEdges(String graphContent) throws NumberFormatException, IOException {
		String lines[] = graphContent.split("\\n");

		for (String line : lines) {
			String s[] = line.trim().split(",");
			int linkId = Integer.parseInt(s[0]);
			int sourceId = Integer.parseInt(s[1]);
			int targetId = Integer.parseInt(s[2]);
			int cost = Integer.parseInt(s[3]);
			EdgeInfo info = new EdgeInfo(linkId, cost);
			HashMap<Integer, EdgeInfo> map = new HashMap<Integer, EdgeInfo>();

			if (edges.containsKey(sourceId)) {
				map = edges.get(sourceId);
			}

			boolean exist = map.containsKey(targetId);

			if (!exist || (exist && cost < map.get(targetId).cost)) {
				map.put(targetId, info);
			}

			edges.put(sourceId, map);

			nodes.add(sourceId);
			nodes.add(targetId);

		}

		nodeNum = nodes.size();
	}

	public static void readNodes(String condition) throws IOException {
		int i = 0;
		String s[] = condition.trim().split(",");

		origin = Integer.parseInt(s[0]);
		target = Integer.parseInt(s[1]);
		String r[] = s[2].split("\\|");
		msize = r.length;

		for (String str : r) {
			int nodeId = Integer.parseInt(str);
			sNodes[i++] = nodeId;
		}
	}

	public static void initDist(int n) {
		dist = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = Inf;
			}
		}

		for (int src : edges.keySet()) {
			for (Map.Entry<Integer, EdgeInfo> edginfo : edges.get(src).entrySet()) {
				dist[src][edginfo.getKey()] = edginfo.getValue().cost;
				// System.out.println("边："+edginfo.getValue().linkId+","+src+","+edginfo.getKey()+","+dist[src][edginfo.getKey()]);//调试信息罢了
			}
		}
		for (int i = 0; i < n; i++) {
			dist[i][i] = 0;
		}

		map = dist.clone();
	}

	public static void initPath(int n) {
		path = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == Inf) {
					path[i][j] = -1;// 表示 i -> j 不通
				} else {
					path[i][j] = i;// 表示 i -> j 前驱为 i
					// System.out.println(i+","+j+","+path[i][j]);
				}
			}
		}
	}

	public static void floyd(int n) {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (!(dist[i][k] == Inf || dist[k][j] == Inf) && dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						path[i][j] = path[k][j];

					}
				}
			}
		}
	}

	// 传进来的参数是中间节点的个数。求中间节点集两两之间的最短距离与cost
	public static void floyd_m() {
		HashSet<Integer> mNodes = (HashSet<Integer>) nodes.clone();
		mNodes.remove(origin);
		mNodes.remove(target);
		for (int k : mNodes) {
			for (int i : mNodes) {
				for (int j : mNodes) {
					if (!(dist[i][k] == Inf || dist[k][j] == Inf) && dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						path[i][j] = path[k][j];
					}
				}
			}
		}
	}

	// 求起点到各中间点的最短距离cost,除去终点
	public static void floyd_s(int src) {

		@SuppressWarnings("unchecked")
		HashSet<Integer> srcNodes = (HashSet<Integer>) nodes.clone();
		srcNodes.remove(target);
		for (int k : srcNodes) {
			for (int j : srcNodes) {
				if (!(dist[src][k] == Inf || dist[k][j] == Inf) && dist[src][j] > dist[src][k] + dist[k][j]) {
					dist[src][j] = dist[src][k] + dist[k][j];
					path[src][j] = path[k][j];
				}
			}
		}

	}

	// 求各中间点到终点的最短距离cost,除去起点
	public static void floyd_t(int target) {
		@SuppressWarnings("unchecked")
		HashSet<Integer> tNodes = (HashSet<Integer>) nodes.clone();
		tNodes.remove(origin);
		for (int k : tNodes) {
			for (int j : tNodes) {
				if (!(dist[j][k] == Inf || dist[k][target] == Inf) && dist[j][target] > dist[j][k] + dist[k][target]) {
					dist[j][target] = dist[j][k] + dist[k][target];
					path[j][target] = path[k][target];
				}
			}
		}
	}

	public static void dispalyPath() {
		for (int i = 0; i < nodeNum; i++) {
			for (int j = 0; j < nodeNum; j++) {
				System.out.print(path[i][j] + ",");
			}
			System.out.println();
		}
	}

	public static void printPath(int from, int to) {
		/*
		 * 这是倒序输出，若想正序可放入栈中，然后输出。
		 * 
		 * 这样的输出为什么正确呢？个人认为用到了最优子结构性质， 即最短路径的子路径仍然是最短路径
		 */

		if (dist[from][to] != Inf) {
			System.out.println("from: " + from + ", to: " + to);
			System.out.println("最短距离为： " + dist[from][to]);
			while (path[from][to] != from) {
				String str = (path[from][path[from][to]] != from) ? "|" : "\n";
				System.out.print(path[from][to] + str);

				to = path[from][to];

			}
		}else{
			System.out.println("该两点间不存在满足要求的路径！！！");
		}
	}
}
