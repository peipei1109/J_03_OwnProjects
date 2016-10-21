package com.shortestPath.futurenet_v2.filetool.main;

import java.io.IOException;

import com.shortestPath.futurenet_v2.filetool.util.FileUtil;
import com.shortestPath.futurenet_v2.filetool.util.LogUtil;
import com.shortestPath.futurenet_v2.routesearch.route.Route2;

/**
 * 工具入口
 * 
 * @author
 * @since 2016-3-1
 * @version v1.0
 */
//本质上是一个dfs
public class Main
{
    public static void main(String[] args) throws NumberFormatException, IOException
    {
//        if (args.length != 3)
//        {
//            System.err.println("please input args: graphFilePath, conditionFilePath, resultFilePath");
//            return;
//        }

        String graphFilePath = "topo.csv";
        String conditionFilePath = "demand.csv";
        String resultFilePath = "result.csv";

        LogUtil.printLog("Begin");

        // 读取输入文件
        String graphContent = FileUtil.read(graphFilePath, null);
        String conditionContent = FileUtil.read(conditionFilePath, null);

        // 功能实现入口
        //String resultStr = Route2.searchRoute(graphContent, conditionContent);
        Route2.readEdges(graphContent);
        Route2.readNodes(conditionContent);
        Route2.initDist(Route2.nodeNum);
        Route2.initPath(Route2.nodeNum);
        Route2.floyd(Route2.nodeNum);
       // Route2.printDist();
        Route2.printPath(2, 11);
       // LogUtil.printLog(Route2.nodeNum+","+Route2.msize+","+Route2.origin+","+Route2.target+"\n");
        // 写入输出文件
       // FileUtil.write(resultFilePath, resultStr, false);

        LogUtil.printLog("End");
    }

}
