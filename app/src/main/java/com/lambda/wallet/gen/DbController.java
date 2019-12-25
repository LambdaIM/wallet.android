package com.lambda.wallet.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lambda.wallet.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/2
 * Time: 14:34
 * 数据库管理
 */
public class DbController {
    /**
     * Helper
     */
    private MyDevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private UserBeanDao mUserBeanDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new MyDevOpenHelper(context, "lambda.db", null, 1, UserBeanDao.class);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mUserBeanDao = mDaoSession.getUserBeanDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new MyDevOpenHelper(context, "lambda.db", null, 1, UserBeanDao.class);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new MyDevOpenHelper(context, "lambda.db", null, 1, UserBeanDao.class);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param userBean
     */
    public void insertOrReplace(UserBean userBean) {
        mUserBeanDao.insertOrReplace(userBean);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param userBean
     */
    public long insert(UserBean userBean) {
        return mUserBeanDao.insert(userBean);
    }

    /**
     * 更新数据
     *
     * @param userBean
     */
    public void update(UserBean userBean) {
        UserBean mOldPersonInfor = mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(userBean.getId())).build().unique();//拿到之前的记录
        if (mOldPersonInfor != null) {
            mUserBeanDao.update(userBean);
        }
    }

    /**
     * 按条件查询数据
     */
    public UserBean searchByWhere(String wherecluse) {
        UserBean userBeans =mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq(wherecluse)).build().unique();
        return userBeans;
    }

    /**
     * 查询所有数据
     */
    public List<UserBean> searchAll() {
        try {
            List<UserBean> userBeans = mUserBeanDao.queryBuilder().list();
            return userBeans;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 删除数据
     */
    public void delete(String wherecluse) {
        mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
