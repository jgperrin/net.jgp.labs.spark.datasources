package net.jgp.labs.spark.datasources.utils;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructType;

import net.jgp.labs.spark.datasources.model.SparkColumn;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import static java.lang.System.out;

enum ClassMember {
    CONSTRUCTOR, FIELD, METHOD, CLASS, ALL
}

public class SparkUtils {

    public static StructType getSchemaFromBean(Class<?> c) {

        Method[] methods = c.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String name = method.getName().toLowerCase();
            if (name.startsWith("get")) {
                SparkColumn sparkColumn = method.getAnnotation(SparkColumn.class);
                String columnName;
                DataType dataType;
                boolean nullable;
                if (sparkColumn == null) {
                    columnName = "";
                    dataType = DataTypes.StringType;
                    nullable = false;
                } else {
                    columnName = sparkColumn.name();
                    try {
                        dataType = sparkColumn.type().newInstance();
                    } catch (InstantiationException e) {
                        // TODO Auto-generated catch block
                        dataType = DataTypes.StringType;
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        dataType = DataTypes.StringType;
                    }
                    nullable = sparkColumn.nullable();
                }
            }
        }

        return null;
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format("  %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format("  %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format("  %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format("  -- No %s --%n", s);
        out.format("%n");
    }

}
