package com.ycl.framework.utils.emotion;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class EmotionLruCache {
    private LruCache<String, Bitmap> mMemoryCache;
    private static EmotionLruCache   instance;

    public static EmotionLruCache getInstance() {
        if (instance == null) {
            instance = new EmotionLruCache();
        }
        return instance;
    }

    private EmotionLruCache() {
        mMemoryCache = new LruCache<String, Bitmap>(512 * 1024) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number of items.
                return bitmap.getByteCount();
            }
        };
    }

    public void putEmoteToCache(String key, Bitmap value) {
        mMemoryCache.put(key, value);
    }

    public Bitmap getEmoteFromCache(String key) {
        return mMemoryCache.get(key);
    }
}
