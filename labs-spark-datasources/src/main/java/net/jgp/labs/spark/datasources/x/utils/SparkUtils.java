package net.jgp.labs.spark.datasources.x.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jgp.labs.spark.datasources.x.model.PhotoMetadata;

public class SparkUtils {
    private static Logger log = LoggerFactory.getLogger(SparkUtils.class);

    public static StructType getSchemaFromBean(Class<?> c) {
        List<StructField> sfl = new ArrayList<>();

        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.toLowerCase().startsWith("get") == false) {
                continue;
            }

            // We have a public method starting with get
            String columnName;
            DataType dataType;
            boolean nullable;
            SparkColumn sparkColumn = method.getAnnotation(SparkColumn.class);
            if (sparkColumn == null) {
                log.debug("No annotation for method {}", methodName);
                columnName = "";
                dataType = getDataTypeFromReturnType(method);
                nullable = true;
            } else {
                columnName = sparkColumn.name();
                log.debug("Annotation for method {}, column name is {}",
                        methodName,
                        columnName);

                switch (sparkColumn.type().getSimpleName()) {
                case "StringType":
                    dataType = DataTypes.StringType;
                    break;
                case "BinaryType":
                    dataType = DataTypes.BinaryType;
                    break;
                case "BooleanType":
                    dataType = DataTypes.BooleanType;
                    break;
                case "DateType":
                    dataType = DataTypes.DateType;
                    break;
                case "TimestampType":
                    dataType = DataTypes.TimestampType;
                    break;
                case "CalendarIntervalType":
                    dataType = DataTypes.CalendarIntervalType;
                    break;
                case "DoubleType":
                    dataType = DataTypes.DoubleType;
                    break;
                case "FloatType":
                    dataType = DataTypes.FloatType;
                    break;
                case "ByteType":
                    dataType = DataTypes.ByteType;
                    break;
                case "IntegerType":
                    dataType = DataTypes.IntegerType;
                    break;
                case "LongType":
                    dataType = DataTypes.LongType;
                    break;
                case "ShortType":
                    dataType = DataTypes.ShortType;
                    break;
                case "NullType":
                    dataType = DataTypes.NullType;
                    break;
                default:
                    log.debug("Will infer data type from return type for column {}",
                            columnName);
                    dataType = getDataTypeFromReturnType(method);
                }

                nullable = sparkColumn.nullable();
            }
            sfl.add(DataTypes.createStructField(
                    buildColumnName(columnName, methodName), dataType, nullable));

        }

        StructType schema = DataTypes.createStructType(sfl);
        return schema;
    }

    private static DataType getDataTypeFromReturnType(Method method) {
        String typeName = method.getReturnType().getSimpleName();
        switch (typeName) {
        case "int":
        case "Integer":
            return DataTypes.IntegerType;
        case "long":
        case "Long":
            return DataTypes.LongType;
        case "float":
        case "Float":
            return DataTypes.FloatType;
        case "boolean":
        case "Boolean":
            return DataTypes.BooleanType;
        case "double":
        case "Double":
            return DataTypes.DoubleType;
        case "String":
            return DataTypes.StringType;
        case "Date":
            return DataTypes.TimestampType;
        case "short":
        case "Short":
            return DataTypes.ShortType;
        case "Object":
            return DataTypes.BinaryType;
        default:
            log.debug("Using default for type [{}]", typeName);
            return DataTypes.BinaryType;
        }
    }

    private static String buildColumnName(String columnName, String methodName) {
        if (columnName.length() > 0) {
            return columnName;
        }
        columnName = methodName.substring(3);
        if (columnName.length() == 0) {
            return "_c0";
        }
        return columnName;
    }

    public static Row getRowFromBean(StructType structType, Object bean) {
        List<Object> cells = new ArrayList<>();
        String[] fieldName = structType.fieldNames();

        Method method;
        for (int i = 0; i < fieldName.length; i++) {
            try {
                method = bean.getClass().getMethod("get" + fieldName[i]);
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                cells.add(method.invoke(bean));
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Row row = RowFactory.create(cells.toArray());
        return row;
    }

}
