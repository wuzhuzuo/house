package com.w.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w.entity.*;
import com.w.service.*;
import com.w.utils.DateUtils;
import com.w.utils.Result;
import com.w.vo.MainApartVO;
import com.w.vo.ReturnHouse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Api(value = "CollectController", tags = "用户关注模块")
@RestController
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private UserService userService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BusinessService businessService;

    @ApiOperation("添加收藏")
    @PostMapping("/api/conllect/insert")
    public Result insert(@RequestBody CollectVo collectVo) throws ParseException {
        Result result = new Result();
        User user = new User();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(collectVo.getHouseId()) || StringUtils.isEmpty(collectVo.getPhone())) {
            result.setSuccess(false);
            result.setMessage("入参错误，收藏失败");
        } else {
            Collect collect = new Collect();
            collect.setHouseId(collectVo.getHouseId());
            collect.setPhone(collectVo.getPhone());
            user.setPhone(collectVo.getPhone());
            Collect collect1 = collectService.selectByCollectHouse(collect);
            User user1 = userService.selectByPhoneNo(user);
            List<RHouse> rHouses = houseService.selectByDistinct(collectVo.getHouseId());
            if (StringUtils.isEmpty(collect1)) {

                Collect collect2 = new Collect();
                collect2.setOpenId(user1.getOpenId());
                collect2.setHouseId(collectVo.getHouseId());
                collect2.setFollowTime(df.parse(df.format(new Date())));
                collect2.setPhone(collectVo.getPhone());
                if (!StringUtils.isEmpty(rHouses)) {
                    for (RHouse rHouse : rHouses) {
                        collect2.setBusId(rHouse.getBusId());
                    }
                    collectService.insertSelective(collect2);
                    result.setSuccess(true);
                    result.setMessage("关注成功");
                } else {
                    result.setSuccess(false);
                    result.setMessage("传入数据错误！关注房源失败");
                }
            } else {
                Collect collect3 = new Collect();
                collect3.setOpenId(user1.getOpenId());
                collect3.setPhone(user1.getPhone());
                collect3.setHouseId(collectVo.getHouseId());
                collect3.setFollowTime(df.parse(df.format(new Date())));
                collect3.setState(collectVo.getIsFollow());
                for (RHouse rHouse : rHouses) {
                    collect3.setBusId(rHouse.getBusId());
                }
                collectService.updateByState(collect3);
                if (collectVo.getIsFollow() != null && collectVo.getIsFollow().equals(1)) {
                    result.setSuccess(true);
                    result.setMessage("关注成功");
                } else {
                    result.setSuccess(true);
                    result.setMessage("取消成功");
                }
            }

        }
        return result;
    }

    //取消关注
    @ApiOperation("取消关注")
    @PostMapping("/api/collect/delete")
    public Result delete(@RequestBody CollectVo collectVo) {
        Result result = new Result();
        Collect collect = new Collect();
        User user = new User();
        if (StringUtils.isEmpty(collectVo.getPhone()) || StringUtils.isEmpty(collectVo.getHouseId())) {
            result.setMessage("入参错误，取消关注失败");
            result.setSuccess(false);
        } else {
            user.setPhone(collectVo.getPhone());
            User user1 = userService.selectByPhoneNo(user);
            if (StringUtils.isEmpty(user1)) {
                result.setSuccess(false);
                result.setMessage("暂未查到该用户信息");
            } else {
                collect.setOpenId(user1.getOpenId());
                collect.setHouseId(collectVo.getHouseId());
                collectService.updateByState(collect);
                result.setSuccess(true);
                result.setMessage("取消关注成功");
            }
        }
        return result;
    }

    //查看用户关注的所有房源
    @ApiOperation("查看用户关注的所有房源")
    @PostMapping("/api/collect/select")
    public Result select(@RequestBody PhoneVo phoneVo) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        User user = new User();
        if (StringUtils.isEmpty(phoneVo.getPhone())) {
            result.setSuccess(false);
            result.setMessage("手机号为空");
        } else {
            user.setPhone(phoneVo.getPhone());
            User user1 = userService.selectByPhoneNo(user);
            log.info("查询用户信息{}：", user);
            if (StringUtils.isEmpty(user1)) {
                result.setSuccess(false);
                result.setMessage("该用户暂未授权");
            } else {
                List list = new ArrayList();
                Page page = PageHelper.startPage(phoneVo.getPageIndex(), phoneVo.getPageSize());
                List<House> houseList = collectService.selectByUser(user1.getPhone(), phoneVo.getType());
                PageInfo pageInfo = new PageInfo<>(page.getResult());
                log.info("查询用户关注房源总数据{}", houseList.size());
                if (houseList != null && houseList.size() > 0) {

                    for (House house1 : houseList) {
                        ReturnHouse house3 = new ReturnHouse();
                        House house = houseService.selectByHouseId(house1.getId());
                        if (StringUtils.isEmpty(house)) {
                            continue;
                        }
                        if (phoneVo.getPhone() != null && phoneVo.getPhone().length() == 11) {
                            Collect collect1 = new Collect();
                            collect1.setPhone(phoneVo.getPhone());
                            collect1.setHouseId(house.getId());
                            Collect collect = collectService.selectByCollectHouse(collect1);
                            if (!StringUtils.isEmpty(collect)) {
                                house3.setIsFollow(collect.getState());
                            } else {
                                house3.setIsFollow(0);
                            }
                            house3.setFollowTime(collect.getFollowTime());
                        }

                        History history2 = historyService.selectByDetailCount(house.getId(), phoneVo.getPhone());
                        if (!StringUtils.isEmpty(history2.getBrowseCount())) {
                            house3.setRecord(history2.getBrowseCount());
                        } else {
                            house3.setRecord(0);
                        }

                        house3.setUserPhone(history2.getPhone());
                        //house3.setCreateTime(history2.getCreateTime());
                        house3.setActualFloor(house.getActualFloor());
                        house3.setAddress(house.getAddress());
                        house3.setApart(house.getApart());
                        house3.setIsRecommend(house.getIsRecommend());
                        house3.setArea(house.getArea());
                        house3.setCity(house.getCity());
                        house3.setContact(house.getContact());
                        house3.setFloorArea(house.getFloorArea());
                        if (!StringUtils.isEmpty(house.getHandTime())) {
                            house3.setHandTime(dateUtils.dateToString(house.getHandTime()));
                        }
                        house3.setHouseName(house.getHouseName());
                        house3.setIsBus(house.getIsBus());
                        house3.setIsFree(house.getIsFree());
                        house3.setIsHome(house.getIsHome());
                        house3.setIsMinprice(house.getIsMinprice());
                        house3.setIsMintotalprice(house.getIsMintotalprice());
                        house3.setIsPark(house.getIsPark());
                        house3.setIsSubway(house.getIsSubway());
                        house3.setLeaseWay(house.getLeaseWay());
                        house3.setMainFloor(house.getMainFloor());
                        if (!StringUtils.isEmpty(house.getOpenTime())) {
                            house3.setOpenTime(dateUtils.dateToString(house.getOpenTime()));
                        }
                        house3.setQuality(house.getQuality());
                        house3.setDeploy(house.getDeploy());
                        house3.setPayWay(house.getPayWay());
                        house3.setPhone(house.getPhone());
                        String str3 = house.getPicUrl();
                        if (!StringUtils.isEmpty(str3)) {
                            String[] arr2 = new String[0];
                            str3 = str3.replaceAll("\\[", "");
                            str3 = str3.replaceAll("\\]", "");
                            str3 = str3.replaceAll("\\\"", "");
                            log.info(str3);
                            if (str3.length() > 0) {
                                String str4 = str3.substring(0, str3.length());
                                log.info(str4);
                                String[] arr = str4.split(",");
                                house3.setPicUrl(arr);
                            } else {
                                house3.setPicUrl(arr2);
                            }
                        } else {
                            String[] arr3 = new String[0];
                            house3.setPicUrl(arr3);
                        }
                        List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house.getId());
                        List list2 = new ArrayList();
                        if (mainApartList != null && mainApartList.size() > 0) {
                            for (MainApart apart : mainApartList) {
                                MainApartVO mainApartVO = new MainApartVO();
                                mainApartVO.setApart(apart.getApart());
                                mainApartVO.setMainArea(apart.getMainArea());
                                mainApartVO.setPicUrl(apart.getPicUrl());
                                mainApartVO.setState(apart.getState());
                                list2.add(mainApartVO);
                            }
                        }
                        house3.setMainApart(list2);
                        house3.setPrice(house.getPrice());
                        house3.setProvince(house.getProvince());
                        house3.setRemark(house.getRemark());
                        house3.setRenovation(house.getRenovation());
                        house3.setSeeHouse(house.getSeeHouse());
                        house3.setState(house.getState());
                        house3.setTags(house.getTags());
                        house3.setQuality(house.getQuality());
                        house3.setFloors(house.getFloors());
                        house3.setMaxfloorArea(house.getMaxFloorarea());
                        house3.setMinfloorArea(house.getMinFloorarea());
                        house3.setTitle(house.getTitle());
                        house3.setTotalPrice(house.getTotalPrice());
                        house3.setType(house.getType());
                        house3.setHouseYear(house.getHouseYear());
                        house3.setDirection(house.getDirection());
                        house3.setId(house.getId());
                        List<RHouse> rHouseList2 = houseService.selectByDistinct(house.getId());
                        if (!StringUtils.isEmpty(rHouseList2) && rHouseList2.size() > 0) {
                            for (RHouse rHouse1 : rHouseList2) {
                                Business business = new Business();
                                business.setId(rHouse1.getBusId());
                                Business business1 = businessService.selectByPrimaryKey(business);
                                if (StringUtils.isEmpty(business1))
                                    continue;
                                house3.setBusiness(business1);
                            }

                        }
                        List<RHouse> rHouseList = houseService.selectByList(house.getId());
                        List list1 = new ArrayList();
                        if (rHouseList != null) {
                            for (RHouse rHouse : rHouseList) {
                                Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                                if (StringUtils.isEmpty(agent)) {
                                    continue;
                                }
                                list1.add(agent);
                            }
                            house3.setAgent(list1);
                        }
                        list.add(house3);


                    }
                    Map map = new HashMap();
                    map.put("rows", list);
                    map.put("totalCount", pageInfo.getTotal());
                    map.put("pageIndex", phoneVo.getPageIndex());
                    map.put("pageSize", phoneVo.getPageSize());
                    result.setSuccess(true);
                    result.setMessage("查询成功");
                    result.setData(map);
                } else {
                    Map map = new HashMap();
                    map.put("rows", list);
                    map.put("totalCount", pageInfo.getTotal());
                    map.put("pageIndex", phoneVo.getPageIndex());
                    map.put("pageSize", phoneVo.getPageSize());
                    result.setSuccess(true);
                    result.setMessage("查询成功");
                    result.setData(map);
                }
            }
        }

        return result;
    }

    @ApiOperation("查看用户收藏商家下房源的信息")
    @PostMapping("/api/collect/collectHouse")
    public Result collectHouse(@RequestBody RequestVo requestVo) throws Exception {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(requestVo.getBusId()) || StringUtils.isEmpty(requestVo.getPhone())) {
            result.setSuccess(false);
            result.setMessage("用户暂未授权或商家权限不足");
        } else {
            Collect collect = new Collect();
            collect.setBusId(requestVo.getBusId());
            collect.setPhone(requestVo.getPhone());
            Page page = PageHelper.startPage(requestVo.getPageIndex(), requestVo.getPageSize());
            List<Collect> collectList = collectService.selectByHouseCollect(collect);
            PageInfo info = new PageInfo(page.getResult());
            if (StringUtils.isEmpty(collectList)) {
                result.setSuccess(true);
                result.setMessage("该用户暂未关注房源或商家暂无房源被关注");
            } else {
                List list = new ArrayList();
                for (Collect collect1 : collectList) {
                    House house = houseService.selectByHouseId(collect1.getHouseId());
                    ReturnHouse house3 = new ReturnHouse();

                    Collect collect2 = collectService.selectByCollectHouse(collect1);
                    if (!StringUtils.isEmpty(collect2)) {
                        house3.setIsFollow(collect2.getState());
                    } else {
                        house3.setIsFollow(0);
                    }

                    house3.setUserPhone(collect1.getPhone());
                    house3.setCreateTime(collect1.getFollowTime());
                    house3.setActualFloor(house.getActualFloor());
                    house3.setAddress(house.getAddress());
                    house3.setApart(house.getApart());
                    house3.setIsRecommend(house.getIsRecommend());
                    house3.setArea(house.getArea());
                    house3.setCity(house.getCity());
                    house3.setContact(house.getContact());
                    house3.setFloorArea(house.getFloorArea());
                    if (!StringUtils.isEmpty(house.getHandTime())) {
                        house3.setHandTime(dateUtils.dateToString(house.getHandTime()));
                    }
                    house3.setHouseName(house.getHouseName());
                    house3.setIsBus(house.getIsBus());
                    house3.setIsFree(house.getIsFree());
                    house3.setIsHome(house.getIsHome());
                    house3.setIsMinprice(house.getIsMinprice());
                    house3.setIsMintotalprice(house.getIsMintotalprice());
                    house3.setIsPark(house.getIsPark());
                    house3.setIsSubway(house.getIsSubway());
                    house3.setLeaseWay(house.getLeaseWay());
                    house3.setMainFloor(house.getMainFloor());
                    if (!StringUtils.isEmpty(house.getOpenTime())) {
                        house3.setOpenTime(dateUtils.dateToString(house.getOpenTime()));
                    }
                    house3.setQuality(house.getQuality());
                    house3.setDeploy(house.getDeploy());
                    house3.setPayWay(house.getPayWay());
                    house3.setPhone(house.getPhone());
                    String str3 = house.getPicUrl();
                    if (!StringUtils.isEmpty(str3)) {
                        String[] arr2 = new String[0];
                        str3 = str3.replaceAll("\\[", "");
                        str3 = str3.replaceAll("\\]", "");
                        str3 = str3.replaceAll("\\\"", "");
                        log.info(str3);
                        if (str3.length() > 0) {
                            String str4 = str3.substring(0, str3.length());
                            log.info(str4);
                            String[] arr = str4.split(",");
                            house3.setPicUrl(arr);
                        } else {
                            house3.setPicUrl(arr2);
                        }
                    } else {
                        String[] arr3 = new String[0];
                        house3.setPicUrl(arr3);
                    }

                    List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house.getId());
                    List list2 = new ArrayList();
                    if (list2 != null) {
                        for (MainApart apart : mainApartList) {
                            MainApartVO mainApartVO = new MainApartVO();
                            mainApartVO.setApart(apart.getApart());
                            mainApartVO.setMainArea(apart.getMainArea());
                            mainApartVO.setPicUrl(apart.getPicUrl());
                            mainApartVO.setState(apart.getState());
                            list2.add(mainApartVO);
                        }
                    }
                    house3.setMainApart(list2);
                    house3.setPrice(house.getPrice());
                    house3.setProvince(house.getProvince());
                    house3.setRemark(house.getRemark());
                    house3.setRenovation(house.getRenovation());
                    house3.setSeeHouse(house.getSeeHouse());
                    house3.setState(house.getState());
                    house3.setTags(house.getTags());
                    house3.setQuality(house.getQuality());
                    house3.setFloors(house.getFloors());
                    house3.setMaxfloorArea(house.getMaxFloorarea());
                    house3.setMinfloorArea(house.getMinFloorarea());
                    house3.setTitle(house.getTitle());
                    house3.setTotalPrice(house.getTotalPrice());
                    house3.setType(house.getType());
                    house3.setHouseYear(house.getHouseYear());
                    house3.setDirection(house.getDirection());
                    house3.setId(house.getId());


                    History history2 = historyService.selectByDetailCount(house.getId(), requestVo.getPhone());
                    if (!StringUtils.isEmpty(history2.getBrowseCount())) {
                        house3.setRecord(history2.getBrowseCount());
                    } else {
                        house3.setRecord(0);
                    }

                    List<RHouse> rHouseList = houseService.selectByList(house.getId());
                    List list1 = new ArrayList();
                    if (rHouseList != null) {
                        for (RHouse rHouse : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                            if (StringUtils.isEmpty(agent))
                                continue;
                            list1.add(agent);
                        }
                        house3.setAgent(list1);
                    }
                    list.add(house3);

                }
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", requestVo.getPageIndex());
                map.put("pageSize", requestVo.getPageSize());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);

            }
        }
        return result;
    }


    @Data
    @ApiModel
    public static class CollectVo {
        @ApiModelProperty(value = "用户手机号", required = true)
        private String phone;
        @ApiModelProperty(value = "房源编号", required = true)
        private Integer houseId;
        @ApiModelProperty(value = "是否关注房源1：关注，0不关注或取消关注")
        private Integer isFollow;
    }

    @Data
    @ApiModel
    public static class PhoneVo {
        @ApiModelProperty(value = "用户手机号", required = true)
        private String phone;
        @ApiModelProperty(value = "房源类型：1新房，2：二手房 3:租房")
        private Integer type;
        @ApiModelProperty(value = "页码", required = true, example = "1")
        private Integer pageIndex;
        @ApiModelProperty(value = "数量", required = true, example = "2")
        private Integer pageSize;
    }

    @Data
    @ApiModel
    public static class RequestVo {
        @ApiModelProperty(value = "用户手机号", required = true)
        private String phone;
        @ApiModelProperty(value = "商家编码", required = true)
        private Integer busId;
        @ApiModelProperty(value = "页码", required = true, example = "1")
        private Integer pageIndex;
        @ApiModelProperty(value = "数量", required = true, example = "2")
        private Integer pageSize;
    }

    @Data
    @ApiModel
    public static class ReturnCollectVo {
        @ApiModelProperty(value = "用户手机号", required = true)
        private String phone;
        @ApiModelProperty(value = "该用户收藏该商家下房源总数", required = true)
        private Integer userCollect;
    }

}
