package com.glooory.flatreader.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.glooory.flatreader.entity.gank.GankBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "gank".
*/
public class GankBeanDao extends AbstractDao<GankBean, Long> {

    public static final String TABLENAME = "gank";

    /**
     * Properties of entity GankBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property IdPrimary = new Property(0, Long.class, "idPrimary", true, "_id");
        public final static Property _id = new Property(1, String.class, "_id", false, "NEWS_ID");
    }


    public GankBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GankBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"gank\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idPrimary
                "\"NEWS_ID\" TEXT UNIQUE );"); // 1: _id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"gank\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GankBean entity) {
        stmt.clearBindings();
 
        Long idPrimary = entity.getIdPrimary();
        if (idPrimary != null) {
            stmt.bindLong(1, idPrimary);
        }
 
        String _id = entity.get_id();
        if (_id != null) {
            stmt.bindString(2, _id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GankBean entity) {
        stmt.clearBindings();
 
        Long idPrimary = entity.getIdPrimary();
        if (idPrimary != null) {
            stmt.bindLong(1, idPrimary);
        }
 
        String _id = entity.get_id();
        if (_id != null) {
            stmt.bindString(2, _id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GankBean readEntity(Cursor cursor, int offset) {
        GankBean entity = new GankBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // idPrimary
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // _id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GankBean entity, int offset) {
        entity.setIdPrimary(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.set_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GankBean entity, long rowId) {
        entity.setIdPrimary(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GankBean entity) {
        if(entity != null) {
            return entity.getIdPrimary();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GankBean entity) {
        return entity.getIdPrimary() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
