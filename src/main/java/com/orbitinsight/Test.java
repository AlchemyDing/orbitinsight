package com.orbitinsight;

import cn.hutool.extra.tokenizer.Result;
import elki.data.DoubleVector;
import elki.data.type.TypeUtil;
import elki.database.Database;
import elki.database.StaticArrayDatabase;
import elki.database.ids.DBIDIter;
import elki.database.relation.Relation;
import elki.datasource.ArrayAdapterDatabaseConnection;
import elki.datasource.DatabaseConnection;
import elki.distance.minkowski.EuclideanDistance;
import elki.outlier.distance.DBOutlierDetection;
import elki.outlier.distance.DBOutlierScore;
import elki.result.ResultUtil;
import elki.result.outlier.OutlierResult;
import elki.utilities.optionhandling.parameterization.ListParameterization;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static List<DoubleVector> createData() {
        List<DoubleVector> data = new ArrayList<>();
        data.add(new DoubleVector(new double[]{1.0, 2.0}));
        data.add(new DoubleVector(new double[]{2.0, 3.0}));
        data.add(new DoubleVector(new double[]{3.0, 4.0}));
        data.add(new DoubleVector(new double[]{10.0, 11.0})); // 这个可能是离群点
        return data;
    }

    public static double[][] convertToMatrix(List<DoubleVector> data) {
        double[][] matrix = new double[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            matrix[i] = data.get(i).toArray();
        }
        return matrix;
    }

    public static Database loadDatabase(List<DoubleVector> data) {
        DatabaseConnection connection = new ArrayAdapterDatabaseConnection(convertToMatrix(data));
        StaticArrayDatabase db = new StaticArrayDatabase(connection, null);
        db.initialize();
        return db;
    }

    public static void main(String[] args) {
        // 创建数据集合
        List<DoubleVector> data = createData();

        // 加载数据库
        Database database = loadDatabase(data);

        // 配置 DBOutlierDetection 算法
        ListParameterization params = new ListParameterization();
        params.addParameter(DBOutlierScore.Par.D_ID, 3.0);
        // 邻域半径
//        params.addParameter(DBOutlierDetection.Par.P_ID, 0.5);
        // 密度阈值
        // 创建算法实例
        DBOutlierScore.Par<DoubleVector> doubleVectorPar = new DBOutlierScore.Par<>();
        doubleVectorPar.configure(params);
        DBOutlierScore<DoubleVector> algorithm = doubleVectorPar.make();


        Relation<DoubleVector> relation = database.getRelation(TypeUtil.DOUBLE_VECTOR_FIELD);

        // 运行算法
        OutlierResult result = algorithm.run(relation);
        List<OutlierResult> outlierResults = OutlierResult.getOutlierResults(result);
        OutlierResult outlierResult = outlierResults.get(0);

        // 获取离群点分数
        for (DBIDIter iter = relation.iterDBIDs(); iter.valid(); iter.advance()) {
            double score = outlierResult.getScores().doubleValue(iter);
            System.out.println("Object ID: " + iter + ", Score: " + score);
        }

        System.out.println();
    }
}