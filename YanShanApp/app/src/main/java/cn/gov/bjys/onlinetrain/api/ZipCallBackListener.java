package cn.gov.bjys.onlinetrain.api;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
public interface ZipCallBackListener<T> {
    void zipCallBackListener(List<T> datas);

    void zipCallBackSuccess();

    void zipCallBackFail();
}
