package cn.gov.bjys.onlinetrain.utils.multi_file_download.api;

/**
 * Created by dodozhou on 2017/8/22.
 */
public interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
