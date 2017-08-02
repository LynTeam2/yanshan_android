package com.ycl.framework.utils.io;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.ycl.framework.utils.util.YisLoger;

/**
 * LruCacheUtils(新的图片策略 引入LruCache) by yuchaoliang on 16/5/16.
 */
public class LruCacheUtils {//implements Serializable{

    private static class SingletonHolder {
        static final LruCacheUtils INSTANCE = new LruCacheUtils();
    }

    public static LruCacheUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //应对 单例对象 被序列化时候
//    private Object readResolve() {
//        return getInstance();
//    }

    private LruCache<String, Bitmap> mMemoryCache;

    private LruCacheUtils() {
        if (mMemoryCache == null)
            mMemoryCache = new LruCache<String, Bitmap>(
                    (int) (Runtime.getRuntime().maxMemory() / 1024) / 8) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }

                @Override
                protected void entryRemoved(boolean evicted, String key,
                                            Bitmap oldValue, Bitmap newValue) {

                }
            };
    }


    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                YisLoger.logTag("CacheUtils", "清除前  : mMemoryCache.size()_  " + mMemoryCache.size());
                mMemoryCache.evictAll();
                YisLoger.logTag("CacheUtils", "清除后  : mMemoryCache.size()_  " + mMemoryCache.size());
            }
//            mMemoryCache = null;
            System.gc();
        }
    }

    public synchronized void putBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (bitmap != null)
                mMemoryCache.put(key, bitmap);
        } else
            YisLoger.logTagW("CacheUtils", "该资源res 已经存在  ");
    }

    public synchronized Bitmap getBitmapFromMemCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        return bm;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }

}
