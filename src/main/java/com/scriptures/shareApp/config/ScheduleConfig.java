package com.scriptures.shareApp.config;


import com.scriptures.shareApp.contants.OrderStatusEnum;
import com.scriptures.shareApp.dao.entity.SharedataCommodity;
import com.scriptures.shareApp.service.CommodityService;
import com.scriptures.shareApp.service.WithdrawalService;
import com.scriptures.shareApp.service.impl.ShareDataCommodityServiceImpl;
import com.scriptures.shareApp.util.DateUtil;
import com.scriptures.shareApp.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private ShareDataCommodityServiceImpl shareDataCommodityService;

    @Autowired
    private ShareDataConfig shareDataConfig;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private WithdrawalService withdrawalService;

    /**
     * 定时从商城获取商品购买数据
     * 每分钟获取一次
     */
    //@Scheduled(cron = "* * */1 * * ?")
    @Scheduled(cron = "0 */2 * * * ?")
    private void obtainCommodityShareData() {
        System.err.println("----定时任务-商城获取数据-----");

        String url = shareDataConfig.getGetUrl() + "&key=" + shareDataConfig.getKey();
        String requestResult = HttpUtil.doGet(url);
        // JSONObject result=JSONObject.parseObject(requestResult);
        // JSONObject data=JSONObject.fromObject(requestResult);
        JSONArray data = JSONArray.fromObject(requestResult);
        if (data.size() > 0) {
            System.err.println("取到请求返回数据");
            List<SharedataCommodity> sharedataCommodities = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                SharedataCommodity sharedataCommodity = new SharedataCommodity();
                JSONObject jsonObject = data.getJSONObject(i);
                sharedataCommodity.setId(jsonObject.getString("id"));
                sharedataCommodity.setCode(jsonObject.getString("Code"));
                sharedataCommodity.setNum(jsonObject.getInt("num"));
                sharedataCommodity.setSingleamount(jsonObject.getDouble("singleamount"));
                sharedataCommodity.setBuyamount(jsonObject.getDouble("buyamount"));
                sharedataCommodity.setOrderId(jsonObject.getString("OrderID"));
                sharedataCommodity.setReceiver(jsonObject.getString("receiver"));
                sharedataCommodity.setPhone(jsonObject.getString("MobilePhone"));
                sharedataCommodity.setMemberId(jsonObject.getString("utm_source"));
                sharedataCommodity.setCreateDate(DateUtil.getCurrentTime());
                sharedataCommodity.setRemarks("1");
                //计算佣金
                Double commission = commodityService.countCommission(sharedataCommodity.getCode(), sharedataCommodity.getBuyamount());
                sharedataCommodity.setCommission(commission);
                sharedataCommodities.add(sharedataCommodity);
            }
            //处理
            for (SharedataCommodity sharedataCommodity : sharedataCommodities) {
                try {
                    shareDataCommodityService.addData(sharedataCommodity);
                    int resultCount = withdrawalService.commodityCommissionAdd(sharedataCommodity.getMemberId(), sharedataCommodity.getCommission());
                    if (resultCount == 0) {
                        System.err.println(sharedataCommodity.getMemberId() + "添加佣金失败");
                    }
                } catch (Exception e) {
                    System.err.println(sharedataCommodity.getId() + "信息添加失败");
                    continue;
                }
                String url2 = shareDataConfig.getConfirmUrl() + "&key=" + shareDataConfig.getKey() + "&id=" + sharedataCommodity.getId();
                HttpUtil.doGet(url2);
            }
        } else {
            System.err.println("【新支付订单】数据为空");
        }
    }

    /**
     * (失效)定时将下单时间一个月及以上的订单佣金解冻
     * //定时将已完成xxx天及以上的订单佣金解冻
     * 每天凌晨1点半执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0 */1 * * * ?")
    private void scheduledThaw() {
        System.err.println("-------资金解冻查询-------");
        //查询xxx天以上的“已完成”订单
        List<SharedataCommodity> sharedataCommodities = shareDataCommodityService.selectByStatusByTimeOut(10, OrderStatusEnum.COMLETE.getStatus());
        if(sharedataCommodities.size()>0){
            for (SharedataCommodity sharedataCommodity : sharedataCommodities) {
                int resultCount = shareDataCommodityService.updateStatus(sharedataCommodity.getId(), OrderStatusEnum.unfreeze.getStatus());
                if (resultCount > 0) {
                    System.err.println(sharedataCommodity.getId() + "订单解冻成功");
                    int aa = withdrawalService.commodityCommissionThaw(sharedataCommodity.getMemberId(), sharedataCommodity.getCommission());
                    if (aa > 0) {
                        System.err.println(sharedataCommodity.getId() + "会员资金解冻成功" + sharedataCommodity.getMemberId());
                    } else {
                        System.err.println(sharedataCommodity.getId() + "会员资金解冻失败" + sharedataCommodity.getMemberId());
                    }
                } else {
                    System.out.println(sharedataCommodity.getId() + "订单解冻失败");
                }
            }
        }else {
            System.err.println("没有需要解冻的订单");
        }

    }

    /**
     * 定时向商城查询冻结状态的订单状态
     * 如果退款
     * 则收回分享用户相应的佣金，
     * 并设置订单状态为“已失效”，
     * 如果已完成
     * 则设置订单完成时间（已完成XX天后自动解冻）
     * 修改订单状态为已完成
     * ！！！修改佣金统计接口，过滤失效订单
     */
    //每天凌晨2点执行
    //@Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 0 2 * * ?")
    private void selectOrderStatus() {
        System.err.println("-------定时查询订单状态（商城）开启-------");
        //(失效)查询下单一个月以内的“冻结”订单(失效)
        //(失效)List<SharedataCommodity> sharedataCommodities=shareDataCommodityService.selectFreezeByTimeIn(30);(失效)
        //查询所有“冻结”订单
        List<SharedataCommodity> sharedataCommodities = shareDataCommodityService.selectListToFreeze();
        if (sharedataCommodities.size() != 0) {
            for (SharedataCommodity sharedataCommodity : sharedataCommodities) {
                String url = shareDataConfig.getGetStatusUrl() + "&key=" + shareDataConfig.getKey() + "&id=" + sharedataCommodity.getId();
                String requestResult = HttpUtil.doGet(url);
                JSONArray datas = JSONArray.fromObject(requestResult);
                if (datas.size() == 0) {
                    System.err.println("没有找到订单数据:" + sharedataCommodity.getId());
                } else {
                    JSONObject data = datas.getJSONObject(0);
                    String orderStatus = data.getString("status");
                    //如果该商品状态为已退款
                    if (orderStatus.equals(OrderStatusEnum.REFUNDED.getStatus())) {
                        //将对应订单改为失效，并且扣款
                        System.err.println(sharedataCommodity.getId() + "订单被退款");
                        //扣款
                        int a = withdrawalService.commodityCommissionMinus(sharedataCommodity.getMemberId(), sharedataCommodity.getCommission());
                        if (a == 0) {
                            System.err.println(sharedataCommodity.getId() + "扣款失败" + sharedataCommodity.getMemberId());
                        } else {
                            //订单失效
                            int b = shareDataCommodityService.updateStatus(sharedataCommodity.getId(), OrderStatusEnum.invalid.getStatus());
                            if (b > 0) {
                                System.err.println(sharedataCommodity.getId() + "【订单状态】已失效");
                            } else {
                                System.err.println(sharedataCommodity.getId() + "【订单状态-失效】修改失败");
                            }
                        }
                    } else if (orderStatus.equals(OrderStatusEnum.COMLETE.getStatus())) {
                        //如果该商品状态为已完成
                        int c = shareDataCommodityService.updateStatusToComlete(sharedataCommodity.getId());
                        if (c > 0) {
                            System.err.println(sharedataCommodity.getId() + "【订单状态】已完成");
                        } else {
                            System.err.println(sharedataCommodity.getId() + "【订单状态-完成】修改失败");
                        }
                    }
                }
            }
        }
    }
}

@ConfigurationProperties(prefix = "shareData")
@Component
class ShareDataConfig {
    private String getUrl;

    private String confirmUrl;

    private String getStatusUrl;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGetStatusUrl() {
        return getStatusUrl;
    }

    public void setGetStatusUrl(String getStatusUrl) {
        this.getStatusUrl = getStatusUrl;
    }

    public String getGetUrl() {
        return getUrl;
    }

    public void setGetUrl(String getUrl) {
        this.getUrl = getUrl;
    }

    public String getConfirmUrl() {
        return confirmUrl;
    }

    public void setConfirmUrl(String confirmUrl) {
        this.confirmUrl = confirmUrl;
    }
}
