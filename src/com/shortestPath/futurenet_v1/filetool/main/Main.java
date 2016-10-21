package com.shortestPath.futurenet_v1.filetool.main;

import java.io.IOException;

import com.shortestPath.futurenet_v1.filetool.util.FileUtil;
import com.shortestPath.futurenet_v1.filetool.util.LogUtil;
import com.shortestPath.futurenet_v1.routesearch.route.Route;

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
        Route.readEdges(graphContent);
        Route.readNodes(conditionContent);
        Route.initDist(Route.nodeNum);
        Route.initPath(Route.nodeNum);
        Route.floyd_m();
        Route.floyd_s(2);
        Route.floyd_t(19);
        Route.printPath(2, 17);
        Route.printPath(2, 19);
        // 写入输出文件
       // FileUtil.write(resultFilePath, resultStr, false);

        LogUtil.printLog("End");
    }

}
