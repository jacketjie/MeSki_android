package jacketjie.common.libray.custom.view.viewpager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public class TestBean  {

    /**
     * AllAds : [{"ads_position":"\u200b1","ads_platform":"\u200b2","ads_sortweight":"\u200b22","ads_id":"\u200b15","ads_auditstate":"\u200b1","ads_avatar":"http://www.siyslchina.org/static/img/avatar/ads/P15_2690757499.jpeg","ads_externalurl":"http://siyslchina.org","ads_publisherid":"\u200b2509","ads_categoryid":"\u200b17"},{"ads_position":"\u200b3","ads_platform":"\u200b2","ads_sortweight":"\u200b44","ads_id":"\u200b14","ads_auditstate":"\u200b1","ads_avatar":"http://www.siyslchina.org/static/img/avatar/ads/P14_9223369361.jpeg","ads_externalurl":"http://siyslchina.org","ads_publisherid":"\u200b2509","ads_categoryid":"\u200b17"},{"ads_position":"\u200b2","ads_platform":"\u200b2","ads_sortweight":"\u200b11","ads_id":"\u200b11","ads_auditstate":"\u200b1","ads_avatar":"http://www.siyslchina.org/static/img/avatar/ads/P11_9223462622.jpeg","ads_externalurl":"http://siyslchina.org","ads_publisherid":"\u200b2509","ads_categoryid":"\u200b17"}]
     * result : 1
     */
    private String result;
    /**
     * ads_position : ​1
     * ads_platform : ​2
     * ads_sortweight : ​22
     * ads_id : ​15
     * ads_auditstate : ​1
     * ads_avatar : http://www.siyslchina.org/static/img/avatar/ads/P15_2690757499.jpeg
     * ads_externalurl : http://siyslchina.org
     * ads_publisherid : ​2509
     * ads_categoryid : ​17
     */

    private List<AllAdsEntity> AllAds;

    public void setResult(String result) {
        this.result = result;
    }

    public void setAllAds(List<AllAdsEntity> AllAds) {
        this.AllAds = AllAds;
    }

    public String getResult() {
        return result;
    }

    public List<AllAdsEntity> getAllAds() {
        return AllAds;
    }

    public static class AllAdsEntity implements Parcelable{
        private String ads_position;
        private String ads_platform;
        private String ads_sortweight;
        private String ads_id;
        private String ads_auditstate;
        private String ads_avatar;
        private String ads_externalurl;
        private String ads_publisherid;
        private String ads_categoryid;

        public void setAds_position(String ads_position) {
            this.ads_position = ads_position;
        }

        public void setAds_platform(String ads_platform) {
            this.ads_platform = ads_platform;
        }

        public void setAds_sortweight(String ads_sortweight) {
            this.ads_sortweight = ads_sortweight;
        }

        public void setAds_id(String ads_id) {
            this.ads_id = ads_id;
        }

        public void setAds_auditstate(String ads_auditstate) {
            this.ads_auditstate = ads_auditstate;
        }

        public void setAds_avatar(String ads_avatar) {
            this.ads_avatar = ads_avatar;
        }

        public void setAds_externalurl(String ads_externalurl) {
            this.ads_externalurl = ads_externalurl;
        }

        public void setAds_publisherid(String ads_publisherid) {
            this.ads_publisherid = ads_publisherid;
        }

        public void setAds_categoryid(String ads_categoryid) {
            this.ads_categoryid = ads_categoryid;
        }

        public String getAds_position() {
            return ads_position;
        }

        public String getAds_platform() {
            return ads_platform;
        }

        public String getAds_sortweight() {
            return ads_sortweight;
        }

        public String getAds_id() {
            return ads_id;
        }

        public String getAds_auditstate() {
            return ads_auditstate;
        }

        public String getAds_avatar() {
            return ads_avatar;
        }

        public String getAds_externalurl() {
            return ads_externalurl;
        }

        public String getAds_publisherid() {
            return ads_publisherid;
        }

        public String getAds_categoryid() {
            return ads_categoryid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ads_position);
            dest.writeString(this.ads_platform);
            dest.writeString(this.ads_sortweight);
            dest.writeString(this.ads_id);
            dest.writeString(this.ads_auditstate);
            dest.writeString(this.ads_avatar);
            dest.writeString(this.ads_externalurl);
            dest.writeString(this.ads_publisherid);
            dest.writeString(this.ads_categoryid);
        }

        public AllAdsEntity() {
        }

        protected AllAdsEntity(Parcel in) {
            this.ads_position = in.readString();
            this.ads_platform = in.readString();
            this.ads_sortweight = in.readString();
            this.ads_id = in.readString();
            this.ads_auditstate = in.readString();
            this.ads_avatar = in.readString();
            this.ads_externalurl = in.readString();
            this.ads_publisherid = in.readString();
            this.ads_categoryid = in.readString();
        }

        public static final Creator<AllAdsEntity> CREATOR = new Creator<AllAdsEntity>() {
            public AllAdsEntity createFromParcel(Parcel source) {
                return new AllAdsEntity(source);
            }

            public AllAdsEntity[] newArray(int size) {
                return new AllAdsEntity[size];
            }
        };

        @Override
        public String toString() {
            return "AllAdsEntity{" +
                    "ads_auditstate='" + ads_auditstate + '\'' +
                    ", ads_position='" + ads_position + '\'' +
                    ", ads_platform='" + ads_platform + '\'' +
                    ", ads_sortweight='" + ads_sortweight + '\'' +
                    ", ads_id='" + ads_id + '\'' +
                    ", ads_avatar='" + ads_avatar + '\'' +
                    ", ads_externalurl='" + ads_externalurl + '\'' +
                    ", ads_publisherid='" + ads_publisherid + '\'' +
                    ", ads_categoryid='" + ads_categoryid + '\'' +
                    '}';
        }
    }
}
