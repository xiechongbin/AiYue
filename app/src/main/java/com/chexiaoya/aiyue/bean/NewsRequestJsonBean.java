package com.chexiaoya.aiyue.bean;

/**
 * 新闻信息实体类
 * Created by xcb on 2019/1/12.
 */
public class NewsRequestJsonBean {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"7ab4c1f434a9061f2a8d2361788f053c","title":"排超第二阶段第十二轮即将打响：辽宁女排迎生死战，输球即被淘汰","date":"2019-01-12 15:52","category":"体育","author_name":"羽翼排球","url":"http://mini.eastday.com/mobile/190112155258679.html","thumbnail_pic_s":"http://01imgmini.eastday.com/mobile/20190112/20190112155258_169bc9634e2a4f64a476aef9a6476b17_1_mwpm_03200403.jpg"},F{"uniquekey":"221e953282633119da7d021f1a9a6239","title":"遗憾！错过了参加CBA全明星扣篮大赛，来看郭艾论是怎么说的","date":"2019-01-12 15:51","category":"体育","author_name":"体育哲人","url":"http://mini.eastday.com/mobile/190112155110037.html","thumbnail_pic_s":"http://00imgmini.eastday.com/mobile/20190112/2019011215_f3ac529ac4cf4fb2b23132351f5d41b6_3594_mwpm_03200403.jpg","thumbnail_pic_s02":"http://00imgmini.eastday.com/mobile/20190112/2019011215_a1bbfe85c8da44ddb8c23d8feba84a18_2086_mwpm_03200403.jpg","thumbnail_pic_s03":"http://00imgmini.eastday.com/mobile/20190112/2019011215_3a1e1ee6c0954f77adbd757b36094246_4282_mwpm_03200403.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private NewsResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public NewsResultBean getResult() {
        return result;
    }

    public void setResult(NewsResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
