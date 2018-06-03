package cn.gov.bjys.onlinetrain.bean;

import java.util.List;



public class NativeLawsBean {
    private long id;//
    private String name;//
    private String icon;//


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static class NativeLawsBeanParent{
        private List<NativeLawsBean> lawType;//

        public List<NativeLawsBean> getLawType() {
            return lawType;
        }

        public void setLawType(List<NativeLawsBean> lawType) {
            this.lawType = lawType;
        }
    }
}
