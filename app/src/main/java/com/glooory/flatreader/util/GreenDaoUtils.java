package com.glooory.flatreader.util;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Created by Glooory on 2016/10/21 0021 14:23.
 */

public class GreenDaoUtils {

    private GreenDaoUtils() {
    }

    public static boolean isEntityExists(AbstractDao dao, WhereCondition condition) {
        return dao.queryBuilder()
                .where(condition)
                .build()
                .list()
                .size() >= 1;
    }

    public static void insert(AbstractDao dao, Object entity, Property property) {
        if (dao.queryBuilder().count() >= 400) {
            Object firstEntity = dao.queryBuilder()
                    .orderAsc(property)
                    .build()
                    .list()
                    .get(0);
            dao.delete(firstEntity);
        }
        dao.insert(entity);
    }

}
