package com.yunyou.icloudinn.bookhouse.JavaBean;

import java.util.List;

public class HouseCollectData {

    /**
     * code : 100
     * msg : success
     */

    private int code;
    private String msg;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 2
         * per_page : 20
         * current_page : 1
         * last_page : 1
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 211
             * source_id : 19
             * user_id : 12142941
             * type : 1
             * create_time : 0
             * update_time : 0
             */

            private int id;
            private int source_id;
            private int user_id;
            private int type;
            private int create_time;
            private int update_time;
            private HouseBean house;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSource_id() {
                return source_id;
            }

            public void setSource_id(int source_id) {
                this.source_id = source_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public HouseBean getHouse() {
                return house;
            }

            public void setHouse(HouseBean house) {
                this.house = house;
            }

            public static class HouseBean {
                /**
                 * id : 19
                 * name : 浪漫公主主题房
                 * status : 1
                 * thumb : /uploads/20170621/e604f03d58832f0107e04124e2bd7848.jpg
                 * price : 550
                 * house_type : 整套预订、单间预订
                 * room_num : 10
                 * dwell_num : 15
                 * check_in_time : 10:05
                 * check_out_time : 12:00
                 * intro : null
                 * recommend_reason : 浪漫公主主题房
                 * describe : <p><br/></p><p>据美国报道称，中国正在发展一款设计前卫、造型奇特的新概念军舰，既可以在水面航行，也可以潜水航行。它或许意味着曾风靡一时的“武库舰”将在中国海军重获新生，成为中国航母战斗群中的火力支援平台。</p><p>冷战后，西方曾提出建造一种高机动性、载有数百枚对陆对海攻击导弹的“武库舰”，美国海军的设计方案是在万吨级舰艇上安装500个导弹垂直发射单元。</p><p>配备包括“战斧”巡航导弹、“标准”舰对空导弹、“鱼叉”反舰导弹、导弹防御和对岸火力支援系统等多种导弹，当时曾被认为是可以取代航母地位的革命性舰艇，但因种种原因最终被放弃。</p><p><img src="http://api.town.icloudinn.com/ueditor/php/upload/image/20170325/1490424998264301.jpg" alt="1490424998264301.jpg"/></p><p>其中，根据中国官方透露的信息，“中国版武库舰”有2个备选方案，一种是大部分舰身潜入水下、只留防空武器和雷达等露出水面的半潜舰体设计，另一种则是采用双指挥塔的潜艇武库舰设计。</p><p>“中国版武库舰”将采用扁平化舰体、具备船体转向系统，可以在全潜、半潜、水面中速、水面高速4种航行模式自由切换。“中国版武库舰”保持半潜状态时，会大大减少雷达反射面积;配合海军编队作战时，则浮出水面高速航行。</p><p><img src="http://api.town.icloudinn.com/ueditor/php/upload/image/20170325/1490424997388490.jpg" alt="1490424997388490.jpg"/></p><p>过去20年来，中国脚踏实地地钻研技术、建造军舰和新建港口，逐步组建全球大国必备引擎之一：一支能远离本土投送兵力的现代化海军。</p><p>从东海到非洲之角，中国军舰日益频繁地出现，这已经对世界事务产生影响，今后这一趋势还将加速。</p><p>新美国安全中心亚太安全项目主任帕特里克·克罗宁说：“到2030年，一支全球性中国海军的存在，将是国际政治中一个重要、有影响和根本的事实。”</p><p><img src="http://api.town.icloudinn.com/ueditor/php/upload/image/20170323/1490253624161713.jpg" alt="1490253624161713.jpg"/></p>
                 * owner :
                 * lat : 19.681932449341
                 * lng : 110.32600402832
                 * addr : 海南省海口市琼中
                 * create_time : 1499224776
                 */

                private int id;
                private String name;
                private int status;
                private String thumb;
                private int price;
                private String house_type;
                private int room_num;
                private int dwell_num;
                private String check_in_time;
                private String check_out_time;
                private Object intro;
                private String recommend_reason;
                private String describe;
                private String owner;
                private double lat;
                private double lng;
                private String addr;
                private String create_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public String getHouse_type() {
                    return house_type;
                }

                public void setHouse_type(String house_type) {
                    this.house_type = house_type;
                }

                public int getRoom_num() {
                    return room_num;
                }

                public void setRoom_num(int room_num) {
                    this.room_num = room_num;
                }

                public int getDwell_num() {
                    return dwell_num;
                }

                public void setDwell_num(int dwell_num) {
                    this.dwell_num = dwell_num;
                }

                public String getCheck_in_time() {
                    return check_in_time;
                }

                public void setCheck_in_time(String check_in_time) {
                    this.check_in_time = check_in_time;
                }

                public String getCheck_out_time() {
                    return check_out_time;
                }

                public void setCheck_out_time(String check_out_time) {
                    this.check_out_time = check_out_time;
                }

                public Object getIntro() {
                    return intro;
                }

                public void setIntro(Object intro) {
                    this.intro = intro;
                }

                public String getRecommend_reason() {
                    return recommend_reason;
                }

                public void setRecommend_reason(String recommend_reason) {
                    this.recommend_reason = recommend_reason;
                }

                public String getDescribe() {
                    return describe;
                }

                public void setDescribe(String describe) {
                    this.describe = describe;
                }

                public String getOwner() {
                    return owner;
                }

                public void setOwner(String owner) {
                    this.owner = owner;
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }
            }
        }
    }
}
